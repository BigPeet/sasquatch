package retrieval.mailinglist.sourceforge;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.gargoylesoftware.htmlunit.BrowserVersion;

import retrieval.general.CrawlStat;
import retrieval.general.SeleniumCrawler;

public class SFCrawler extends SeleniumCrawler {
	
	private static final String baseURL = "http://sourceforge.net";
	private static final String mailURL = "/mailarchive/forum.php?forum_name=";
	private static final String rowURL = "&max_rows=";
	private static final int nRow = 100;
	private static final String restURL = "&style=nested&viewmonth=";
	private static final String STRONG_START = "<strong>";
	private static final String STRONG_END = "</strong>";
	private static final String NEXT_MESSAGES_BUTTON = "Next Messages";
	
	
	private CrawlStat stat = new HTMLSFCollector();
	private WebDriver driver = new HtmlUnitDriver(BrowserVersion.FIREFOX_17);
	private String listName;
	private int start;
	private int end;

	public SFCrawler(String listName, int start, int end) {
		driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
		this.listName = listName;
		this.start = start;
		this.end = end;
	}

	@Override
	public Object getMyLocalData() {
		return stat;
	}


	private String buildMonthLink(int year, int month) {
		String dMonth = String.format("%02d", month);
		return baseURL + mailURL + listName + rowURL + nRow + restURL + year + dMonth;
	}

	@Override
	public void run() {
		driver.get(baseURL + mailURL + listName);
		
		for (String link : getPageLinks()) {
			
			driver.get(link);
			String content = driver.getPageSource();
			stat.addData(content);
			
			while (hasNextPage(content)) {
				WebElement e = driver.findElement(By.linkText(NEXT_MESSAGES_BUTTON));
				e.click();
				content = driver.getPageSource();
				stat.addData(content);
			}
		}
		//Not sure this is a good idea.
		//driver.close();
	}

	private boolean hasNextPage(String content) {
		return content.contains(NEXT_MESSAGES_BUTTON)
			&& content.contains(STRONG_START)
			&& content.contains(STRONG_END);
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
