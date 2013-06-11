package manager.systems.source.mail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.util.ArrayList;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import manager.systems.source.LocalSourceHandler;
import manager.systems.source.SourceHandler;
import manager.systems.source.Source;


public class LocalMailHandler extends LocalSourceHandler {
	
	private File target;
	
	public LocalMailHandler(File target) {
		this.target = target;
	}
	
	public File getFile() {
		return target;
	}
	
	public void addMail(Mail m) {
		Document doc = getDocument(target);
		
		if (doc != null) {
			Element root = doc.getRootElement();
			Element mail = new Element("mail");
			mail.addContent(new Element("header").setText(m.getHeader()));
			mail.addContent(new Element("body").setText(m.getBody()));
			root.addContent(mail);
			writeDocument(doc, target);
		}
		
	}
	
	public Mail[] getMails() {
		Document doc = getDocument(target);
		ArrayList<Mail> mails = new ArrayList<Mail>();
		if (doc != null) {
			Element root = doc.getRootElement();
			for (Object o : root.getChildren("mail")) {
				Element e = (Element) o;
				String header = e.getChildText("header");
				String body = e.getChildText("body");
				mails.add(new Mail(header, body));
			}
		}
		return mails.toArray(new Mail[mails.size()]);
	}
	
	public void clear() {
		Document doc = getDocument(target);
		if (doc != null) {
			Element root = doc.getRootElement();
			root.removeContent();
			writeDocument(doc, target);
		}
	}
	
	private static void writeDocument(Document doc, File f) {
		XMLOutputter xmlOutput = new XMLOutputter();
		xmlOutput.setFormat(Format.getPrettyFormat());
		try {
			FileOutputStream os = new FileOutputStream(f);
			OutputStreamWriter writer = new OutputStreamWriter(os, "UTF-8");
			xmlOutput.output(doc, writer);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static Document getDocument(File f) {
		SAXBuilder builder = new SAXBuilder();
		Document doc = null;
		try {
			InputStream inputStream= new FileInputStream(f);
    	    Reader reader = new InputStreamReader(inputStream,"UTF-8");
			doc = builder.build(reader);
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return doc;
	}

	@Override
	public Source[] getSources() {
		return getMails();
	}

	@Override
	public void addSource(Source s) {
		if (s instanceof Mail) {
			addMail((Mail) s);
		}
	}
	
}
