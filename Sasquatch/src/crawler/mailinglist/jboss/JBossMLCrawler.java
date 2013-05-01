package crawler.mailinglist.jboss;

import parser.mail.jboss.JBossMLParser;
import crawler.mailinglist.Mail;
import crawler.mailinglist.MailingListCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;

public class JBossMLCrawler extends MailingListCrawler {

	@Override
	protected String trimContent(HtmlParseData htmlContent) {
		return htmlContent.getText();
	}

}
