package manager.parser.mail.yahoo;

import manager.parser.mail.MailParser;
import manager.systems.source.mail.LocalMailHandler;
import manager.systems.source.mail.Mail;

public class YahooMailParser extends MailParser {
	
	private static final String HEADER_END = "\n                </a>";
	private static final String HEADER_END_ALT = "\n    </a>";
	private static final String BODY_START = "\n                <pre>";
	private static final String BODY_END = "\n                </pre>";
	private static final Object END_LINE = "[Non-text portions of this message have been removed]";
	
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
		return new Mail(header, body);
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
