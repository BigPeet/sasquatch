package crawler.mailinglist;

import java.util.ArrayList;
import java.util.List;

import parser.mail.MailParser;
import systems.source.mail.Mail;
import crawler.general.AbstractCrawlController;
import crawler.general.AbstractCrawler;
import crawler.general.GeneralControllerConfiguration;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

public class MailingListCrawlController extends AbstractCrawlController {

	private String storageFolder;
	private MailParser parser;
	private ArrayList<Mail> mails = new ArrayList<Mail>();

	public MailingListCrawlController() {

	}

	public MailingListCrawlController(GeneralControllerConfiguration config) {
		super(config);
	}

	public void setStorageFolder(String folder) {
		this.storageFolder = folder;
	}

	public String getStorageFolder() {
		return storageFolder;
	}

	@Override
	public void run() {
		CrawlConfig config = new CrawlConfig();

		config.setCrawlStorageFolder(this.getConfig().getRootFolder());
		
		config.setMaxPagesToFetch(this.getConfig().getMaxPages());
		config.setPolitenessDelay((int) this.getConfig().getDelay());
		config.setMaxDepthOfCrawling(this.getConfig().getMaxDepth());
		
		PageFetcher pageFetcher = new PageFetcher(config);
		RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
		RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
		CrawlController controller = null;

		try {
			controller = new CrawlController(config, pageFetcher, robotstxtServer);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (controller != null) {
			for (String domain : this.getConfig().getDomains()) {
				controller.addSeed(domain);
			}
			AbstractCrawler.setSeedDomains(this.getConfig().getDomains());
			AbstractCrawler.setFilters(this.getConfig().getFilters());
			AbstractCrawler.setStorageFolder(this.getConfig().getStorageFolder());
			AbstractCrawler.setTrigger(this.getConfig().getTrigger());
			controller.start(this.getConfig().getCrawlerClass(), this.getConfig().getNumberOfCrawlers());
			
			System.out.println("Done crawling.");
			
			
			List<Object> crawlersLocalData = controller.getCrawlersLocalData();
			parser.clearFile();
			for (Object o : crawlersLocalData) {
				TextCollector c = (TextCollector) o;
				for (String t : c.getTextData()) {
					Mail m = parser.parseMail(t);
					if (!mails.contains(m)) {
						mails.add(m);
						parser.writeMailToFile(m);
					}
				}
			}
		}
	}
	
	public Mail[] getMails() {
		return mails.toArray(new Mail[mails.size()]);
	}

	/**
	 * @return the parser
	 */
	public MailParser getParser() {
		return parser;
	}

	/**
	 * @param parser the parser to set
	 */
	public void setParser(MailParser parser) {
		this.parser = parser;
	}
}
