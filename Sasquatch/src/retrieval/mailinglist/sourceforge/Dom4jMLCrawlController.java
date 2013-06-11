package retrieval.mailinglist.sourceforge;

import java.util.regex.Pattern;

import manager.parser.mail.MailParser;
import retrieval.general.GeneralControllerConfiguration;
import retrieval.mailinglist.MailingListCrawlController;
import retrieval.mailinglist.MailingListCrawler;
import edu.uci.ics.crawler4j.crawler.WebCrawler;

public class Dom4jMLCrawlController extends MailingListCrawlController {
	
	private static String[] DOMAINS = {"http://sourceforge.net/mailarchive/forum.php?forum_name=dom4j-user/"};
	private static final Pattern gzPattern = Pattern.compile(".*(\\.(gz|txt))$");
	private static final Pattern threadPattern = Pattern.compile(".*(/(subject|author|date)\\.html)$");
	private static final Pattern startPattern = Pattern.compile(".*(\\#start)$");
	private static final Pattern TRIGGER = Pattern.compile(".*(/(\\d+)\\.html)$");
	private static String ROOT = "res\\crawler\\root\\jboss";
	private static String STORAGE = "res\\crawler\\storage\\jboss";
	private static Pattern[] FILTERS = {gzPattern, threadPattern, startPattern};
	private static int MAX_PAGES = 500;
	private static int NUM_OF_CRAWLERS = 5;
	private static int MAX_DEPTH = 2;
	private static long DELAY = 200;
	private static Class<? extends WebCrawler> CRAWLER = MailingListCrawler.class;
	//private static String PATH = "res/mails/jboss.xml";


	public Dom4jMLCrawlController(String[] domains,
			int numberOfCrawlers, int maxPages, int maxDepth,
			String rootFolder, String storageFolder, long delay,
			Class<? extends WebCrawler> crawler, Pattern[] filters, Pattern trigger) {
		initialize(domains, numberOfCrawlers, maxPages, maxDepth, rootFolder, storageFolder, delay, crawler, filters, trigger);
	}

	public Dom4jMLCrawlController() {
		initialize(DOMAINS, NUM_OF_CRAWLERS, MAX_PAGES, MAX_DEPTH, ROOT, STORAGE, DELAY, CRAWLER, FILTERS, TRIGGER);
	}

	public Dom4jMLCrawlController(MailParser parser) {
		super(parser);
		initialize(DOMAINS, NUM_OF_CRAWLERS, MAX_PAGES, MAX_DEPTH, ROOT, STORAGE, DELAY, CRAWLER, FILTERS, TRIGGER);
	}

	private void initialize(String[] domains,
			int numberOfCrawlers, int maxPages, int maxDepth,
			String rootFolder, String storageFolder, long delay,
			Class<? extends WebCrawler> crawler, Pattern[] filters, Pattern trigger) {
		GeneralControllerConfiguration config = new GeneralControllerConfiguration(domains, numberOfCrawlers, maxPages, 
				maxDepth, rootFolder, storageFolder, delay, crawler, filters, trigger);
		this.setConfig(config);
	}
}
