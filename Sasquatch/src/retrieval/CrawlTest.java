package retrieval;

import retrieval.mailinglist.jboss.JBossMLCrawlController;

public class CrawlTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JBossMLCrawlController controller = new JBossMLCrawlController();
		controller.run();
	}

}
