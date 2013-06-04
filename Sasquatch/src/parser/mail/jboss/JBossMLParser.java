package parser.mail.jboss;

import parser.mail.MailParser;
import systems.source.mail.LocalMailHandler;
import systems.source.mail.Mail;

public class JBossMLParser extends MailParser {

	private String separator = "--------------------------------------------------------------\n";
	private String separator2 = "--------------------------------------------------\n";
	
	public JBossMLParser(String path) {
		super(path);
	}
	
	public JBossMLParser(LocalMailHandler handler) {
		super(handler);
	}
	
	@Override
	public Mail parseMail(String text) {
		String sep = getSeparator(text);
		String[] lines = text.split("\\n", 2);
		String header = lines[0];
		String[] tokens = lines[1].split(sep, 3);
		String body = "";
		if (tokens.length == 3) {
			body = tokens[1];
			body = parseBody(body);
		}
		return new Mail(header, body);
	}

	private String parseBody(String body) {
		String ret = "";
		String[] lines = body.split("\n");
		for (String line : lines) {
			//somehow a "trouble letter" slipped in
			//line = line.replace(" ", " ");
			if (!(isCode(line) || isQuote(line) || isEmpty(line))) {
				//System.out.println("Line: \"" + line + "\"");
				ret += " " + line.trim();
			}
		}
		ret = ret.trim();
		return ret;
	}
	
	private boolean isEmpty(String line) {
		boolean empty = true;
		String[] token = line.split("\\s+");
		for (int i = 0; i < token.length && empty; i++) {
			empty = token[i].isEmpty();
		}
		return empty;
	}

	private boolean isQuote(String line) {
		return line.startsWith(">");
	}

	private boolean isCode(String line) {
		boolean leadingSpaces = line.startsWith("   ");
		line = line.trim();
		return leadingSpaces || line.endsWith(";") || line.contains("{") || line.contains("}");
	}

	private String getSeparator(String text) {
		String ret = "";
		if (text.contains(separator)) {
			ret = separator;
		} else if (text.contains(separator2)) {
			ret = separator2;
		}
		return ret;
	}

}
