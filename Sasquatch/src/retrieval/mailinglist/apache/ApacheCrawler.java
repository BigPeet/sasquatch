package retrieval.mailinglist.apache;

import java.util.ArrayList;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.tags.TableColumn;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import retrieval.general.SeleniumCrawler;
import retrieval.mailinglist.TextCollector;

public class ApacheCrawler extends SeleniumCrawler {

	private static final String baseURL = "http://mail-archives.apache.org/mod_mbox/";
	private static final String mailURL = ".mbox/thread?0";
	private static final String mailView = ".mbox/ajax/";

	private int start;
	private int end;

	public ApacheCrawler(String listName, int start, int end) {
		super(listName);
		this.start = start;
		this.end = end;
		setStat(new TextCollector());
	}

	@Override
	public void run() {
		for (String link : getPageLinks()) {
			getDriver().get(link);
			for (String mailLink : getMailLinks(link, getDriver().getPageSource())) {
				getDriver().get(mailLink);
				String content = getDriver().getPageSource();
				getStat().addData(content);
			}
		}
	}

	private String buildMonthLink(int year, int month) {
		String dMonth = String.format("%02d", month);
		return baseURL + getListName() + "/" + year + dMonth + mailURL;
	}
	
	private String buildMailLink(String base, String href) {
		return base.replace(mailURL, mailView) + href;
	}

	private String[] getMailLinks(String base, String pageSource) {
		ArrayList<String> pages = new ArrayList<String>();
		try {
			Parser parser = new Parser(pageSource);
			HasAttributeFilter classFilter = new HasAttributeFilter("class", "subject");
			TagNameFilter tagFilter = new TagNameFilter("td");
			AndFilter filter = new AndFilter(classFilter, tagFilter);
			NodeList list = parser.parse(filter);
			for (int i = 0; i < list.size(); i++) {
				TableColumn col = (TableColumn) list.elementAt(i);
				NodeList linkTags = col.searchFor(LinkTag.class, false);
				if (linkTags.size() == 1) {
					LinkTag linkTag = (LinkTag) linkTags.elementAt(0);
					String href = linkTag.getLink();
					pages.add(buildMailLink(base, href));
				}
			}
		} catch (ParserException e) {
			e.printStackTrace();
		}
		return pages.toArray(new String[pages.size()]);
	}
	
	private String[] getPageLinks() {
		ArrayList<String> pages = new ArrayList<String>();
		for (int year = start; year <= end; year++) {
			for (int month = 1; month < 13; month++) {
				pages.add(buildMonthLink(year, month));
			}
		}
		return pages.toArray(new String[pages.size()]);
	}

}
