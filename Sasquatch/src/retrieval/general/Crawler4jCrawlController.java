package retrieval.general;

import retrieval.interfaces.ICrawlController;

public abstract class Crawler4jCrawlController implements ICrawlController {

	private Crawler4jControllerConfiguration config;
	
	public Crawler4jCrawlController() {
		
	}
	
	public Crawler4jCrawlController(Crawler4jControllerConfiguration config) {
		this.setConfig(config);
	}
	
	@Override
	public abstract void run();

	public Crawler4jControllerConfiguration getConfig() {
		return config;
	}

	public void setConfig(Crawler4jControllerConfiguration config) {
		this.config = config;
	}
	
	
	
	
}
