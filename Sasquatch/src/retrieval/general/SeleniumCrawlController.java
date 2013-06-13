package retrieval.general;

import manager.parser.mail.MailParser;

public abstract class SeleniumCrawlController extends GeneralMailCrawlController {

	private SeleniumCrawler crawler;
	private String listName;
	private int start;
	private int end;
	
	public SeleniumCrawlController(String listName, int startYear, int endYear) {
		this.listName = listName;
		this.start = startYear;
		this.end = endYear;	
	}
	
	public SeleniumCrawlController(SeleniumCrawler crawler, String listName, int startYear, int endYear) {
		this.crawler = crawler;
		this.listName = listName;
		this.start = startYear;
		this.end = endYear;	
	}
	
	public SeleniumCrawlController(MailParser parser, String listName, int startYear, int endYear) {
		setParser(parser);
		this.listName = listName;
		this.start = startYear;
		this.end = endYear;	
	}
	
	public SeleniumCrawlController(MailParser parser, SeleniumCrawler crawler, String listName, int startYear, int endYear) {
		setParser(parser);
		this.crawler = crawler;
		this.listName = listName;
		this.start = startYear;
		this.end = endYear;	
	}
	
	
	@Override
	public void run() {
		crawler.run();
		CrawlStat[] runStat = {(CrawlStat) crawler.getMyLocalData()};
		collectResults(runStat);
	}


	/**
	 * @return the listName
	 */
	public String getListName() {
		return listName;
	}

	/**
	 * @return the start
	 */
	public int getStart() {
		return start;
	}

	/**
	 * @return the end
	 */
	public int getEnd() {
		return end;
	}

	/**
	 * @param listName the listName to set
	 */
	public void setListName(String listName) {
		this.listName = listName;
	}

	/**
	 * @param start the start to set
	 */
	public void setStart(int start) {
		this.start = start;
	}

	/**
	 * @param end the end to set
	 */
	public void setEnd(int end) {
		this.end = end;
	}


	/**
	 * @return the crawler
	 */
	public SeleniumCrawler getCrawler() {
		return crawler;
	}


	/**
	 * @param crawler the crawler to set
	 */
	public void setCrawler(SeleniumCrawler crawler) {
		this.crawler = crawler;
	}
	
}
