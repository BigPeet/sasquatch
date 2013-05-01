package crawler.general;

import crawler.interfaces.ICrawlController;

public abstract class AbstractCrawlController implements ICrawlController {

	private GeneralControllerConfiguration config;
	
	public AbstractCrawlController() {
		
	}
	
	public AbstractCrawlController(GeneralControllerConfiguration config) {
		this.setConfig(config);
	}
	
	@Override
	public abstract void run();

	public GeneralControllerConfiguration getConfig() {
		return config;
	}

	public void setConfig(GeneralControllerConfiguration config) {
		this.config = config;
	}
	
	
	
	
}
