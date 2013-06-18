package manager.parser.mail.pipermail;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.htmlparser.Parser;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.BodyTag;
import org.htmlparser.tags.TitleTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import manager.parser.mail.MailParser;
import manager.systems.source.mail.LocalMailHandler;
import manager.systems.source.mail.Mail;

public class PiperMailParser extends MailParser {

	private static final String BODY_START = "<!--beginarticle-->";
	private static final String BODY_END = "<!--endarticle-->";
	private static final String DATE_START = "<I>";
	private static final String DATE_END = "</I>";
	private static final String DATE_FORMAT = "EEE MMM d HH:mm:ss z yyyy";

	public PiperMailParser() {

	}

	public PiperMailParser(String path) {
		super(path);
	}

	public PiperMailParser(LocalMailHandler handler) {
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
		if (!dateText.isEmpty()) {
			try {
				DateFormat formatter = new SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH);
				date = formatter.parse(dateText);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return date;
	}

	private String getDate(String text) {
		String date = "";
		try {
			Parser parser = new Parser(text);
			TagNameFilter filter = new TagNameFilter("body");
			NodeList list = parser.parse(filter);
			if (list.size() == 1) {
				BodyTag body = (BodyTag) list.elementAt(0);
				String content = body.getStringText().trim();
				int start = content.indexOf(DATE_START);
				int end = content.indexOf(DATE_END);
				if (start != -1 && end != -1 && start < end) {
					date = content.substring(start + DATE_START.length(), end);
				}
			}

		} catch (ParserException e) {
			e.printStackTrace();
		} 
		return date;
	}

	private String getBody(String text) {
		String body = "";
		int start = text.indexOf(BODY_START);
		int end = text.indexOf(BODY_END);
		if (start != -1 && end != -1 && start < end) {
			body = text.substring(start + BODY_START.length(), end);
		}
		return body;
	}

	private String getHeader(String text) {
		String header = "";
		try {
			Parser parser = new Parser(text);
			TagNameFilter filter = new TagNameFilter("title");
			NodeList list = parser.parse(filter);
			if (list.size() == 1) {
				TitleTag title = (TitleTag) list.elementAt(0);
				header = title.getStringText().trim();
			}
		} catch (ParserException e) {
			e.printStackTrace();
		}
		return header;
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
					ret += " " + line.trim();
				}
			}
		}
		return ret.trim();
	}

	private boolean isEmpty(String line) {
		return line.isEmpty();
	}

	private boolean isQuote(String line) {
		return line.startsWith("</I>&gt;<i>") || line.startsWith("&gt;") || line.endsWith("wrote:");
	}

	private boolean isCode(String line) {
		boolean leadingSpaces = line.startsWith("   ");
		line = line.trim();
		return leadingSpaces || line.endsWith(";") || line.contains("{") || line.contains("}");
	}
}
