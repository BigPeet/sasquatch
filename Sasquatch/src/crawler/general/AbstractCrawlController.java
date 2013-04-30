package crawler.general;

import crawler.interfaces.ICrawlController;

public abstract class AbstractCrawlController implements ICrawlController {

	private String[] domains;
	private int numberOfCrawlers;
	private int maxPages;
	private String rootFolder;
	private long delay;
	
	@Override
	public abstract void run();
	
	@Override
	public void setSeedDomains(String[] seeds) {
		this.domains = seeds;
	}
	
	@Override
	public void setRootFolder(String root) {
		this.rootFolder = root;
	}
	
	@Override
	public void setMaxPagesToFetch(int max) {
		maxPages = max;
	}
	
	@Override
	public void setDelay(long delay) {
		this.delay = delay;
	}
	
	@Override
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
