package manager.parser.mail.yahoo;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import manager.parser.mail.MailParser;
import manager.systems.source.mail.LocalMailHandler;
import manager.systems.source.mail.Mail;

public class YahooMailParser extends MailParser {
	
	private static final String HEADER_END = "\n                </a>";
	private static final String HEADER_END_ALT = "\n    </a>";
	private static final String BODY_START = "\n                <pre>";
	private static final String BODY_END = "\n                </pre>";
	private static final String END_LINE = "[Non-text portions of this message have been removed]";
	private static final String TAG = "<span class=\"smalltype\">";
	private static final String DATE_START = "Date:";
	private static final String DATE_FORMAT = "EEE MMM dd, yyyy hh:mm a";
	
	public YahooMailParser() {

	}

	public YahooMailParser(String path) {
		super(path);
	}

	public YahooMailParser(LocalMailHandler handler) {
		super(handler);
	}

	@Override
	public Mail parseMail(String text) {
		String header = "";
		String body = "";
		header = getHeader(text);
		body = getBody(text);
		body = parseBody(body);
		Date date = convertDate(getDateText(text));
		return new Mail(header, body, date);
	}

	private Date convertDate(String dateText) {
		Date date = null;
		if (!dateText.isEmpty()) {
			dateText = dateText.replace(" ", " ");
			try {
				DateFormat formatter = new SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH);
				date = formatter.parse(dateText);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return date;
	}

	private String getDateText(String text) {
		String date = "";
		
		int first = text.indexOf(TAG);
		if (first != -1) {
			int start = text.indexOf(TAG, first + TAG.length());
			if (start != -1) {
				int end = text.indexOf(TAG, start + TAG.length());
				text = text.substring(start + TAG.length(), end);
				if (text.contains(DATE_START)) {
					String[] lines = text.split("\n");
					if (lines.length == 9 && lines[2].trim().equals(DATE_START)) {
						date = lines[6].trim();
					}
				}
			}
		}
		return date;
	}

	private String parseBody(String text) {
		String ret = "";
		text = text.trim();
		String[] lines = text.split("\n");
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
		return line.contains((CharSequence) END_LINE);
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

	private String getHeader(String text) {
		String header = "";
		int mailNo = getMailNo(text);
		String tag = buildHeaderTag(mailNo);
		int start = text.indexOf(tag) + tag.length();
		int end = text.indexOf(HEADER_END);
		if (end == -1) {
			end = text.indexOf(HEADER_END_ALT);
		}
		if (start != -1 && end != -1) {
			header = text.substring(start, end).trim();
		}
		return header;
	}

	private int getMailNo(String text) {
		int mailNo = -1;
		int start = text.indexOf("#") + 1;
		int end = text.substring(start).indexOf("\n") + start;
		if (start != -1 && end != -1) {
			String no = text.substring(start, end).trim();
			mailNo = Integer.parseInt(no);
		}
		return mailNo;
	}

	private String getBody(String text) {
		String body = "";
		int start = text.indexOf(BODY_START) + BODY_START.length();
		int end = text.indexOf(BODY_END);
		if (start != -1 && end != -1) {
			body = text.substring(start, end).trim();
		}
		return body;
	}
	
	private String buildHeaderTag(int mailNo) {
		return mailNo + "?l=1\">";
	}

}
