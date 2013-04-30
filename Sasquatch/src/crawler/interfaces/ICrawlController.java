package crawler.interfaces;

public interface ICrawlController {

	public void run();
	public void setSeedDomains(String[] seeds);
	public void setRootFolder(String root);
	public void setMaxPagesToFetch(int max);
	public void setDelay(long delay);
	public void setNumberOfCrawlers(int no);
	//public List<Object> getData();
}
