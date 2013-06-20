package retrieval.general;

import java.util.regex.Pattern;
import edu.uci.ics.crawler4j.crawler.WebCrawler;

public class Crawler4jControllerConfiguration {
	
	private String[] domains;
	private int numberOfCrawlers;
	private int maxPages;
	private int maxDepth;
	private String rootFolder;
	private String storageFolder;
	private long delay;
	private Class<? extends WebCrawler> crawlerClass;
	private Pattern[] filters;
	private Pattern trigger;
	
	
	public Crawler4jControllerConfiguration(String[] domains,
			int numberOfCrawlers, int maxPages, int maxDepth,
			String rootFolder, String storageFolder, long delay,
			Class<? extends WebCrawler> crawler, Pattern[] filters,
			Pattern trigger) {
		super();
		this.domains = domains;
		this.numberOfCrawlers = numberOfCrawlers;
		this.maxPages = maxPages;
		this.maxDepth = maxDepth;
		this.rootFolder = rootFolder;
		this.storageFolder = storageFolder;
		this.delay = delay;
		this.crawlerClass = crawler;
		this.filters = filters;
		this.trigger = trigger;
	}
	
	/**
	 * @return the domains
	 */
	public String[] getDomains() {
		return domains;
	}
	/**
	 * @return the numberOfCrawlers
	 */
	public int getNumberOfCrawlers() {
		return numberOfCrawlers;
	}
	/**
	 * @return the maxPages
	 */
	public int getMaxPages() {
		return maxPages;
	}
	/**
	 * @return the maxDepth
	 */
	public int getMaxDepth() {
		return maxDepth;
	}
	/**
	 * @return the rootFolder
	 */
	public String getRootFolder() {
		return rootFolder;
	}
	/**
	 * @return the storageFolder
	 */
	public String getStorageFolder() {
		return storageFolder;
	}
	/**
	 * @return the delay
	 */
	public long getDelay() {
		return delay;
	}
	/**
	 * @param domains the domains to set
	 */
	public void setDomains(String[] domains) {
		this.domains = domains;
	}
	/**
	 * @param numberOfCrawlers the numberOfCrawlers to set
	 */
	public void setNumberOfCrawlers(int numberOfCrawlers) {
		this.numberOfCrawlers = numberOfCrawlers;
	}
	/**
	 * @param maxPages the maxPages to set
	 */
	public void setMaxPages(int maxPages) {
		this.maxPages = maxPages;
	}
	/**
	 * @param maxDepth the maxDepth to set
	 */
	public void setMaxDepth(int maxDepth) {
		this.maxDepth = maxDepth;
	}
	/**
	 * @param rootFolder the rootFolder to set
	 */
	public void setRootFolder(String rootFolder) {
		this.rootFolder = rootFolder;
	}
	/**
	 * @param storageFolder the storageFolder to set
	 */
	public void setStorageFolder(String storageFolder) {
		this.storageFolder = storageFolder;
	}
	/**
	 * @param delay the delay to set
	 */
	public void setDelay(long delay) {
		this.delay = delay;
	}

	/**
	 * @return the crawlerClass
	 */
	public Class<? extends WebCrawler> getCrawlerClass() {
		return crawlerClass;
	}

	/**
	 * @param crawlerClass the crawlerClass to set
	 */
	public void setCrawlerClass(Class<? extends WebCrawler> crawlerClass) {
		this.crawlerClass = crawlerClass;
	}

	/**
	 * @return the filters
	 */
	public Pattern[] getFilters() {
		return filters;
	}

	/**
	 * @param filters the filters to set
	 */
	public void setFilters(Pattern[] filters) {
		this.filters = filters;
	}

	/**
	 * @return the trigger
	 */
	public Pattern getTrigger() {
		return trigger;
	}

	/**
	 * @param trigger the trigger to set
	 */
	public void setTrigger(Pattern trigger) {
		this.trigger = trigger;
	}
	
	
	
}
