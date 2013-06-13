package retrieval.mailinglist.pipermail;

import manager.parser.mail.MailParser;
import retrieval.general.Crawler4jCrawlController;
import retrieval.general.Crawler4jControllerConfiguration;

public class JBossCrawlController extends Crawler4jCrawlController {
	
	public JBossCrawlController() {

	}
	
	public JBossCrawlController(Crawler4jControllerConfiguration config, MailParser parser) {
		super(config, parser);
	}

	public JBossCrawlController(Crawler4jControllerConfiguration config) {
		super(config);
	}

	
}
