package manager.parser.html;

import retrieval.interfaces.ICrawlController;
import retrieval.selenium.MMCrawlController;
import retrieval.selenium.SFCrawlController;
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
