package retrieval.general;

import java.util.ArrayList;

import manager.parser.mail.MailParser;
import manager.systems.source.mail.Mail;
import retrieval.interfaces.ICrawlController;

public abstract class GeneralMailCrawlController implements ICrawlController {

	private MailParser parser;
	private ArrayList<Mail> mails = new ArrayList<Mail>();
	
	@Override
	public abstract void run();
	
	protected void collectResults(CrawlStat[] stats) {
		for (CrawlStat stat : stats) {
			if (parser != null) {
				for (Object o : stat.getData()) {
					String s = (String) o;
					Mail m = parser.parseMail(s);
					if (!mails.contains(m)) {
						mails.add(m);
					}
				}
			}
		}
	}
	
	@Override
	public void saveData() {
		if (parser != null && parser.getHandler() != null) {
			parser.clearFile();
			for (Mail m : mails) {
				parser.writeMailToFile(m);
			}
		}
	}
		
	
	@Override
	public Object[] getData() {
		return getMails();
	}
	
	public Mail[] getMails() {
		return mails.toArray(new Mail[mails.size()]);
	}
	
	/**
	 * @return the parser
	 */
	public MailParser getParser() {
		return parser;
	}
	/**
	 * @param parser the parser to set
	 */
	public void setParser(MailParser parser) {
		this.parser = parser;
	}
	
}
