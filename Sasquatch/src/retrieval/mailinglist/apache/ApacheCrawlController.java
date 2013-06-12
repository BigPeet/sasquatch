package retrieval.mailinglist.apache;

import manager.parser.mail.MailParser;
import manager.parser.mail.apache.ApacheMailParser;
import retrieval.general.CrawlStat;
import retrieval.interfaces.ICrawlController;
import java.util.ArrayList;
import manager.systems.source.mail.Mail;

public class ApacheCrawlController implements ICrawlController {
	
	private ApacheCrawler crawler;
	private MailParser parser = null;
	private String listName;
	private int start;
	private int end;
	private ArrayList<Mail> mails = new ArrayList<Mail>();
	private boolean saveOption = true;
	
	public ApacheCrawlController(String path, String listName, int startYear, int endYear) {
		parser = new ApacheMailParser(path);
		this.listName = listName;
		this.start = startYear;
		this.end = endYear;
		crawler = new ApacheCrawler(listName, startYear, endYear);
	}
	
	public void setSaveOption(boolean saveData) {
		this.saveOption = saveData;
	}
	
	public ApacheCrawlController(MailParser parser, String listName, int startYear, int endYear) {
		this.parser = parser;
		this.listName = listName;
		this.start = startYear;
		this.end = endYear;
		crawler = new ApacheCrawler(listName, startYear, endYear);
	}
	

	public ApacheCrawlController(String listName, int startYear, int endYear) {
		this.parser = new ApacheMailParser();
		this.listName = listName;
		this.start = startYear;
		this.end = endYear;	
		crawler = new ApacheCrawler(listName, startYear, endYear);
	}

	@Override
	public void run() {
		crawler.run();
		CrawlStat runStat = (CrawlStat) crawler.getMyLocalData();
		if (parser != null) {
			if (saveData()) {
				parser.clearFile();
			}
			for (Object o : runStat.getData()) {
				String s = (String) o;
				Mail m = parser.parseMail(s);
				if (!mails.contains(m)) {
					mails.add(m);
					if (saveData()) {
						parser.writeMailToFile(m);
					}
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
	
	private boolean saveData() {
		return saveOption && parser != null && parser.getHandler() != null;
	}
	

}
