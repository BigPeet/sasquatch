package retrieval.general;

import java.util.ArrayList;

import manager.parser.mail.MailParser;
import manager.systems.source.mail.Mail;
import retrieval.interfaces.ICrawlController;

public abstract class SeleniumCrawlController implements ICrawlController {

	private SeleniumCrawler crawler;
	private MailParser parser;
	private ArrayList<Mail> mails = new ArrayList<Mail>();
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
		this.parser = parser;
		this.listName = listName;
		this.start = startYear;
		this.end = endYear;	
	}
	
	public SeleniumCrawlController(MailParser parser, SeleniumCrawler crawler, String listName, int startYear, int endYear) {
		this.parser = parser;
		this.crawler = crawler;
		this.listName = listName;
		this.start = startYear;
		this.end = endYear;	
	}
	
	
	@Override
	public void run() {
		crawler.run();
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
	
	public Mail[] getMails() {
		return mails.toArray(new Mail[mails.size()]);
	}

	@Override
	public Object[] getData() {
		return getMails();
	}

	@Override
	public void saveData() {
		if (parser != null && parser.getHandler() != null) {
			for (Mail m : mails) {
				parser.writeMailToFile(m);
			}
		}
	}
	
	/**
	 * @return the parser
	 */
	public MailParser getParser() {
		return parser;
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
	 * @param parser the parser to set
	 */
	public void setParser(MailParser parser) {
		this.parser = parser;
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
