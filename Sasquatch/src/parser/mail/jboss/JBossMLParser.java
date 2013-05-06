package parser.mail.jboss;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import crawler.mailinglist.Mail;
import parser.mail.MailParser;

public class JBossMLParser extends MailParser {

	private String separator = "--------------------------------------------------------------\n";
	private String separator2 = "--------------------------------------------------\n";
	private File target;
	
	public JBossMLParser(String path) {
		target = new File(path);
	}
	
	public void clearFile() {
		SAXBuilder builder = new SAXBuilder();
		Document doc = null;
		try {
			doc = builder.build(target);
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (doc != null) {
			Element root = doc.getRootElement();
			root.removeContent();
			XMLOutputter xmlOutput = new XMLOutputter();
			xmlOutput.setFormat(Format.getPrettyFormat());
			try {
				xmlOutput.output(doc, new FileWriter(target));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
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
			line = line.replace(" ", " ");
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

	@Override
	public void writeMailToFile(Mail m) {
		if (!m.getBody().isEmpty()) {
			SAXBuilder builder = new SAXBuilder();
			Document doc = null;
			try {
				doc = builder.build(target);
			} catch (JDOMException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (doc != null) {
				Element root = doc.getRootElement();
				Element mail = new Element("mail");
				mail.addContent(new Element("header").setText(m.getHeader()));
				mail.addContent(new Element("body").setText(m.getBody()));
				root.addContent(mail);
				XMLOutputter xmlOutput = new XMLOutputter();
				xmlOutput.setFormat(Format.getPrettyFormat());
				try {
					xmlOutput.output(doc, new FileWriter(target));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
