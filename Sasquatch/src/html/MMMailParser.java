package html;

import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.Div;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import parser.mail.MailParser;
import systems.source.mail.LocalMailHandler;
import systems.source.mail.Mail;

public class MMMailParser extends MailParser {

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

	private String getHeader(Parser parser) {
		String header = "";
		HasAttributeFilter attrFilter = new HasAttributeFilter("class", "subject");
		TagNameFilter tagFilter = new TagNameFilter("a");
		AndFilter filter = new AndFilter(attrFilter, tagFilter);
		try {
			NodeList list = parser.parse(filter);
			Node node = list.elementAt(0);
			header = node.toPlainTextString();
		} catch (ParserException e) {
			e.printStackTrace();
		}
		return header;
	}

}
