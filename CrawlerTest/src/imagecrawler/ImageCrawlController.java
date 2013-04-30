package imagecrawler;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

/**
 * @author Yasser Ganjisaffar <lastname at gmail dot com>
 */

/*
 * IMPORTANT: Make sure that you update crawler4j.properties file and set
 * crawler.include_images to true
 */

public class ImageCrawlController {

        public static void main(String[] args) throws Exception {
        	
        		String parent = "C:\\Users\\Peter Wolf\\Documents\\CrawlTest";
                String rootFolder = parent + "\\root";
                int numberOfCrawlers = 1;
                String storageFolder = parent + "\\storage";

                CrawlConfig config = new CrawlConfig();

                config.setCrawlStorageFolder(rootFolder);

                /*
                 * Since images are binary content, we need to set this parameter to
                 * true to make sure they are included in the crawl.
                 */
                config.setIncludeBinaryContentInCrawling(true);

                String[] crawlDomains = new String[] { "http://www.vdst-karlsruhe.de/" };

                PageFetcher pageFetcher = new PageFetcher(config);
                RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
                RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
                CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);
                for (String domain : crawlDomains) {
                        controller.addSeed(domain);
                }

                ImageCrawler.configure(crawlDomains, storageFolder);

                controller.start(ImageCrawler.class, numberOfCrawlers);
        }

}
