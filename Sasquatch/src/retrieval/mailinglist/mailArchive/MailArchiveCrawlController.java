package retrieval.mailinglist.mailArchive;

import manager.parser.mail.MailParser;
import manager.parser.mail.mailArchive.MailArchiveParser;
import retrieval.general.SeleniumCrawlController;

public class MailArchiveCrawlController extends SeleniumCrawlController {
	
	private int pages;

	public MailArchiveCrawlController(String path, String listName, int startYear, int endYear, int pages) {
		super(listName, startYear, endYear);
		setParser(new MailArchiveParser(path));
		this.pages = pages;
		setCrawler(new MailArchiveCrawler(listName, pages));
	}
	
	public MailArchiveCrawlController(String path, String listName, int startYear, int endYear) {
		super(listName, startYear, endYear);
		setParser(new MailArchiveParser(path));
		this.pages = -1;
		setCrawler(new MailArchiveCrawler(listName, pages));
	}
	
	public MailArchiveCrawlController(MailParser parser, String listName, int startYear, int endYear, int pages) {
		super(parser, listName, startYear, endYear);
		this.pages = pages;
		setCrawler(new MailArchiveCrawler(listName, pages));
	}
	
	public MailArchiveCrawlController(MailParser parser, String listName, int startYear, int endYear) {
		super(parser, listName, startYear, endYear);
		this.pages = -1;
		setCrawler(new MailArchiveCrawler(listName, pages));
	}
	
	public MailArchiveCrawlController(String listName, int startYear, int endYear, int pages) {
		super(listName, startYear, endYear);
		this.pages = pages;
		setParser(new MailArchiveParser());
		setCrawler(new MailArchiveCrawler(listName, pages));
	}
	
	public MailArchiveCrawlController(String listName, int startYear, int endYear) {
		super(listName, startYear, endYear);
		this.pages = -1;
		setParser(new MailArchiveParser());
		setCrawler(new MailArchiveCrawler(listName, pages));
	}
}
