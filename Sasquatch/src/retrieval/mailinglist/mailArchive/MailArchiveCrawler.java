package retrieval.mailinglist.mailArchive;

import java.util.ArrayList;

import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import retrieval.general.SeleniumCrawler;
import retrieval.mailinglist.TextCollector;

public class MailArchiveCrawler extends SeleniumCrawler {

	private static final String baseURL = "http://www.mail-archive.com/";
	private static final String indexURL = "/index.html";

	private int pages;

	public MailArchiveCrawler(String listName, int pages) {
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
			String[] mailForms = getMailLinks(content);
			for (String link : mailForms) {
				try {
					getDriver().get(link);
					String mailHtml = getDriver().getPageSource();
					getStat().addData(mailHtml);
				} catch (org.openqa.selenium.WebDriverException e) {
					//What to do?
					System.out.println(link);
				}
			}
			if (hasNextPage(content)) {
				i++;
			} else {
				done = true;
			}
		}
	}

	private boolean hasNextPage(String content) {
		boolean hasNextPage = false;
		try {
			Parser parser = new Parser(content);
			TagNameFilter tagFilter = new TagNameFilter("span");
			HasAttributeFilter hrefFilter = new HasAttributeFilter("class", "tnextpglink");
			AndFilter filter = new AndFilter(tagFilter, hrefFilter);
			NodeList list = parser.parse(filter);
			hasNextPage = list.size() > 0;
		} catch (ParserException e) {
			e.printStackTrace();
		}

		return hasNextPage;
	}

	private String[] getMailLinks(String content) {
		ArrayList<String> mailLinks = new ArrayList<String>();
		try {
			Parser parser = new Parser(content);
			TagNameFilter linkFilter = new TagNameFilter("a");
			HasAttributeFilter hrefFilter = new HasAttributeFilter("href");
			HasAttributeFilter nameFilter = new HasAttributeFilter("name");
			NodeFilter[] predicates = {linkFilter, hrefFilter, nameFilter};
			AndFilter filter = new AndFilter(predicates);
			NodeList links = parser.parse(filter);
			for (int i = 0; i < links.size(); i++) {
				LinkTag link = (LinkTag) links.elementAt(i);
				String name = link.getAttribute("name");
				String href = link.getAttribute("href");
				if (href.equals("msg" + name + ".html")) {
					String mailLink = baseURL + getListName() + "/" +  href;
					mailLinks.add(mailLink);
				}
			}
		} catch (ParserException e) {
			e.printStackTrace();
		}
		return mailLinks.toArray(new String[mailLinks.size()]);
	}

	private String getPageLink(String listName, int i) {
		String pageLink = "";
		if (i == 1) {
			pageLink = buildInitialURL(listName);
		} else {
			pageLink = baseURL + listName + "/thrd" + i + ".html";
		}
		return pageLink;
	}

	private String buildInitialURL(String listName) {
		return baseURL + listName + indexURL;
	}

}
