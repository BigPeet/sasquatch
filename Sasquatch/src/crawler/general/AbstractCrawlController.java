package crawler.general;

import crawler.interfaces.ICrawlController;

public abstract class AbstractCrawlController implements ICrawlController {

	private String[] domains;
	private int numberOfCrawlers;
	private int maxPages;
	private String rootFolder;
	private long delay;
	
	public abstract void run();
	
	public void setSeedDomains(String[] seeds) {
		this.domains = seeds;
	}
	
	public void setRootFolder(String root) {
		this.rootFolder = root;
	}
	
	public void setMaxPagesToFetch(int max) {
		maxPages = max;
	}
	
	public void setDelay(long delay) {
		this.delay = delay;
	}
	public void setNumberOfCrawlers(int no) {
		this.numberOfCrawlers = no;
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
	 * @return the rootFolder
	 */
	public String getRootFolder() {
		return rootFolder;
	}

	/**
	 * @return the delay
	 */
	public long getDelay() {
		return delay;
	}
	
	
}
