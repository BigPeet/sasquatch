package systems.source.mail;

import crawler.mailinglist.MailingListCrawlController;
import systems.source.Source;
import systems.source.WebSourceHandler;

public class WebMailHandler extends WebSourceHandler {
	
	private Mail[] mails;
	
	public WebMailHandler(MailingListCrawlController controller) {
		controller.run();
		mails = controller.getMails();
	}

	@Override
	public Source[] getSources() {
		return mails;
	}

}
