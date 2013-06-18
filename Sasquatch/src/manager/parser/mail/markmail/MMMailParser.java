package manager.parser.mail.markmail;

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
import org.htmlparser.tags.TableColumn;
import org.htmlparser.tags.TableHeader;
import org.htmlparser.tags.TableRow;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import manager.parser.mail.MailParser;
import manager.systems.source.mail.LocalMailHandler;
import manager.systems.source.mail.Mail;

public class MMMailParser extends MailParser {
	
	private static final String DATE_START = "Date:";
	private static final String DATE_FORMAT = "MMM d, yyyy h:m:s aaa";

	public MMMailParser() {

	}

	public MMMailParser(String path) {
		super(path);
	}

	public MMMailParser(LocalMailHandler handler) {
		super(handler);
	}

	@Override
	public Mail parseMail(String text) {
		String header = "";
		String body = "";
		header = getHeader(text);
		body = getBody(text);
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
		try {
			Parser parser = new Parser(text);
			TagNameFilter filter = new TagNameFilter("tr");
			NodeList list = parser.parse(filter);
			for (int i = 0; i < list.size(); i++) {
				TableRow row = (TableRow) list.elementAt(i);
				if (row.getStringText().contains(DATE_START)) {
					NodeList headers = row.searchFor(TableHeader.class, false);
					NodeList cols = row.searchFor(TableColumn.class, false);
					if (headers.size() == 1 && cols.size() == 1) {
						TableHeader header = (TableHeader) headers.elementAt(0);
						TableColumn col = (TableColumn) cols.elementAt(0);
						String headerText = header.getStringText().trim();
						if (headerText.equals(DATE_START)) {
							date = col.getStringText().trim() 	;
						}
					}
				}
			}

		} catch (ParserException e) {
			e.printStackTrace();
		} 
		return date;
	}

	private String getBody(String text) {
		String body = "";
		String startTag = "<div class=\"pws\">";
		String signTag = "<div class=\"footer signature\">";
		String endTag = "<div class=\"footer list-management\">";
		int start = text.indexOf(startTag) + startTag.length();
		int end = text.indexOf(signTag);
		if (end == -1) {
			end = text.indexOf(endTag);
		}
		if (start > -1 && end > -1) {
			body = parseBody(text.substring(start, end));
		}
		return body;
	}

	private String parseBody(String text) {
		String ret = "";
		text = text.replace("<p>", "");
		text = text.replace("</p>", "");

		//replace other html-tags like <a>

		text = text.trim();
		String[] lines = text.split("\n");
		for (String line : lines) {
			if (!(isCode(line) || isQuote(line) || isEmpty(line))) {
				ret += " " + line.trim();
			}
		}
		return ret;
	}

	private boolean isEmpty(String line) {
		return line.isEmpty();
	}

	private boolean isQuote(String line) {
		return line.startsWith("&gt;");
	}

	private boolean isCode(String line) {
		//		boolean leadingSpaces = line.startsWith("   ");
		line = line.trim();
		return /*leadingSpaces ||*/ line.endsWith(";") || line.contains("{") || line.contains("}") || line.startsWith("&lt;");
	}

	private String getHeader(String text) {
		String header = "";
		HasAttributeFilter attrFilter = new HasAttributeFilter("class", "subject");
		TagNameFilter tagFilter = new TagNameFilter("a");
		AndFilter filter = new AndFilter(attrFilter, tagFilter);
		try {
			Parser parser = new Parser(text);
			NodeList list = parser.parse(filter);
			Node node = list.elementAt(0);
			header = node.toPlainTextString();
		} catch (ParserException e) {
			e.printStackTrace();
		}
		return header;
	}
}
