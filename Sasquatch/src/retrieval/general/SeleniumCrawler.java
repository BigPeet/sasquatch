package retrieval.general;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.gargoylesoftware.htmlunit.BrowserVersion;

import retrieval.interfaces.ICrawler;

public abstract class SeleniumCrawler implements ICrawler {

	private CrawlStat stat;
	private WebDriver driver = new HtmlUnitDriver(BrowserVersion.FIREFOX_17);
	private String listName;
	
	public SeleniumCrawler() {
		driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
	}
	
	public SeleniumCrawler(String listName) {
		driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
		this.listName = listName;
	}
	
	@Override
	public abstract void run();
	
	
	@Override
	public Object getMyLocalData() {
		return stat;
	}

	/**
	 * @return the stat
	 */
	public CrawlStat getStat() {
		return stat;
	}

	/**
	 * @return the listName
	 */
	public String getListName() {
		return listName;
	}

	/**
	 * @param stat the stat to set
	 */
	public void setStat(CrawlStat stat) {
		this.stat = stat;
	}

	/**
	 * @param listName the listName to set
	 */
	public void setListName(String listName) {
		this.listName = listName;
	}

	/**
	 * @return the driver
	 */
	public WebDriver getDriver() {
		return driver;
	}
}
