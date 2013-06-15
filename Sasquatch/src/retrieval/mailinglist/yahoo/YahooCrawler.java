package retrieval.mailinglist.yahoo;

import java.io.BufferedWriter;
import java.io.FileWriter;

import retrieval.general.SeleniumCrawler;
import retrieval.mailinglist.TextCollector;

public class YahooCrawler extends SeleniumCrawler {


	private static final String baseURL = "http://tech.groups.yahoo.com/group/";
	private static final String mailURL = "/messages/";
	private static final String endURL = "?xm=1&m=e&l=1";
	private static final int mailsPerPage = 30;
	private static final String MAIL_FORM_END = "<span class=\"corner-bottom\">";

	private int pages;

	public YahooCrawler(String listName, int pages) {
		super(listName);
		this.pages = pages;
		setStat(new TextCollector());
	}

	@Override
	public void run() {

		getDriver().get(buildInitialURL(getListName()));
		int mailNo = getNumberOfLastMail(getDriver().getTitle()) - mailsPerPage + 1;
		int i = 1;
		boolean done = false;
		while (!done && (i <= pages || pages < 0) && mailNo > 0) {
			getDriver().get(getPageLink(getListName(), mailNo));
			String content = getDriver().getPageSource();
			String[] mailForms = getMailForms(mailNo, content);
			
			write(mailForms[0]);
			
			for (String form : mailForms) {
				getStat().addData(form);
			}
			if (hasNextPage(mailNo)) {
				i++;
				mailNo = getNextMailNumber(mailNo);; 
			} else {
				done = true;
			}
		}
	}

	private String[] getMailForms(int mailNo, String pageSource) {
		String[] mailForms = new String[mailsPerPage];
		for (int i = 0; i < mailsPerPage; i++) {
			int start = -1;
			int end = -1;
			if (i != 0) {
				start = pageSource.indexOf(getMailFormStart(mailNo + i));
			} else {
				start = pageSource.indexOf(getFirstMailFormStart(mailNo));
			}
			if (i < mailsPerPage - 1 && start != -1) {
				end = pageSource.indexOf(getMailFormStart(mailNo + i + 1));
			} else if (start != -1){
				end = pageSource.substring(start).indexOf(MAIL_FORM_END) + start;
			}
			if (start != -1 && end != -1) {
				mailForms[i] = pageSource.substring(start, end);
			}
		}
		return mailForms;
	}

	private String getFirstMailFormStart(int mailNo) {
		return "#" + mailNo;
	}

	private String getMailFormStart(int mailNo) {
		return "\n#" + mailNo + "\n";
	}

	private void write(String content) {
		String path = "res/mails/test3.html";
		try{
			// Create file 
			FileWriter fstream = new FileWriter(path);
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(content);
			//Close the output stream
			out.close();
		}catch (Exception e){//Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
	}

	private boolean hasNextPage(int currentMailNumber) {
		return currentMailNumber != 1;
	}

	private int getNextMailNumber(int currentMailNumber) {
		return (currentMailNumber > mailsPerPage) ? (currentMailNumber - mailsPerPage) : 1;
	}

	private int getNumberOfLastMail(String title) {
		return extractSizeFromTitle(title.trim());
	}

	private int extractSizeFromTitle(String title) {
		int size = -1;
		String[] token = title.split("\\s+");
		if (token.length == 7) {
			String strSize = token[token.length - 1];
			try {
				size = Integer.parseInt(strSize);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		return size;
	}

	private String getPageLink(String listName, int mailNo) {
		return baseURL + listName + mailURL + mailNo + endURL;
	}

	private String buildInitialURL(String listName) {
		return baseURL + listName + mailURL;
	}

}
