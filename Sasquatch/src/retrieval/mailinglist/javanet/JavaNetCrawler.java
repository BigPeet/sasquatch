package retrieval.mailinglist.javanet;

import java.util.ArrayList;

import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.Div;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.tags.TableRow;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import retrieval.general.SeleniumCrawler;
import retrieval.mailinglist.TextCollector;

public class JavaNetCrawler extends SeleniumCrawler {

	private static final String baseURL = "https://java.net/projects/";
	private static final String archiveURL = "/archive/";
	private static final String mailURL = "/message/";
	private static final String NOT_FOUND = "The mailing list users@jpa-spec.java.net does not have any messages in its archive.";

	private int start;
	private int end;

	public JavaNetCrawler(String listName, int start, int end) {
		super(listName);
		this.start = start;
		this.end = end;
		setStat(new TextCollector());
	}


	@Override
	public void run() {
		for (String link : getPageLinks()) {
			getDriver().get(link);
			String page = getDriver().getPageSource();
			for (String mailLink : getMailLinks(link, page)) {
				getDriver().get(mailLink);
				String mailPage = getDriver().getPageSource();
				if (!pageNotFound(mailPage)) {
					getStat().addData(mailPage);
				}
			}
		}
	}

	private boolean pageNotFound(String mailPage) {
		boolean wasNotFound = false;
		try {
			Parser parser = new Parser(mailPage);
			TagNameFilter tagFilter = new TagNameFilter("div");
			HasAttributeFilter classFilter = new HasAttributeFilter("class", "flash notice");
			AndFilter filter = new AndFilter(tagFilter, classFilter);
			NodeList list = parser.parse(filter);
			if (list.size() > 0) {
				Div divTag = (Div) list.elementAt(0);
				String title = divTag.getStringText().trim();
				wasNotFound = title.equals(NOT_FOUND);
			}
		} catch (ParserException e) {
			e.printStackTrace();
		}
		return wasNotFound;
	}


	private String[] getMailLinks(String link, String pageSource) {
		ArrayList<String> mailLinks = new ArrayList<String>();
		int mails = getNumberOfMails(pageSource);
		for (int i = 0; i < mails; i++) {
			String mailLink = buildMailLink(link, i);
			mailLinks.add(mailLink);
		}
		return mailLinks.toArray(new String[mailLinks.size()]);
	}


	private String buildMailLink(String link, int mailNumber) {
		return link + mailURL + mailNumber;
	}


	private int getNumberOfMails(String pageSource) {
		int mails = 0;
		String monthRow = getMonthRow(pageSource);
		if (!monthRow.isEmpty()) {
			mails = extractNumberFromCurrentMonth(monthRow);
		}
		return mails;
	}

	private String getMonthRow(String pageSource) {
		String monthRow = "";
		try {
			Parser parser = new Parser(pageSource);
			TagNameFilter tagFilter = new TagNameFilter("tr");
			HasAttributeFilter classFilter = new HasAttributeFilter("id", "mlmMonthList");
			AndFilter filter = new AndFilter(tagFilter, classFilter);
			NodeList rows = parser.parse(filter);
			if (rows.size() == 1) {
				TableRow row = (TableRow) rows.elementAt(0);
				monthRow = row.getStringText();
			}
		} catch (ParserException e) {
			e.printStackTrace();
		}
		return monthRow;
	}


	private int extractNumberFromCurrentMonth(String monthRow) {
		int num = 0;
		try {
			Parser parser = new Parser(monthRow);
			TagNameFilter tagFilter = new TagNameFilter("a");
			HasAttributeFilter classFilter = new HasAttributeFilter("class", "current");
			AndFilter filter = new AndFilter(tagFilter, classFilter);
			NodeList links = parser.parse(filter);
			if (links.size() == 1) {
				LinkTag link = (LinkTag) links.elementAt(0);
				String number = link.getStringText().trim();
				num = Integer.parseInt(number);
			}
		} catch (ParserException e) {
			e.printStackTrace();
		}
		return num;
	}


	private String[] getPageLinks() {
		ArrayList<String> pageLinks = new ArrayList<String>();
		for (int year = start; year <= end; year++) {
			for (int month = 1; month < 13; month++) {
				pageLinks.add(buildMonthLink(year, month));
			}
		}
		return pageLinks.toArray(new String[pageLinks.size()]);
	}


	private String buildMonthLink(int year, int month) {
		String dMonth = String.format("%02d", month);
		return baseURL + getListName() + archiveURL + year + "-" + dMonth;
	}

}
