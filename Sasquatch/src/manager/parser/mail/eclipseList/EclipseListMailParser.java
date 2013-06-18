package manager.parser.mail.eclipseList;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.htmlparser.Parser;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.Bullet;
import org.htmlparser.tags.TitleTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import manager.parser.mail.MailParser;
import manager.systems.source.mail.LocalMailHandler;
import manager.systems.source.mail.Mail;

public class EclipseListMailParser extends MailParser {

	private static final String BODY_START = "<!--X-Body-of-Message-->";
	private static final String BODY_END = "<!--X-Body-of-Message-End-->";
	private static final String BLOCKQUOUTE_OPENING = "<blockquote class=\"gmail_quote\" style=\"margin:0 0 0 .8ex;border-left:1px #ccc solid;padding-left:1ex\">";
	private static final String BLOCKQUOUTE_CLOSING = "</blockquote>";
	private static final String DATE_START = "Date                : ";
	private static final String DATE_FORMAT = "EEE, dd MMM yyyy HH:mm:ss z";
	
	private int openQuoteBlocks = 0;
	
	public EclipseListMailParser() {

	}

	public EclipseListMailParser(String path) {
		super(path);
	}

	public EclipseListMailParser(LocalMailHandler handler) {
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
	
	private String getDateText(String text) {
		String date = "";
		try {
			Parser parser = new Parser(text);
			TagNameFilter filter = new TagNameFilter("li");
			NodeList list = parser.parse(filter);
			for (int i = 0; i < list.size(); i++) {
				Bullet b = (Bullet) list.elementAt(i);
				String content = b.toPlainTextString().replace("\n", "").trim();
				if (content.startsWith(DATE_START)) {
					date = content.substring(DATE_START.length());
				}
			}
		} catch (ParserException e) {
			e.printStackTrace();
		}
		return date;
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

	private String getBody(String text) {
		String body = "";

		int start = text.indexOf(BODY_START) + BODY_START.length();
		int end = text.indexOf(BODY_END);

		if (start != -1 && end != -1) {
			body = text.substring(start, end);
		}

		return body;
	}

	private String parseBody(String text) {
		openQuoteBlocks = 0;
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
					ret += " " + line.trim();
				}
			}
		}

		return ret.trim();
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
		String tmp = line;
		while(tmp.contains(BLOCKQUOUTE_OPENING)) {
			tmp = tmp.replaceFirst(BLOCKQUOUTE_OPENING, "");
			openQuoteBlocks++;
		}
		tmp = line;
		while(tmp.contains(BLOCKQUOUTE_CLOSING)) {
			tmp = tmp.replaceFirst(BLOCKQUOUTE_CLOSING, "");
			openQuoteBlocks--;
		}
		line = line.trim();
		return (openQuoteBlocks != 0) || line.endsWith("wrote:") || line.startsWith("&gt;");
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
