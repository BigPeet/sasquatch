package crawler.interfaces;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.url.WebURL;

public interface ICrawler {

	public boolean shouldVisit(WebURL url);
	public void visit(Page page);
}
