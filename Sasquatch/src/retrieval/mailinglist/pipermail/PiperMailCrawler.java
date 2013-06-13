package retrieval.mailinglist.pipermail;

import retrieval.general.Crawler4jCrawler;
import retrieval.mailinglist.TextCollector;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.parser.HtmlParseData;

public class PiperMailCrawler extends Crawler4jCrawler {
	
	public PiperMailCrawler() {
		setCrawlStat(new TextCollector());
	}

	@Override
	public void visit(Page page) {
		String href = page.getWebURL().getURL();
		if (checkTrigger(href)) {
			HtmlParseData htmlContent = (HtmlParseData) page.getParseData();
			String text = trimContent(htmlContent);
			getCrawlStat().addData(text);
		}
	}

	protected String trimContent(HtmlParseData htmlContent) {
		return htmlContent.getText();
	}
	
}
