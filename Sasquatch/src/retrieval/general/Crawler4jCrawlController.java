package retrieval.general;

import java.util.List;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

import manager.parser.mail.MailParser;

public abstract class Crawler4jCrawlController extends GeneralMailCrawlController {

	private Crawler4jControllerConfiguration config;
	private String storageFolder;
	private int maxPages;

	
	public Crawler4jCrawlController(int maxPages) {
		this.maxPages = maxPages;
	}
	
	public Crawler4jCrawlController(Crawler4jControllerConfiguration config) {
		this.setConfig(config);
	}
	
	public Crawler4jCrawlController(Crawler4jControllerConfiguration config, MailParser parser) {
		this.setConfig(config);
		setParser(parser);
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
			
			List<Object> crawlersLocalData = controller.getCrawlersLocalData();
			CrawlStat[] runStat = new CrawlStat[crawlersLocalData.size()];
			crawlersLocalData.toArray(runStat);
			collectResults(runStat);
		}
	}

	public Crawler4jControllerConfiguration getConfig() {
		return config;
	}

	public void setConfig(Crawler4jControllerConfiguration config) {
		this.config = config;
	}
	


	/**
	 * @return the storageFolder
	 */
	public String getStorageFolder() {
		return storageFolder;
	}


	/**
	 * @param storageFolder the storageFolder to set
	 */
	public void setStorageFolder(String storageFolder) {
		this.storageFolder = storageFolder;
	}

	/**
	 * @return the maxPages
	 */
	public int getMaxPages() {
		return maxPages;
	}

	/**
	 * @param maxPages the maxPages to set
	 */
	public void setMaxPages(int maxPages) {
		this.maxPages = maxPages;
	}
}
