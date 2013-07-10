package main;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import retrieval.interfaces.ICrawlController;
import retrieval.mailinglist.apache.ApacheCrawlController;
import retrieval.mailinglist.appleList.AppleListCrawlController;
import retrieval.mailinglist.eclipseList.EclipseListCrawlController;
import retrieval.mailinglist.javanet.JavaNetCrawlController;
import retrieval.mailinglist.mailArchive.MailArchiveCrawlController;
import retrieval.mailinglist.markmail.MMCrawlController;
import retrieval.mailinglist.pipermail.PiperMailCrawlController;
import retrieval.mailinglist.sourceforge.SFCrawlController;
import retrieval.mailinglist.yahoo.YahooCrawlController;

import manager.systems.ArchiveType;
import manager.systems.SoftwareSystem;
import manager.systems.source.Source;
import manager.systems.source.SourceHandler;
import manager.systems.source.mail.LocalMailHandler;
import manager.systems.source.mail.WebMailHandler;

public class CrawlSystems {

	//USER-BASED SOURCES
	private static SoftwareSystem httpClientUsers = new SoftwareSystem("httpclient", "hc-httpclient-users", 2004, 2013, ArchiveType.APACHE);
	private static SoftwareSystem log4jUsers = new SoftwareSystem("log4j", "logging-log4j-user", 2000, 2013, ArchiveType.APACHE);
	private static SoftwareSystem httpunitUsers = new SoftwareSystem("httpunit", "httpunit-users", 2008, 2008, ArchiveType.SOURCEFORGE);
	private static SoftwareSystem htmlunitUsers = new SoftwareSystem("htmlunit", "htmlunit-user", 2003, 2013, ArchiveType.SOURCEFORGE);
	private static SoftwareSystem jettyUsers = new SoftwareSystem("jetty", "jetty-users", 2009, 2013, ArchiveType.ECLIPSE_LIST);
	private static SoftwareSystem tomcatUsers = new SoftwareSystem("tomcat", "tomcat-users", 2010, 2013, ArchiveType.APACHE); //2000
	private static SoftwareSystem jpaUsers = new SoftwareSystem("jpa", "jpa-spec/lists/users", 2011, 2013, ArchiveType.JAVANET);
	private static SoftwareSystem tapestryUsers = new SoftwareSystem("tapestry", "tapestry-users", 2010, 2013, ArchiveType.APACHE); //2003
	private static SoftwareSystem jsfUsers = new SoftwareSystem("jsf", "javaserverfaces/lists/users", 2004, 2013, ArchiveType.JAVANET);
	private static SoftwareSystem strutsUsers = new SoftwareSystem("struts", "struts-user", 2010, 2013, ArchiveType.APACHE); //2000
	private static SoftwareSystem nekohtmlUsers = new SoftwareSystem("nekohtml", "nekohtml-user", 2007, 2013, ArchiveType.SOURCEFORGE);
	private static SoftwareSystem htmlparserUsers = new SoftwareSystem("htmlparser", "htmlparser-user", 2001, 2013, ArchiveType.SOURCEFORGE);
	private static SoftwareSystem dom4jUsers = new SoftwareSystem("dom4j", "dom4j-user", 2001, 2013, ArchiveType.SOURCEFORGE);
	private static SoftwareSystem jbossUsers = new SoftwareSystem("jboss", "jboss-user", 2006, 2013, 3000, ArchiveType.PIPERMAIL);
	private static SoftwareSystem glassFishUsers = new SoftwareSystem("glassfish", "glassfish/lists/users", 2011, 2013, ArchiveType.JAVANET); //2005
	private static SoftwareSystem resinUsers = new SoftwareSystem("resin", "resin-interest@caucho.com", 2008, 2013, ArchiveType.MAIL_ARCHIVE);

	private static SoftwareSystem[] userSystems = {/*httpClientUsers, log4jUsers, httpunitUsers,
		htmlunitUsers, jettyUsers, tomcatUsers, jpaUsers, tapestryUsers, jsfUsers, strutsUsers,
		nekohtmlUsers, htmlparserUsers, dom4jUsers, jbossUsers, */glassFishUsers, resinUsers};

	//DEVELOPER BASED SOURCES
	private static SoftwareSystem jcronTabDevs = new SoftwareSystem("jcrontab", "jcrontab-developers", 2001, 2013, ArchiveType.SOURCEFORGE);
	private static SoftwareSystem hibernateDevs = new SoftwareSystem("hibernate", "hibernate-dev", 2006, 2013, ArchiveType.PIPERMAIL);
	private static SoftwareSystem httpClientDevs = new SoftwareSystem("httpclient", "hc-dev", 2002, 2013, ArchiveType.APACHE);
	private static SoftwareSystem log4jDevs = new SoftwareSystem("log4j", "logging-log4j-dev", 2000, 2013, ArchiveType.APACHE);
	private static SoftwareSystem httpunitDevs = new SoftwareSystem("httpunit", "httpunit-develop", 2000, 2013, ArchiveType.SOURCEFORGE);
	private static SoftwareSystem htmlunitDevs = new SoftwareSystem("htmlunit", "htmlunit-develop", 2002, 2013, ArchiveType.SOURCEFORGE);
	private static SoftwareSystem jettyDevs = new SoftwareSystem("jetty", "jetty-dev", 2009, 2013, ArchiveType.ECLIPSE_LIST);
	private static SoftwareSystem tomcatDevs = new SoftwareSystem("tomcat", "tomcat-dev", 1999, 2013, ArchiveType.APACHE);
	private static SoftwareSystem jpaDevs = new SoftwareSystem("jpa", "jpa-spec/lists/jsr338-experts", 2011, 2013, ArchiveType.JAVANET);
	private static SoftwareSystem tapestryDevs = new SoftwareSystem("tapestry", "tapestry-dev", 2003, 2013, ArchiveType.APACHE);
	private static SoftwareSystem jsfDevs = new SoftwareSystem("jsf", "javaserverfaces/lists/dev", 2004, 2013, ArchiveType.JAVANET);
	private static SoftwareSystem strutsDevs = new SoftwareSystem("struts", "struts-dev", 2000, 2013, ArchiveType.APACHE);
	private static SoftwareSystem nekohtmlDevs = new SoftwareSystem("nekohtml", "nekohtml-developer", 2007, 2013, ArchiveType.SOURCEFORGE);
	private static SoftwareSystem htmlparserDevs = new SoftwareSystem("htmlparser", "htmlparser-developer", 2001, 2013, ArchiveType.SOURCEFORGE);
	private static SoftwareSystem dom4jDevs = new SoftwareSystem("dom4j", "dom4j-dev", 2001, 2013, ArchiveType.SOURCEFORGE);
	private static SoftwareSystem jBossDevs = new SoftwareSystem("jboss", "jboss-development", 2006, 2013, ArchiveType.PIPERMAIL);
	private static SoftwareSystem glassFishDevs = new SoftwareSystem("glassfish", "glassfish/lists/dev", 2005, 2013, ArchiveType.JAVANET);

	private static SoftwareSystem[] devSystems = {jcronTabDevs,hibernateDevs, httpClientDevs, log4jDevs, httpunitDevs,
		htmlunitDevs, jettyDevs, tomcatDevs, jpaDevs, tapestryDevs, jsfDevs, strutsDevs, nekohtmlDevs, htmlparserDevs,
		dom4jDevs, jBossDevs, glassFishDevs};

	//NOT DEFINED
	private static SoftwareSystem easyMock = new SoftwareSystem("easymock", "easymock", 2002, 2013, ArchiveType.YAHOO);
	private static SoftwareSystem junit = new SoftwareSystem("junit", "junit", 2000, 2013, ArchiveType.YAHOO);
	private static SoftwareSystem jdom = new SoftwareSystem("jdom", "jdom", 2001, 2013, ArchiveType.MARK_MAIL);

	private static SoftwareSystem[] undefinedSystems = {easyMock, junit, jdom};
	
	private static Logger logger = Logger.getLogger("CrawlLog");
	private static int mode = 1; //0 = debug, 1 = log&debug, 2 = log, 3 = nothing

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		
		FileHandler fh;
		
		try {  
            
            // This block configure the logger with handler and formatter  
            fh = new FileHandler("res/crawlLog2.log");  
            logger.addHandler(fh);  
            //logger.setLevel(Level.ALL);  
            SimpleFormatter formatter = new SimpleFormatter();  
            fh.setFormatter(formatter);  
              
        } catch (SecurityException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }
		
		
		Date start = new Date();
//		SoftwareSystem test = new SoftwareSystem("httpclient", "hc-httpclient-users", 2004, 2013, Archive.APACHE);
//		crawlSystem(test, new LocalMailHandler(new File("res/mails/" + test.getName() + ".xml")));
//		crawlUserSystems();
//		crawlDevSystems();
//		crawlUndefinedSystems();
		Date end = new Date();
		long millis = (end.getTime() - start.getTime()) / (60 * 1000);
		log("Crawling finished after : " + millis + " min.");
	}
	
	private static void log(String msg) {
		if (mode == 0) {
			System.out.println(msg);
		} else if (mode == 1) {
			logger.info(msg);
			System.out.println(msg);
		} else if (mode == 2) {
			logger.info(msg);
		}
	}

	private static void crawlUndefinedSystems() {
		for (SoftwareSystem s : undefinedSystems) {
			LocalMailHandler handler = new LocalMailHandler(new File("res/mails/undef/" + s.getName() + ".xml"));
			crawlSystem(s, handler);
		}
	}

	private static void crawlDevSystems() {
		for (SoftwareSystem s : devSystems) {
			LocalMailHandler handler = new LocalMailHandler(new File("res/mails/devs/" + s.getName() + ".xml"));
			crawlSystem(s, handler);
		}
	}
	
	private static void crawlSystem(SoftwareSystem s, LocalMailHandler handler) {
		Date start = new Date();
		s.setHandler(getWebMailHandler(s));
		Source[] sources = s.getSources();
		log("System " + s.getName() + " crawled. " + s.getSources().length + " Results. ");
		handler.addSources(sources);
		log("Written on File.");
		Date end = new Date();
		long millis = (end.getTime() - start.getTime()) / (60 * 1000);
		log("Crawling finished after : " + millis + " min.");
	}

	private static void crawlUserSystems() {
		for (SoftwareSystem s : userSystems) {
			LocalMailHandler handler = new LocalMailHandler(new File("res/mails/users/" + s.getName() + ".xml"));
			crawlSystem(s, handler);
		}
	}

	private static WebMailHandler getWebMailHandler(SoftwareSystem s) {
		WebMailHandler handler = null;
		ICrawlController controller = getCrawlController(s);
		if (controller != null) {
			handler = new WebMailHandler(controller);
		}
		return handler;
	}

	private static ICrawlController getCrawlController(SoftwareSystem s) {
		ICrawlController controller = null;
		switch(s.getArchiveType()) {
		case APACHE: controller = new ApacheCrawlController(s.getListName(), s.getStart(), s.getEnd()); break;
		case ECLIPSE_LIST: controller = new EclipseListCrawlController(s.getListName(), s.getStart(), s.getEnd(), s.getPages()); break;
		case APPLE_LIST : controller = new AppleListCrawlController(new String[]{s.getListName()}, s.getPages()); break;
		case JAVANET : controller = new JavaNetCrawlController(s.getListName(), s.getStart(), s.getEnd()); break;
		case MAIL_ARCHIVE : controller = new MailArchiveCrawlController(s.getListName(), s.getStart(), s.getEnd(), s.getPages()); break;
		case MARK_MAIL : controller = new MMCrawlController(s.getListName(), s.getStart(), s.getEnd(), s.getPages()); break;
		case PIPERMAIL : controller = new PiperMailCrawlController(new String[]{s.getListName()}, s.getPages()); break;
		case SOURCEFORGE : controller = new SFCrawlController(s.getListName(), s.getStart(), s.getEnd()); break;
		case YAHOO : controller = new YahooCrawlController(s.getListName(), s.getStart(), s.getEnd(), s.getPages()); break;
		case LOCAL: 
		default: controller = null;
		}
		return controller;
	}

}
