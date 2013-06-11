package retrieval.mailinglist.markmail;

import java.util.ArrayList;

import manager.parser.mail.MailParser;
import manager.parser.mail.markmail.MMMailParser;
import manager.systems.source.mail.Mail;
import retrieval.general.CrawlStat;
import retrieval.interfaces.ICrawlController;

public class MMCrawlController implements ICrawlController {
	
	private MMCrawler crawler = new MMCrawler();
	private MailParser parser = null;
	private String listName;
	private int pages;
	private ArrayList<Mail> mails = new ArrayList<Mail>();
	
	public MMCrawlController(String path, String listName, int pages) {
		parser = new MMMailParser(path);
		this.listName = listName;
		this.pages = pages;
	}
	
	public MMCrawlController(String path, String listName) {
		parser = new MMMailParser(path);
		this.listName = listName;
		this.pages = -1;
	}
	
	public MMCrawlController(MailParser parser, String listName, int pages) {
		this.parser = parser;
		this.listName = listName;
		this.pages = pages;
	}
	
	public MMCrawlController(MailParser parser, String listName) {
		this.parser = parser;
		this.listName = listName;
		this.pages = -1;
	}
	
	public MMCrawlController(String listName, int pages) {
		this.listName = listName;
		this.pages = pages;
	}
	
	public MMCrawlController(String listName) {
		this.listName = listName;
		this.pages = -1;
	}
	
	public Mail[] getMails() {
		return mails.toArray(new Mail[mails.size()]);
	}
	
	@Override
	public void run() {
		crawler.run(listName, pages);
		CrawlStat runStat = (CrawlStat) crawler.getMyLocalData();
		if (parser != null) {
			parser.clearFile();
			for (Object o : runStat.getData()) {
				String s = (String) o;
				Mail m = parser.parseMail(s);
				if (!mails.contains(m)) {
					mails.add(m);
					parser.writeMailToFile(m);
				}
			}
		}
	}

	@Override
	public Object[] getData() {
		return getMails();
	}


}
