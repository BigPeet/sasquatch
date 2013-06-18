package retrieval;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import retrieval.interfaces.ICrawlController;
import retrieval.mailinglist.apache.ApacheCrawlController;
import retrieval.mailinglist.appleList.AppleListCrawlController;
import retrieval.mailinglist.eclipseList.EclipseListCrawlController;
import retrieval.mailinglist.javanet.JavaNetCrawlController;
import retrieval.mailinglist.mailArchive.MailArchiveCrawlController;
import retrieval.mailinglist.markmail.MMCrawlController;
import retrieval.mailinglist.pipermail.PiperMailCrawlController;
import retrieval.mailinglist.sourceforge.SFCrawlController;
import retrieval.mailinglist.yahoo.YahooCrawlController;
import manager.parser.mail.MailParser;
import manager.parser.mail.apache.ApacheMailParser;
import manager.parser.mail.appleList.AppleListMailParser;
import manager.parser.mail.eclipseList.EclipseListMailParser;
import manager.parser.mail.javanet.JavaNetMailParser;
import manager.parser.mail.mailArchive.MailArchiveParser;
import manager.parser.mail.markmail.MMMailParser;
import manager.parser.mail.pipermail.PiperMailParser;
import manager.parser.mail.sourceforge.SFMailParser;

public class CrawlTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
//		MailParser parser = new MailArchiveParser("res/mails/resin.xml");
//		parser.parseMail(read("res/mails/tests/mailarchive.html"));
		
//		ICrawlController httpClientControler = new ApacheCrawlController(parser, "hc-httpclient-users", 2013, 2013);
//		
//		httpClientControler.run();
//		httpClientControler.saveData();
//		
//		ICrawlController easyMockController = new YahooCrawlController("easymock", 2013, 2013, 10);
//		
//		easyMockController.run();
//		
//		ICrawlController htmlUnitController = new SFCrawlController(parser, "htmlunit-user", 2012, 2013);
//		
//		htmlUnitController.run();
//		htmlUnitController.saveData();
//		
//		String[] domains = {"http://lists.apple.com/archives/quartz-dev/"};
//		ICrawlController quartzController = new AppleListCrawlController(domains, parser);
//		
//		quartzController.run();
//		quartzController.saveData();
//		
//		
//		ICrawlController jettyController = new EclipseListCrawlController(parser, "jetty-users", 2013, 2013, 1);
//		
//		jettyController.run();
//		jettyController.saveData();
//		
//		ICrawlController jpaController = new JavaNetCrawlController(parser, "jpa-spec/lists/users", 2013, 2013);
//		
//		jpaController.run();
//		jpaController.saveData();
//		
//		String[] piperdomains = {"http://lists.jboss.org/pipermail/hibernate-dev/"};
//		ICrawlController hibernateController = new PiperMailCrawlController(piperdomains, parser);
//		
//		hibernateController.run();
//		hibernateController.saveData();
//		
//		ICrawlController jdomController = new MMCrawlController(parser, "jdom", 2013, 2013, 1);
//		
//		jdomController.run();
//		jdomController.saveData();
//		
//		ICrawlController resinController = new MailArchiveCrawlController(parser, "resin-interest@caucho.com", 2008, 2013, 1);
//		resinController.run();
//		resinController.saveData();
		
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
