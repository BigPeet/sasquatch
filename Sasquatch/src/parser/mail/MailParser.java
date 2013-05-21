package parser.mail;

import parser.general.AbstractParser;
import systems.source.mail.Mail;

public abstract class MailParser extends AbstractParser {
	
	public abstract Mail parseMail(String text);

	public abstract void writeMailToFile(Mail m);
	
	public abstract void clearFile();
}
