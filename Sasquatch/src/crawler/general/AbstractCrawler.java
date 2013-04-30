package crawler.general;

import crawler.interfaces.ICrawler;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.url.WebURL;

public abstract class AbstractCrawler extends WebCrawler implements ICrawler {

	private static String[] crawlDomains;
	
	@Override
	public abstract boolean shouldVisit(WebURL url);
	
	@Override
	public abstract void visit(Page page);
	
	public static void configure(String[] domains) {
		crawlDomains = domains;
	}
}
