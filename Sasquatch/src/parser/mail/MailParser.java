package parser.mail;

import java.io.File;

import parser.general.AbstractParser;
import systems.source.mail.LocalMailHandler;
import systems.source.mail.Mail;

public abstract class MailParser extends AbstractParser {
	
	//private File target;
	private LocalMailHandler handler;
	
	public MailParser() {
		
	}
	
	public MailParser(String path) {
		//this.target = new File(path);
		this.handler = new LocalMailHandler(new File(path));
	}
	
	public MailParser(LocalMailHandler handler) {
		this.handler = handler;
	}
	
	public abstract Mail parseMail(String text);

	public void writeMailToFile(Mail m) {
		if (!m.getBody().isEmpty()) {
			handler.addMail(m);
		}
	}
	
	public void clearFile() {
		handler.clear();
	}
	
	public LocalMailHandler getHandler() {
		return this.handler;
	}
}
