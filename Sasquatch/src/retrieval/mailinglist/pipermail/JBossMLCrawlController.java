package retrieval.mailinglist.pipermail;

import java.util.regex.Pattern;

import manager.parser.mail.MailParser;

import retrieval.general.Crawler4jControllerConfiguration;
import edu.uci.ics.crawler4j.crawler.WebCrawler;

public class JBossMLCrawlController extends JBossCrawlController {
	
	private static String[] DOMAINS = {"http://lists.jboss.org/pipermail/jboss-dev-forums/"};
	private static final Pattern gzPattern = Pattern.compile(".*(\\.(gz|txt))$");
	private static final Pattern threadPattern = Pattern.compile(".*(/(subject|author|date)\\.html)$");
	private static final Pattern startPattern = Pattern.compile(".*(\\#start)$");
	private static final Pattern TRIGGER = Pattern.compile(".*(/(\\d+)\\.html)$");
	private static String ROOT = "res\\crawler\\root\\jboss";
	private static String STORAGE = "res\\crawler\\storage\\jboss";
	private static Pattern[] FILTERS = {gzPattern, threadPattern, startPattern};
	private static int MAX_PAGES = 8000;
	private static int NUM_OF_CRAWLERS = 6;
	private static int MAX_DEPTH = 2;
	private static long DELAY = 200;
	private static Class<? extends WebCrawler> CRAWLER = JBossCrawler.class;
	//private static String PATH = "res/mails/jboss.xml";
	
	
	public JBossMLCrawlController(String[] domains,
			int numberOfCrawlers, int maxPages, int maxDepth,
			String rootFolder, String storageFolder, long delay,
			Class<? extends WebCrawler> crawler, Pattern[] filters, Pattern trigger) {
		initialize(domains, numberOfCrawlers, maxPages, maxDepth, rootFolder, storageFolder, delay, crawler, filters, trigger);
	}
	
	public JBossMLCrawlController() {
		initialize(DOMAINS, NUM_OF_CRAWLERS, MAX_PAGES, MAX_DEPTH, ROOT, STORAGE, DELAY, CRAWLER, FILTERS, TRIGGER);
	}
	
	public JBossMLCrawlController(MailParser parser) {
		initialize(DOMAINS, NUM_OF_CRAWLERS, MAX_PAGES, MAX_DEPTH, ROOT, STORAGE, DELAY, CRAWLER, FILTERS, TRIGGER);
		setParser(parser);
	}
	
	private void initialize(String[] domains,
			int numberOfCrawlers, int maxPages, int maxDepth,
			String rootFolder, String storageFolder, long delay,
			Class<? extends WebCrawler> crawler, Pattern[] filters, Pattern trigger) {
		Crawler4jControllerConfiguration config = new Crawler4jControllerConfiguration(domains, numberOfCrawlers, maxPages, 
				maxDepth, rootFolder, storageFolder, delay, crawler, filters, trigger);
		this.setConfig(config);
	}
	
}
