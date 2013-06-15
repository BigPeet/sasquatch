package retrieval;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import retrieval.interfaces.ICrawlController;
import retrieval.mailinglist.pipermail.PiperMailCrawlController;
import retrieval.mailinglist.yahoo.YahooCrawlController;
import manager.parser.mail.MailParser;
import manager.parser.mail.pipermail.PiperMailParser;
import manager.parser.mail.yahoo.YahooMailParser;

public class CrawlTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String listName = "easymock";
		String path = "res/mails/easymock.xml";
		ICrawlController controller = new YahooCrawlController(path, listName, 0, 0, 100);
		
		controller.run();
		controller.saveData();
		
//		String hibernatePath = "http://lists.jboss.org/pipermail/jboss-user/";
//		String[] domains = {hibernatePath};
//		
//		MailParser parser = new PiperMailParser("res/mails/jboss.xml");
//		
//		ICrawlController controller = new PiperMailCrawlController(domains, parser);
//		
//		Date start = new Date();
//		controller.run();
//		int size = controller.getData().length;
//		Date end = new Date();
//		controller.saveData();
//		
//		long mills = end.getTime() - start.getTime();
//		System.out.println("Time: " + TimeUnit.SECONDS.convert(mills, TimeUnit.MILLISECONDS));
//		System.out.println("Size: " + size);
	}

	private static String read(String string) {
		String content = "";
		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File(string)));
			String line = "";
			while ((line = reader.readLine()) != null) {
				content += line;
				content += "\n";
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return content;
	}

}
