package retrieval.mailinglist.apache;

import manager.parser.mail.MailParser;
import manager.parser.mail.apache.ApacheMailParser;
import retrieval.general.SeleniumCrawlController;

public class ApacheCrawlController extends SeleniumCrawlController {
	
	
	public ApacheCrawlController(String path, String listName, int startYear, int endYear) {
		super(listName, startYear, endYear);
		setParser(new ApacheMailParser(path));
		setCrawler(new ApacheCrawler(listName, startYear, endYear));
	}
	
	public ApacheCrawlController(MailParser parser, String listName, int startYear, int endYear) {
		super(parser, listName, startYear, endYear);
		setCrawler(new ApacheCrawler(listName, startYear, endYear));
	}
	

	public ApacheCrawlController(String listName, int startYear, int endYear) {
		super(listName, startYear, endYear);
		setParser(new ApacheMailParser());
		setCrawler(new ApacheCrawler(listName, startYear, endYear));
	}
}
