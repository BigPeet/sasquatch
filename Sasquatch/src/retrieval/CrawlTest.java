package retrieval;

import retrieval.mailinglist.apache.ApacheCrawlController;
import retrieval.mailinglist.pipermail.JBossMLCrawlController;

public class CrawlTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JBossMLCrawlController controller = new JBossMLCrawlController();
		controller.run();
//		String listName = "hc-httpclient-users";
//		String path = "res/mails/httpclient.xml";
//		ApacheCrawlController controller = new ApacheCrawlController(path, listName, 2013, 2013);
//		controller.run();
	}

}
