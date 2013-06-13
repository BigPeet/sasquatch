package retrieval.mailinglist.sourceforge;

import manager.parser.mail.MailParser;
import manager.parser.mail.sourceforge.SFMailParser;
import retrieval.general.SeleniumCrawlController;

public class SFCrawlController extends SeleniumCrawlController {
	
	public SFCrawlController(String path, String listName, int startYear, int endYear) {
		super(listName, startYear, endYear);
		setParser(new SFMailParser(path));
		setCrawler(new SFCrawler(listName, startYear, endYear));
	}
	
	public SFCrawlController(MailParser parser, String listName, int startYear, int endYear) {
		super(parser, listName, startYear, endYear);
		setCrawler(new SFCrawler(listName, startYear, endYear));
	}
	
	public SFCrawlController(String listName, int startYear, int endYear) {
		super(listName, startYear, endYear);
		setCrawler(new SFCrawler(listName, startYear, endYear));
	}
}
