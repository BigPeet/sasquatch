package retrieval;

import retrieval.interfaces.ICrawlController;
import retrieval.mailinglist.pipermail.PiperMailCrawlController;
import manager.parser.mail.MailParser;
import manager.parser.mail.pipermail.PiperMailParser;

public class CrawlTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String hibernatePath = "http://lists.jboss.org/pipermail/jboss-user/";
		String[] domains = {hibernatePath};
		
		MailParser parser = new PiperMailParser("res/mails/jboss.xml");
		
		ICrawlController controller = new PiperMailCrawlController(domains, parser);
		
		controller.run();
		controller.saveData();
	}

}
