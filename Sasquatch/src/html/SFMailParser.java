package html;

import parser.mail.MailParser;

import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.Div;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import systems.source.mail.LocalMailHandler;
import systems.source.mail.Mail;

public class SFMailParser extends MailParser {
	
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
		String header = "";
		String body = "";
		try {
			Parser parser = new Parser(text);
			header = getHeader(parser);
			body = getBody(text);
		} catch (ParserException e) {
			e.printStackTrace();
		}
		return new Mail(header, body);
	}

	private String getBody(String text) {
		String body = "";
		String preStart = "<pre>";
		String preEnd = "</pre>";
		int start = text.indexOf(preStart) + preStart.length();
		int end = text.indexOf(preEnd);
		if (start > -1 && end > -1) {
			body = parseBody(text.substring(start, end));
		}
		return body;
	}

	private String parseBody(String text) {
		String ret = "";
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
		return line.startsWith("&gt;") || line.contains("----- Original Message -----");
	}

	private boolean isCode(String line) {
		boolean leadingSpaces = line.startsWith("   ");
		line = line.trim();
		return leadingSpaces || line.endsWith(";") || line.contains("{") || line.contains("}") || line.startsWith("&lt;");
	}

	private String getHeader(Parser parser) {
		String header = "";
		TagNameFilter divFilter = new TagNameFilter("div");
		HasAttributeFilter forumFilter = new HasAttributeFilter("class", "forum");
		AndFilter andFilter = new AndFilter(divFilter, forumFilter);
		try {
			NodeList list = parser.parse(andFilter);
			Div div = (Div) list.elementAt(0);
			NodeList divList = div.searchFor(LinkTag.class, true);
			LinkTag link = (LinkTag) divList.elementAt(0);
			header = link.getStringText().trim();
		} catch (ParserException e) {
			e.printStackTrace();
		}
		return header;
	}
}
