package manager.parser.mail.mailArchive;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.Bullet;
import org.htmlparser.tags.Div;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.tags.Span;
import org.htmlparser.tags.TitleTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import manager.parser.mail.MailParser;
import manager.systems.source.mail.LocalMailHandler;
import manager.systems.source.mail.Mail;

public class MailArchiveParser extends MailParser {
	
	private static final String END_LINE = "_______________________________________________";
	private static final String DATE_FORMAT = "EEE, dd MMM yyyy HH:mm:ss z";
	
	public MailArchiveParser() {

	}

	public MailArchiveParser(String path) {
		super(path);
	}

	public MailArchiveParser(LocalMailHandler handler) {
		super(handler);
	}

	@Override
	public Mail parseMail(String text) {
		String header = getHeader(text);
		Date date = convertDate(getDateText(text));
		String body = getBody(text);
		body = parseBody(body);
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

	private String parseBody(String text) {
		String ret = "";
		text = text.trim();
		text = text.replace("<br/>", "\n");
		text = text.replace("&nbsp;", " ");
		text = text.replace("&#xC2;", " ");
		text = text.replace("&#xA0;", " ");
		text = text.replace("&#39;", "'");
		String[] lines = text.split("\n");
		for (String line : lines) {
			if (!isQuote(line)) {
				line = line.replaceAll("<(.)*>", "").trim();
				if (!(isCode(line) || isEmpty(line))) {
					if (isEnd(line)) {
						break;
					} else {
						ret += " " + line.trim();
					}
				}
			}
		}
		return ret.trim();
	}

	private boolean isEnd(String line) {
		return line.equals(END_LINE);
	}

	private boolean isEmpty(String line) {
		return line.trim().isEmpty();
	}


	private boolean isCode(String line) {
		boolean leadingSpaces = line.startsWith("   ");
		line = line.trim();
		return leadingSpaces || line.endsWith(";") || line.contains("{") || line.contains("}") || line.startsWith("&lt;");
	}


	private boolean isQuote(String line) {
		line = line.trim();
		return line.endsWith("wrote:") || line.startsWith("&gt;");
	}
	
	private String getBody(String text) {
		String body = "";
		try {
			Parser parser = new Parser(text);
			TagNameFilter tagFilter = new TagNameFilter("div");
			HasAttributeFilter attrFilter = new HasAttributeFilter("class", "msgBody");
			AndFilter filter = new AndFilter(tagFilter, attrFilter);
			NodeList list = parser.parse(filter);
			if (list.size() == 1) {
				Div divTag = (Div) list.elementAt(0);
				body = divTag.getStringText().trim();
			}
		} catch (ParserException e) {
			e.printStackTrace();
		}
		return body;
	}

	private String getDateText(String text) {
		String date = "";
		try {
			Parser parser = new Parser(text);
			TagNameFilter tagFilter = new TagNameFilter("span");
			HasAttributeFilter attrFilter = new HasAttributeFilter("class", "date");
			AndFilter filter = new AndFilter(tagFilter, attrFilter);
			NodeList list = parser.parse(filter);
			if (list.size() > 0) {
				Span s = (Span) list.elementAt(0);
				String content = s.getStringText();
				parser = new Parser(content);
				tagFilter = new TagNameFilter("a");
				NodeList links = parser.parse(tagFilter);
				if (links.size() > 0) {
					LinkTag link = (LinkTag) links.elementAt(0);
					String linkContent = link.getStringText().trim();
					date = linkContent;
				}
			}
		} catch (ParserException e) {
			e.printStackTrace();
		}
		return date;
	}

	private String getHeader(String text) {
		String header = "";
		try {
			Parser parser = new Parser(text);
			TagNameFilter titleFilter = new TagNameFilter("title");
			NodeList list = parser.parse(titleFilter);
			if (list.size() == 1) {
				TitleTag title = (TitleTag) list.elementAt(0);
				header = title.getStringText().trim();
			}
		} catch (ParserException e) {
			e.printStackTrace();
		}
		return header;
	}

}
