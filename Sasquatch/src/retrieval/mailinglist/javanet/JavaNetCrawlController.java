package retrieval.mailinglist.javanet;

import manager.parser.mail.MailParser;
import manager.parser.mail.javanet.JavaNetMailParser;
import retrieval.general.SeleniumCrawlController;

public class JavaNetCrawlController extends SeleniumCrawlController {

	public JavaNetCrawlController(String path, String listName, int startYear, int endYear) {
		super(listName, startYear, endYear);
		setParser(new JavaNetMailParser(path));
		setCrawler(new JavaNetCrawler(listName, startYear, endYear));
	}
	
	public JavaNetCrawlController(MailParser parser, String listName, int startYear, int endYear) {
		super(parser, listName, startYear, endYear);
		setCrawler(new JavaNetCrawler(listName, startYear, endYear));
	}
	

	public JavaNetCrawlController(String listName, int startYear, int endYear) {
		super(listName, startYear, endYear);
		setParser(new JavaNetMailParser());
		setCrawler(new JavaNetCrawler(listName, startYear, endYear));
	}
}
