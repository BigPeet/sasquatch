package retrieval.mailinglist.pipermail;

import java.util.regex.Pattern;

import edu.uci.ics.crawler4j.crawler.WebCrawler;
import manager.parser.mail.MailParser;
import manager.parser.mail.pipermail.PiperMailParser;
import retrieval.general.Crawler4jCrawlController;
import retrieval.general.Crawler4jControllerConfiguration;

public class PiperMailCrawlController extends Crawler4jCrawlController {

	private static final String baseURL = "http://lists.jboss.org/pipermail/";
	private static final Pattern gzPattern = Pattern.compile(".*(\\.(gz|txt))$");
	private static final Pattern threadPattern = Pattern.compile(".*(/(subject|author|date)\\.html)$");
	private static final Pattern startPattern = Pattern.compile(".*(\\#start)$");
	private static final Pattern TRIGGER = Pattern.compile(".*(/(\\d+)\\.html)$");
	private static String ROOT = "res\\crawler\\root\\jboss";
	private static String STORAGE = "res\\crawler\\storage\\jboss";
	private static Pattern[] FILTERS = {gzPattern, threadPattern, startPattern};
	private static int MAX_PAGES = 500;//8000;
	private static int NUM_OF_CRAWLERS = 4;
	private static int MAX_DEPTH = 2;
	private static long DELAY = 200;
	private static Class<? extends WebCrawler> CRAWLER = PiperMailCrawler.class;
	
	//e.g. http://lists.jboss.org/pipermail/hibernate-dev/
	private String[] domains;

	
	public PiperMailCrawlController(String[] listNames) {
		super(MAX_PAGES);
		this.domains = getPiperMailDomains(listNames);
		setConfig(createStandardConfig());
		setParser(new PiperMailParser());
	}
	
	public PiperMailCrawlController(String[] listNames, int maxPages) {
		super(maxPages);
		this.domains = getPiperMailDomains(listNames);
		setConfig(createStandardConfig());
		setParser(new PiperMailParser());
	}
	
	public PiperMailCrawlController(String[] listNames, MailParser parser) {
		super(MAX_PAGES);
		this.domains = getPiperMailDomains(listNames);
		setConfig(createStandardConfig());
		setParser(parser);
	}
	


	public PiperMailCrawlController(String[] listNames, MailParser parser, int maxPages) {
		super(maxPages);
		this.domains = getPiperMailDomains(listNames);
		setConfig(createStandardConfig());
		setParser(parser);
	}

	public PiperMailCrawlController(Crawler4jControllerConfiguration config, MailParser parser) {
		super(config, parser);
	}

	public PiperMailCrawlController(Crawler4jControllerConfiguration config) {
		super(config);
	}
	
	private String[] getPiperMailDomains(String[] listNames) {
		String[] links = new String[listNames.length];
		for (int i = 0; i < listNames.length; i++) {
			links[i] = buildLink(listNames[i]);
		}
		
		return links;
	}

	private String buildLink(String listName) {
		return baseURL + listName + "/";
	}



	public Crawler4jControllerConfiguration createConfig(String[] domains,
			int numberOfCrawlers, int maxPages, int maxDepth,
			String rootFolder, String storageFolder, long delay,
			Class<? extends WebCrawler> crawler, Pattern[] filters, Pattern trigger) {

		return new Crawler4jControllerConfiguration(domains, numberOfCrawlers, maxPages, 
				maxDepth, rootFolder, storageFolder, delay, crawler, filters, trigger);	
	}
	
	protected Crawler4jControllerConfiguration createStandardConfig() {
		return createConfig(domains, NUM_OF_CRAWLERS, getMaxPages(), MAX_DEPTH, ROOT, STORAGE, DELAY, CRAWLER, FILTERS, TRIGGER);
	}


}
