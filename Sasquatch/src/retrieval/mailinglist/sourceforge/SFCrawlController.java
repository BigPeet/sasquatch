package retrieval.mailinglist.sourceforge;

import java.util.ArrayList;


import manager.systems.source.mail.Mail;
import manager.parser.mail.MailParser;
import manager.parser.mail.sourceforge.SFMailParser;
import retrieval.general.CrawlStat;
import retrieval.general.SeleniumCrawlController;

public class SFCrawlController extends SeleniumCrawlController {
	
	private SFCrawler crawler;
	private MailParser parser = null;
	private String listName;
	private int start;
	private int end;
	private ArrayList<Mail> mails = new ArrayList<Mail>();
	
	public SFCrawlController(String path, String listName, int startYear, int endYear) {
		parser = new SFMailParser(path);
		this.listName = listName;
		this.start = startYear;
		this.end = endYear;
		crawler = new SFCrawler(listName, startYear, endYear);
	}
	
	public SFCrawlController(MailParser parser, String listName, int startYear, int endYear) {
		this.parser = parser;
		this.listName = listName;
		this.start = startYear;
		this.end = endYear;
		crawler = new SFCrawler(listName, startYear, endYear);
	}
	
	public SFCrawlController(String listName, int startYear, int endYear) {
		this.listName = listName;
		this.start = startYear;
		this.end = endYear;
		crawler = new SFCrawler(listName, startYear, endYear);
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

	@Override
	public Object[] getData() {
		return getMails();
	}

	@Override
	public void setSaveOption(boolean saveOn) {
		// TODO Auto-generated method stub
		
	}

}
