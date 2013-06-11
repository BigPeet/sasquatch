package retrieval.general;

import retrieval.interfaces.ICrawlController;

public abstract class AbstractCrawlController implements ICrawlController {

	private GeneralControllerConfiguration config;
	
	public AbstractCrawlController() {
		
	}
	
	public AbstractCrawlController(GeneralControllerConfiguration config) {
		this.setConfig(config);
	}
	
	public abstract void run();

	public GeneralControllerConfiguration getConfig() {
		return config;
	}

	public void setConfig(GeneralControllerConfiguration config) {
		this.config = config;
	}
	
	
	
	
}
