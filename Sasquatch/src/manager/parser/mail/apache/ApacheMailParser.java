package manager.parser.mail.apache;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import manager.parser.mail.MailParser;
import manager.systems.source.mail.LocalMailHandler;
import manager.systems.source.mail.Mail;

public class ApacheMailParser extends MailParser {
	
	private static final String DATE_FORMAT = "EEE, dd MMM yyyy HH:mm:ss z";

	public ApacheMailParser() {

	}


	public ApacheMailParser(String path) {
		super(path);
	}

	public ApacheMailParser(LocalMailHandler handler) {
		super(handler);
	}

	@Override
	public Mail parseMail(String text) {
		String header = "";
		String body = "";
		Date date = null;
		String subject = getTagText(text, "subject");
		header = subject;
		String content = getTagText(text, "contents");
		date= convertDate(getTagText(text, "date"));
		body = parseBody(content);
		return new Mail(header, body, date);
	}


	private Date convertDate(String tagText) {
		Date date = null;
		try {
			DateFormat formatter = new SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH);
			date = formatter.parse(tagText);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}


	private String parseBody(String text) {
		String ret = "";
		text = text.trim();
		String[] lines = text.split("&#010;");
		for (String line : lines) {
			if (!(isCode(line) || isQuote(line) || isEmpty(line))) {
				if (isEnd(line)) {
					break;
				} else {
					ret += " " + line.trim();
				}
			}
		}
		return ret.trim();
	}

	private boolean isEnd(String line) {
		line = line.trim();
		return line.equals("---------------------------------------------------------------------");
	}

	private boolean isEmpty(String line) {
		return line.isEmpty();
	}


	private boolean isQuote(String line) {
		return line.endsWith("wrote:") || line.startsWith("&gt;");
	}


	private boolean isCode(String line) {
		boolean leadingSpaces = line.startsWith("   ");
		line = line.trim();
		return leadingSpaces || line.endsWith(";") || line.contains("{") || line.contains("}") || line.startsWith("&lt;");
	}


	private String removeCDATATags(String text) {
		String retVal = "";
		retVal = text.replace("<![CDATA[", "");
		if (retVal.endsWith("]]>")) {
			retVal = retVal.substring(0, retVal.length() - 3);
		}
		return retVal;
	}


	private String getTagText(String text, String tag) {
		String retVal = "";
		int start = text.indexOf("<" + tag + ">");
		int end = text.indexOf("</" + tag + ">");
		if (start != -1 && end != -1 && (start + tag.length() + 2) < text.length()) {
			retVal = text.substring(start + tag.length() + 2, end);
			retVal = retVal.replace("\n", "");
			retVal = retVal.trim();
			retVal = removeCDATATags(retVal);
		}
		return retVal;
	}

}
