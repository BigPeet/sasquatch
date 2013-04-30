package crawler.mailinglist;

import crawler.general.AbstractCrawlController;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

public abstract class MailingListCrawlController extends AbstractCrawlController {

	private String storageFolder;

	public void setStorageFolder(String folder) {
		this.storageFolder = folder;
	}

	public String getStorageFolder() {
		return storageFolder;
	}

	@Override
	public void run() {
		CrawlConfig config = new CrawlConfig();

		config.setCrawlStorageFolder(this.getRootFolder());
		config.setIncludeBinaryContentInCrawling(true);

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
			for (String domain : this.getDomains()) {
				controller.addSeed(domain);
			}
			MailingListCrawler.configure(this.getDomains(), this.getStorageFolder());
			controller.start(MailingListCrawler.class, this.getNumberOfCrawlers());
		}
	}
}
