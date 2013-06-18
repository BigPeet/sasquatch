package retrieval.mailinglist.appleList;

import java.util.regex.Pattern;

import manager.parser.mail.MailParser;
import manager.parser.mail.appleList.AppleListMailParser;

import edu.uci.ics.crawler4j.crawler.WebCrawler;
import retrieval.general.Crawler4jControllerConfiguration;
import retrieval.general.Crawler4jCrawlController;

public class AppleListCrawlController extends Crawler4jCrawlController {
	
	private static final String baseURL = "http://lists.apple.com/archives/";
	private static final Pattern threadPattern = Pattern.compile(".*(/(subject|author|date|threads)\\.html)$");
	//private static final Pattern nextPattern = Pattern.compile(".*(/mail(\\d+)\\.html)$");
	private static final Pattern TRIGGER = Pattern.compile(".*(/msg(\\d+)\\.html)$");
	private static String ROOT = "res\\crawler\\root\\apple";
	private static String STORAGE = "res\\crawler\\storage\\apple";
	private static Pattern[] FILTERS = {threadPattern};
	private static int MAX_PAGES = 1000;
	private static int NUM_OF_CRAWLERS = 4;
	private static int MAX_DEPTH = 3;
	private static long DELAY = 200;
	private static Class<? extends WebCrawler> CRAWLER = AppleListCrawler.class;
	
	private String[] domains;

	public AppleListCrawlController(String[] listNames) {
		super(MAX_PAGES);
		this.domains = getAppleListDomains(listNames);
		setConfig(createStandardConfig());
		setParser(new AppleListMailParser());
	}
	
	public AppleListCrawlController(String[] listNames, int maxPages) {
		super(maxPages);
		this.domains = getAppleListDomains(listNames);
		setConfig(createStandardConfig());
		setParser(new AppleListMailParser());
	}
	
	public AppleListCrawlController(String[] listNames, MailParser parser) {
		super(MAX_PAGES);
		this.domains = getAppleListDomains(listNames);
		setConfig(createStandardConfig());
		setParser(parser);
	}

	public AppleListCrawlController(String[] listNames, MailParser parser, int maxPages) {
		super(maxPages);
		this.domains = getAppleListDomains(listNames);
		setConfig(createStandardConfig());
		setParser(parser);
	}

	public AppleListCrawlController(Crawler4jControllerConfiguration config, MailParser parser) {
		super(config, parser);
	}

	public AppleListCrawlController(Crawler4jControllerConfiguration config) {
		super(config);
	}
	
	private String[] getAppleListDomains(String[] listNames) {
		String[] links = new String[listNames.length];
		for (int i = 0; i < listNames.length; i++) {
			links[i] = buildLink(listNames[i]);
		}
		
		return links;
	}
	
	private String buildLink(String listName) {
		return baseURL + listName + "/";
	}



	protected Crawler4jControllerConfiguration createStandardConfig() {
		return createConfig(domains, NUM_OF_CRAWLERS, getMaxPages(), MAX_DEPTH, ROOT, STORAGE, DELAY, CRAWLER, FILTERS, TRIGGER);
	}
	
	public Crawler4jControllerConfiguration createConfig(String[] domains,
			int numberOfCrawlers, int maxPages, int maxDepth,
			String rootFolder, String storageFolder, long delay,
			Class<? extends WebCrawler> crawler, Pattern[] filters, Pattern trigger) {

		return new Crawler4jControllerConfiguration(domains, numberOfCrawlers, maxPages, 
				maxDepth, rootFolder, storageFolder, delay, crawler, filters, trigger);	
	}


}
