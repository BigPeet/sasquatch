package manager.parser.mail.appleList;

import org.htmlparser.Parser;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.BodyTag;
import org.htmlparser.tags.TitleTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import manager.parser.mail.MailParser;
import manager.systems.source.mail.LocalMailHandler;
import manager.systems.source.mail.Mail;

public class AppleListMailParser extends MailParser {
	
	private static final String UNDERLINE = "\n<hr>\n";
	private static final String BLOCKQUOUTE_OPENING = "<blockquote style=\"border-left: #5555EE solid 0.2em; margin: 0em; padding-left: 0.85em\">";
	private static final String BLOCKQUOUTE_CLOSING = "</blockquote>";
	private static final Object MAIL_END = "_______________________________________________";
	private int openQuoteBlocks = 0;
	
	public AppleListMailParser() {

	}


	public AppleListMailParser(String path) {
		super(path);
	}

	public AppleListMailParser(LocalMailHandler handler) {
		super(handler);
	}

	@Override
	public Mail parseMail(String text) {
		String header = getHeader(text);
		String body = getBody(text);
		body = parseBody(body);
		System.out.println(header);
		System.out.println(body);
		return new Mail(header, body);
	}
	
	private String parseBody(String text) {
		openQuoteBlocks = 0;
		String ret = "";
		text = text.trim();
		text = text.replace("<br>", "\n");
		String[] lines = text.split("\n");
		for (String line : lines) {
			if (!isQuote(line)) {
				line = line.replaceAll("<(.)*>", "").trim();
				if (!(isCode(line) || isEmpty(line))) {
					line = line.replace(BLOCKQUOUTE_CLOSING, "");
					if (isEnd(line)) {
						break;
					} else {
						line = line.replaceAll("<(.)*>", "");
						ret += " " + line.trim();
					}
				}
			}
			
		}
		return ret.trim();
	}

	private boolean isEnd(String line) {
		return line.trim().equals(MAIL_END);
	}


	private boolean isEmpty(String line) {
		//line = line.replaceAll("<(.)*>", "");
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
		return (openQuoteBlocks != 0) || line.endsWith("wrote:") || line.startsWith("&gt;");
	}


	private String getBody(String htmlText) {
		String body = "";
		
		try {
			Parser parser = new Parser(htmlText);
			TagNameFilter bodyFilter = new TagNameFilter("body");
			NodeList nodes = parser.parse(bodyFilter);
			if (nodes.size() == 1) {
				BodyTag tTag = (BodyTag) nodes.elementAt(0);
				String htmlBody = tTag.getStringText();
				int firstUnderline = htmlBody.indexOf(UNDERLINE) + UNDERLINE.length();
				int start = htmlBody.indexOf(UNDERLINE, firstUnderline) + UNDERLINE.length();
				int end = htmlBody.indexOf(UNDERLINE, start);
				body = htmlBody.substring(start, end);
			}
		} catch (ParserException e) {
			e.printStackTrace();
		}
		return body;
	}


	private String getHeader(String htmlText) {
		String header = "";
		try {
			Parser parser = new Parser(htmlText);
			TagNameFilter titleFilter = new TagNameFilter("title");
			NodeList titles = parser.parse(titleFilter);
			if (titles.size() == 1) {
				TitleTag tTag = (TitleTag) titles.elementAt(0);
				header = tTag.getStringText();
			}
		} catch (ParserException e) {
			e.printStackTrace();
		}
		return header;
	}

}
