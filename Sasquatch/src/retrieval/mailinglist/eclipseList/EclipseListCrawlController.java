package retrieval.mailinglist.eclipseList;

import manager.parser.mail.MailParser;
import manager.parser.mail.eclipseList.EclipseListMailParser;
import retrieval.general.SeleniumCrawlController;

public class EclipseListCrawlController extends SeleniumCrawlController {

	private int pages;

	public EclipseListCrawlController(String path, String listName, int startYear, int endYear, int pages) {
		super(listName, startYear, endYear);
		setParser(new EclipseListMailParser(path));
		this.pages = pages;
		setCrawler(new EclipseListCrawler(listName, pages));
	}
	
	public EclipseListCrawlController(String path, String listName, int startYear, int endYear) {
		super(listName, startYear, endYear);
		setParser(new EclipseListMailParser(path));
		this.pages = -1;
		setCrawler(new EclipseListCrawler(listName, pages));
	}
	
	public EclipseListCrawlController(MailParser parser, String listName, int startYear, int endYear, int pages) {
		super(parser, listName, startYear, endYear);
		this.pages = pages;
		setCrawler(new EclipseListCrawler(listName, pages));
	}
	
	public EclipseListCrawlController(MailParser parser, String listName, int startYear, int endYear) {
		super(parser, listName, startYear, endYear);
		this.pages = -1;
		setCrawler(new EclipseListCrawler(listName, pages));
	}
	
	public EclipseListCrawlController(String listName, int startYear, int endYear, int pages) {
		super(listName, startYear, endYear);
		this.pages = pages;
		setCrawler(new EclipseListCrawler(listName, pages));
	}
	
	public EclipseListCrawlController(String listName, int startYear, int endYear) {
		super(listName, startYear, endYear);
		this.pages = -1;
		setCrawler(new EclipseListCrawler(listName, pages));
	}
}
