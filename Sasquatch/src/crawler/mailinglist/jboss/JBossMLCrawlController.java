package crawler.mailinglist.jboss;

import crawler.mailinglist.MailingListCrawlController;

public class JBossMLCrawlController extends MailingListCrawlController {
	
	private static String[] DOMAINS = {"http://lists.jboss.org/pipermail/jboss-user/"};
	private static String ROOT = "res\\crawler\\root";
	private static int MAX_PAGES = 1000;
	private static int NUM_OF_CRAWLERS = 4;
	private static long DELAY = 200;
	
	public JBossMLCrawlController(String[] domains, String root, int maxPages, 
			int numberOfCrawlers, long delay) {
		initialize(domains, root, maxPages, numberOfCrawlers, delay);
	}
	
	public JBossMLCrawlController() {
		initialize(DOMAINS, ROOT, MAX_PAGES, NUM_OF_CRAWLERS, DELAY);
	}
	
	private void initialize(String[] domains, String root, int maxPages, 
			int numberOfCrawlers, long delay) {
		this.setSeedDomains(domains);
		this.setDelay(delay);
		this.setNumberOfCrawlers(numberOfCrawlers);
		this.setMaxPagesToFetch(maxPages);
		this.setRootFolder(root);
	}
}
