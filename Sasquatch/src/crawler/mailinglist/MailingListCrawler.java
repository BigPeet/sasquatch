package crawler.mailinglist;

import crawler.general.AbstractCrawler;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.parser.HtmlParseData;

public abstract class MailingListCrawler extends AbstractCrawler {
	
	public MailingListCrawler() {
		setCrawlStat(new TextCollector());
	}

	@Override
	public void visit(Page page) {
		String href = page.getWebURL().getURL();
		if (checkTrigger(href)) {
			/*Here has to be the routine that saves the data to the storage OR
			parses them first*/
			System.out.println(href);
			HtmlParseData htmlContent = (HtmlParseData) page.getParseData();
			String text = trimContent(htmlContent);
			getCrawlStat().addData(text);
		}
	}

	protected abstract String trimContent(HtmlParseData htmlContent);
	
}
