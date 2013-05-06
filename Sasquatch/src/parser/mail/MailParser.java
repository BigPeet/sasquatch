package parser.mail;

import crawler.mailinglist.Mail;
import parser.general.AbstractParser;

public abstract class MailParser extends AbstractParser {
	
	public abstract Mail parseMail(String text);

	public abstract void writeMailToFile(Mail m);
	
	public abstract void clearFile();
}
