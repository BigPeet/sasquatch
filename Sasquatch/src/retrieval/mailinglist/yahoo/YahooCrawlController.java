package retrieval.mailinglist.yahoo;

import manager.parser.mail.MailParser;
import manager.parser.mail.yahoo.YahooMailParser;
import retrieval.general.SeleniumCrawlController;

public class YahooCrawlController extends SeleniumCrawlController {
	
	private int pages;

	public YahooCrawlController(String path, String listName, int startYear, int endYear, int pages) {
		super(listName, startYear, endYear);
		setParser(new YahooMailParser(path));
		this.pages = pages;
		setCrawler(new YahooCrawler(listName, pages));
	}
	
	public YahooCrawlController(String path, String listName, int startYear, int endYear) {
		super(listName, startYear, endYear);
		setParser(new YahooMailParser(path));
		this.pages = -1;
		setCrawler(new YahooCrawler(listName, pages));
	}
	
	public YahooCrawlController(MailParser parser, String listName, int startYear, int endYear, int pages) {
		super(parser, listName, startYear, endYear);
		this.pages = pages;
		setCrawler(new YahooCrawler(listName, pages));
	}
	
	public YahooCrawlController(MailParser parser, String listName, int startYear, int endYear) {
		super(parser, listName, startYear, endYear);
		this.pages = -1;
		setCrawler(new YahooCrawler(listName, pages));
	}
	
	public YahooCrawlController(String listName, int startYear, int endYear, int pages) {
		super(listName, startYear, endYear);
		this.pages = pages;
		setCrawler(new YahooCrawler(listName, pages));
	}
	
	public YahooCrawlController(String listName, int startYear, int endYear) {
		super(listName, startYear, endYear);
		this.pages = -1;
		setCrawler(new YahooCrawler(listName, pages));
	}

}
