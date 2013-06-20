package manager.systems.source.mail;

import retrieval.interfaces.ICrawlController;
import manager.systems.source.Source;
import manager.systems.source.WebSourceHandler;

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
