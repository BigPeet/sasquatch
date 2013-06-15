package retrieval.mailinglist.markmail;


import java.util.ArrayList;
import org.htmlparser.Parser;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.tags.Div;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import retrieval.general.SeleniumCrawler;
import retrieval.mailinglist.TextCollector;

public class MMCrawler extends SeleniumCrawler {

	private static final String startURL = "http://";
	private static final String baseURL = ".markmail.org";
	private static final String searchURL = "/search/?page=";
	private static final String messageURL = "/message/";
	
	private int pages;
	
	
	public MMCrawler(String listName, int pages) {
		super(listName);
		this.pages = pages;
		setStat(new TextCollector());
	}

	@Override
	public void run() {
		int i = 1;
		boolean done = false;
		while (!done && (i <= pages || pages < 0)) {
			getDriver().get(getPageLink(getListName(), i));
			String content = getDriver().getPageSource();
			String[] links = getMailLinks(getListName(), content);
			for (String link : links) {
				getDriver().get(link);
				String mailPage = getDriver().getPageSource();
				getStat().addData(mailPage);
			}
			if (!hasNextPage(content)) {
				done = true;
			} else {
				i++;
			}
		}
		//Not sure this is a good idea.
		//driver.close();
	}
	
	private String[] getMailLinks(String listName, String content) {
		Parser htmlParser = null;
		ArrayList<String> links = new ArrayList<String>();
		try {
			htmlParser = new Parser(content);
			HasAttributeFilter filter = new HasAttributeFilter("class", "result");
			NodeList nodes = htmlParser.parse(filter);
			for (int i = 0; i < nodes.size(); i++) {
				Div div = (Div) nodes.elementAt(i);
				String id = div.getAttribute("id");
				String link = getMailLink(listName, id);
				links.add(link);
			}
			
		} catch (ParserException e) {
			e.printStackTrace();
		}
		return links.toArray(new String[links.size()]);
	}


	private String getPageLink(String listName, int page) {
		return startURL + listName + baseURL + searchURL + page;
	}
	
	private String getMailLink(String listName, String id) {
		return startURL + listName + baseURL + messageURL + id;
	}

	private boolean hasNextPage(String content) {
		boolean hasNext = false;
		try {
			Parser htmlParser = new Parser(content);
			HasAttributeFilter filter = new HasAttributeFilter("class", "nextpage");
			NodeList list = htmlParser.parse(filter);
			hasNext = list.size() != 0;
		} catch (ParserException e) {
			e.printStackTrace();
		}
		return hasNext;
	}


}
