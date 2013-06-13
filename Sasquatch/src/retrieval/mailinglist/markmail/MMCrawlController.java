package retrieval.mailinglist.markmail;

import manager.parser.mail.MailParser;
import manager.parser.mail.markmail.MMMailParser;
import retrieval.general.SeleniumCrawlController;

public class MMCrawlController extends SeleniumCrawlController {
	
	private int pages;
	
	public MMCrawlController(String path, String listName, int startYear, int endYear, int pages) {
		super(listName, startYear, endYear);
		setParser(new MMMailParser(path));
		this.pages = pages;
		setCrawler(new MMCrawler(listName, pages));
	}
	
	public MMCrawlController(String path, String listName, int startYear, int endYear) {
		super(listName, startYear, endYear);
		setParser(new MMMailParser(path));
		this.pages = -1;
		setCrawler(new MMCrawler(listName, pages));
	}
	
	public MMCrawlController(MailParser parser, String listName, int startYear, int endYear, int pages) {
		super(parser, listName, startYear, endYear);
		this.pages = pages;
		setCrawler(new MMCrawler(listName, pages));
	}
	
	public MMCrawlController(MailParser parser, String listName, int startYear, int endYear) {
		super(parser, listName, startYear, endYear);
		this.pages = -1;
		setCrawler(new MMCrawler(listName, pages));
	}
	
	public MMCrawlController(String listName, int startYear, int endYear, int pages) {
		super(listName, startYear, endYear);
		this.pages = pages;
		setCrawler(new MMCrawler(listName, pages));
	}
	
	public MMCrawlController(String listName, int startYear, int endYear) {
		super(listName, startYear, endYear);
		this.pages = -1;
		setCrawler(new MMCrawler(listName, pages));
	}
}
