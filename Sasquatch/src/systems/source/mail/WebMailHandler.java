package systems.source.mail;

import crawler.interfaces.ICrawlController;
import systems.source.Source;
import systems.source.WebSourceHandler;

public class WebMailHandler extends WebSourceHandler {
	
	private ICrawlController controller;
	private Mail[] mails;
	
	public WebMailHandler(ICrawlController controller) {
		this.controller = controller;
	}

	@Override
	public Source[] getSources() {
		if (mails == null && controller != null) {
			crawl();
		}
		return mails;
	}
	
	private void crawl() {
		controller.run();
		Object[] data = controller.getData();
		if (data != null && data instanceof Mail[]) {
			mails = (Mail[]) data;
		}
	}

}
