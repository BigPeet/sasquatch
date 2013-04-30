package collectingdata;

import java.util.List;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

public class LocalDataCollectorController {

	public static void main(String[] args) throws Exception {
		String parent = "C:\\Users\\Peter Wolf\\Documents\\CrawlTest";
		String rootFolder = parent + "\\root";
		int numberOfCrawlers = 6;
		
		String[] domains = {"http://lists.jboss.org/pipermail/jboss-user/"};
		LocalDataCollectorCrawler.configure(domains);
		

		CrawlConfig config = new CrawlConfig();
		config.setCrawlStorageFolder(rootFolder);
		config.setMaxPagesToFetch(10);
		config.setPolitenessDelay(1000);

		PageFetcher pageFetcher = new PageFetcher(config);
		RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
		RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
		CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

		for (String domain : domains) {
			controller.addSeed(domain);
		}
		controller.start(LocalDataCollectorCrawler.class, numberOfCrawlers);

		List<Object> crawlersLocalData = controller.getCrawlersLocalData();
		long totalLinks = 0;
		long totalTextSize = 0;
		int totalProcessedPages = 0;
		for (Object localData : crawlersLocalData) {
			CrawlStat stat = (CrawlStat) localData;
			totalLinks += stat.getTotalLinks();
			totalTextSize += stat.getTotalTextSize();
			totalProcessedPages += stat.getTotalProcessedPages();
		}
		System.out.println("Aggregated Statistics:");
		System.out.println("   Processed Pages: " + totalProcessedPages);
		System.out.println("   Total Links found: " + totalLinks);
		System.out.println("   Total Text Size: " + totalTextSize);
	}

}

