package retrieval.mailinglist.appleList;

import org.htmlparser.Parser;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.Html;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import retrieval.general.Crawler4jCrawler;
import retrieval.mailinglist.TextCollector;

public class AppleListCrawler extends Crawler4jCrawler {

	public AppleListCrawler() {
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
		
		String ret = "";
		try {
			Parser parser = new Parser(htmlContent.getHtml());
			TagNameFilter tagFilter = new TagNameFilter("html");
			NodeList list = parser.parse(tagFilter);
			if (list.size() == 2) {
				Html doc = (Html) list.elementAt(1);
				ret = doc.getStringText();
			}
		} catch (ParserException e) {
			e.printStackTrace();
		}
		
		return ret;
	}

}
