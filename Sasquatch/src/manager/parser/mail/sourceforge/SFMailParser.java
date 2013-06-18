package manager.parser.mail.sourceforge;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import manager.parser.mail.MailParser;

import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.Div;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.tags.TableColumn;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import manager.systems.source.mail.LocalMailHandler;
import manager.systems.source.mail.Mail;

public class SFMailParser extends MailParser {

	private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm";
	private static final String DATE_SEPARATOR = " -	";
	private static final String DATE_TAG = "small";
	
	public SFMailParser() {

	}

	public SFMailParser(String path) {
		super(path);
	}

	public SFMailParser(LocalMailHandler handler) {
		super(handler);
	}

	@Override
	public Mail parseMail(String text) {
		String header = getHeader(text);
		String body = getBody(text);
		body = parseBody(body);
		Date date = convertDate(getDate(text));
		return new Mail(header, body, date);
	}

	private Date convertDate(String dateText) {
		Date date = null;
		try {
			DateFormat formatter = new SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH);
			date = formatter.parse(dateText);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	private String getDate(String text) {
		String date = "";
		TagNameFilter divFilter = new TagNameFilter("div");
		HasAttributeFilter forumFilter = new HasAttributeFilter("class", "forum");
		AndFilter andFilter = new AndFilter(divFilter, forumFilter);
		try {
			Parser parser = new Parser(text);
			NodeList list = parser.parse(andFilter);
			Div div = (Div) list.elementAt(0);
			if (div != null) {
				String content = div.getStringText();
				String smallTag = getTagText(DATE_TAG, content);
				int separatorIndex = smallTag.indexOf(DATE_SEPARATOR);
				date = smallTag.substring(separatorIndex + DATE_SEPARATOR.length()).trim();
			}
		} catch (ParserException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	private String getTagText(String tag, String text) {
		String ret = "";
		int start = text.indexOf("<" + tag + ">");
		int end = text.indexOf("</" + tag + ">");
		
		if (start != -1 && end != -1 && start < end) {
			ret = text.substring(start + tag.length() + 2, end);
		}
		
		return ret;
	}

	private String getBody(String text) {
		String body = "";
		try {
			Parser parser = new Parser(text);
			TagNameFilter tagFilter = new TagNameFilter("td");
			HasAttributeFilter classFilter = new HasAttributeFilter("class", "email_body");
			AndFilter filter = new AndFilter(tagFilter, classFilter);
			NodeList list = parser.parse(filter);
			if (list.size() == 1) {
				TableColumn col = (TableColumn) list.elementAt(0);
				String content = col.getStringText();
				body = content;
			}
		} catch (ParserException e) {
			e.printStackTrace();
		}
		return body;
	}

	private String parseBody(String text) {
		String ret = "";
		text = text.trim();
		text = text.replace("<br>", "\n");
		String[] lines = text.split("\n");
		for (String line : lines) {
			if (!isQuote(line)) {
				line = line.replaceAll("<(.)*>", "").trim();
				if (!(isCode(line) || isEmpty(line))) {
//					if (isEnd(line)) {
//						break;
//					} else {
						line = line.replaceAll("<(.)*>", "");
						ret += " " + line.trim();
//					}
				}
			}
			
		}
		return ret.trim();

	}

	//	private boolean isEnd(String line) {
	//		line = line.trim();
	//		return line.equals("---------------------------------------------------------------------");
	//	}

	private boolean isEmpty(String line) {
		return line.isEmpty();
	}

	private boolean isQuote(String line) {
		return line.startsWith("&gt;") || line.contains("----- Original Message -----") || line.endsWith("writes:");
	}

	private boolean isCode(String line) {
		boolean leadingSpaces = line.startsWith("   ");
		line = line.trim();
		return leadingSpaces || line.endsWith(";") || line.contains("{") || line.contains("}") || line.startsWith("&lt;");
	}

	private String getHeader(String text) {
		String header = "";
		TagNameFilter divFilter = new TagNameFilter("div");
		HasAttributeFilter forumFilter = new HasAttributeFilter("class", "forum");
		AndFilter andFilter = new AndFilter(divFilter, forumFilter);
		try {
			Parser parser = new Parser(text);
			NodeList list = parser.parse(andFilter);
			Div div = (Div) list.elementAt(0);
			if (div != null) {
				NodeList divList = div.searchFor(LinkTag.class, true);
				LinkTag link = (LinkTag) divList.elementAt(0);
				header = link.getStringText().trim();
			}
		} catch (ParserException e) {
			e.printStackTrace();
		}
		return header;
	}
}
