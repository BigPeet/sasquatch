package retrieval.mailinglist.pipermail;

import java.util.ArrayList;
import java.util.List;

import manager.parser.mail.MailParser;
import manager.systems.source.mail.Mail;
import retrieval.general.Crawler4jCrawlController;
import retrieval.general.Crawler4jCrawler;
import retrieval.general.Crawler4jControllerConfiguration;
import retrieval.mailinglist.TextCollector;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

public class JBossCrawlController extends Crawler4jCrawlController {
	
	//Not used yet
	private String storageFolder;
	private MailParser parser;
	private ArrayList<Mail> mails = new ArrayList<Mail>();

	public JBossCrawlController() {

	}
	
	public JBossCrawlController(MailParser parser) {
		this.parser = parser;
	}

	public JBossCrawlController(Crawler4jControllerConfiguration config) {
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
		config.setIncludeHttpsPages(true);
		
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
			Crawler4jCrawler.setSeedDomains(this.getConfig().getDomains());
			Crawler4jCrawler.setFilters(this.getConfig().getFilters());
			Crawler4jCrawler.setStorageFolder(this.getConfig().getStorageFolder());
			Crawler4jCrawler.setTrigger(this.getConfig().getTrigger());
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

	@Override
	public Object[] getData() {
		return getMails();
	}

	@Override
	public void saveData() {
		// TODO Auto-generated method stub
		
	}
}
