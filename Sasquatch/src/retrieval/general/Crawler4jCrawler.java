package retrieval.general;

import java.io.File;
import java.util.regex.Pattern;

import retrieval.interfaces.ICrawler;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.url.WebURL;

public abstract class Crawler4jCrawler extends WebCrawler implements ICrawler {

	private static File storageFolder;
	private static String[] crawlDomains;
	private static Pattern[] filters;
	private static Pattern trigger;

	private CrawlStat crawlData;

	public static void setSeedDomains(String[] domains) {
		crawlDomains = domains;
	}

	public static void setFilters(Pattern[] filterL) {
		filters = filterL;
	}

	public static void setStorageFolder(String folderName) {
		storageFolder = new File(folderName);
		if (!storageFolder.exists()) {
			storageFolder.mkdirs();
		}
	}

	public static void setTrigger(Pattern pat) {
		trigger = pat;
	}

	@Override
	public boolean shouldVisit(WebURL url) {
		String href = url.getURL().toLowerCase();
		return checkFilters(href) &&
				checkDomains(href);
	}

	private boolean checkDomains(String href) {
		for (String domain : crawlDomains) {
			if (href.startsWith(domain)) {
				return true;
			}
		}
		return false;
	}

	private boolean checkFilters(String href) {
		for (Pattern filter : filters) {
			if (filter.matcher(href).matches()) {
				return false;
			}
		}
		return true;
	}

	protected boolean checkTrigger(String href) {
		return trigger.matcher(href).matches();
	}

	public void setCrawlStat(CrawlStat stat) {
		crawlData = stat;
	}

	public CrawlStat getCrawlStat() {
		return crawlData;
	}

	@Override
	public Object getMyLocalData() {
		return crawlData;
	}

	@Override
	public abstract void visit(Page page);

}
