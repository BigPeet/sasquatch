package crawler.mailinglist.jboss;

import crawler.mailinglist.MailingListCrawler;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.url.WebURL;

public class JBossMLCrawler extends MailingListCrawler {

	@Override
	public boolean shouldVisit(WebURL url) {
		return false;
	}

	@Override
	public void visit(Page page) {
		
	}
}
