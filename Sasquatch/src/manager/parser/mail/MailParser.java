package manager.parser.mail;

import java.io.File;

import manager.parser.general.SourceParser;
import manager.systems.source.Source;
import manager.systems.source.mail.LocalMailHandler;
import manager.systems.source.mail.Mail;

public abstract class MailParser extends SourceParser {
	
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
	
	@Override
	public Source parseSource(String text) {
		return parseMail(text);
	}

	@Override
	public void saveSource(Source source) {
		if (source instanceof Mail) {
			writeMailToFile((Mail) source);
		}
	}
}
