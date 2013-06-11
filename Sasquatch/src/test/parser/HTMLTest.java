package test.parser;

import retrieval.interfaces.ICrawlController;
import retrieval.mailinglist.markmail.MMCrawlController;
import retrieval.mailinglist.sourceforge.SFCrawlController;
import manager.systems.source.mail.Mail;

public class HTMLTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String path = "res/mails/jdom.xml";
		String listName = "jdom";
		int pages = 1;
		int startYear = 2001;
		int endYear = 2013;
		
		ICrawlController controller = new MMCrawlController(path, listName, pages);
		controller.run();
	}
}
