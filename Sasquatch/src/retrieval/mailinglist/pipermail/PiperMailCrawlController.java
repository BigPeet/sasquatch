package retrieval.mailinglist.pipermail;

import java.util.regex.Pattern;

import edu.uci.ics.crawler4j.crawler.WebCrawler;
import manager.parser.mail.MailParser;
import retrieval.general.Crawler4jCrawlController;
import retrieval.general.Crawler4jControllerConfiguration;

public class PiperMailCrawlController extends Crawler4jCrawlController {

	private static final Pattern gzPattern = Pattern.compile(".*(\\.(gz|txt))$");
	private static final Pattern threadPattern = Pattern.compile(".*(/(subject|author|date)\\.html)$");
	private static final Pattern startPattern = Pattern.compile(".*(\\#start)$");
	private static final Pattern TRIGGER = Pattern.compile(".*(/(\\d+)\\.html)$");
	private static String ROOT = "res\\crawler\\root\\jboss";
	private static String STORAGE = "res\\crawler\\storage\\jboss";
	private static Pattern[] FILTERS = {gzPattern, threadPattern, startPattern};
	private static int MAX_PAGES = 8000;
	private static int NUM_OF_CRAWLERS = 4;
	private static int MAX_DEPTH = 2;
	private static long DELAY = 200;
	private static Class<? extends WebCrawler> CRAWLER = PiperMailCrawler.class;
	
	//e.g. http://lists.jboss.org/pipermail/hibernate-dev/
	private String[] domains;

	public PiperMailCrawlController(String[] domains, MailParser parser) {
		this.domains = domains;
		setConfig(createStandardConfig());
		setParser(parser);
	}

	public PiperMailCrawlController(Crawler4jControllerConfiguration config, MailParser parser) {
		super(config, parser);
	}

	public PiperMailCrawlController(Crawler4jControllerConfiguration config) {
		super(config);
	}

	public Crawler4jControllerConfiguration createConfig(String[] domains,
			int numberOfCrawlers, int maxPages, int maxDepth,
			String rootFolder, String storageFolder, long delay,
			Class<? extends WebCrawler> crawler, Pattern[] filters, Pattern trigger) {

		return new Crawler4jControllerConfiguration(domains, numberOfCrawlers, maxPages, 
				maxDepth, rootFolder, storageFolder, delay, crawler, filters, trigger);	
	}
	
	protected Crawler4jControllerConfiguration createStandardConfig() {
		return createConfig(domains, NUM_OF_CRAWLERS, MAX_PAGES, MAX_DEPTH, ROOT, STORAGE, DELAY, CRAWLER, FILTERS, TRIGGER);
	}


}
