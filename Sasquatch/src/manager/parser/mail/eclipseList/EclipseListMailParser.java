package manager.parser.mail.eclipseList;

import org.htmlparser.Parser;
import org.htmlparser.filters.TagNameFilter;
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
		String body = getBody(text);
		body = parseBody(body);
		return new Mail(header, body);
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
