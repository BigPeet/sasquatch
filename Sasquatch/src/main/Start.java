package main;

import html.MMMailParser;
import html.SFMailParser;

import java.io.File;

import parser.mail.MailParser;
import parser.mail.jboss.JBossMLParser;
import crawler.interfaces.ICrawlController;
import crawler.mailinglist.jboss.JBossMLCrawlController;
import analyze.AnalysisResult;
import analyze.SentimentAnalyzer;
import analyze.polarity.simple.SimplePolarityAnalyzer;


import selenium.MMCrawlController;
import selenium.SFCrawlController;
import systems.SoftwareSystem;
import systems.source.SourceHandler;
import systems.source.SourceIndexer;
import systems.source.SourceQuery;
import systems.source.mail.LocalMailHandler;
import systems.source.mail.Mail;
import systems.source.mail.SimpleMailIndexer;
import systems.source.mail.SimpleMailQuery;
import systems.source.mail.WebMailHandler;

public class Start {

	private static String TEST = "res/mails/dummy.xml";
	private static String JBOSS = "res/mails/jboss.xml";
	private static String DOM4J = "res/mails/dom4j.xml";
	private static String JDOM = "res/mails/jdom.xml";
	private static String INDEX_PATH = "res/lucene/mail";
	
	public static void main(String[] args) {
		
		File indexDir = new File(INDEX_PATH);
		SourceIndexer indexer = new SimpleMailIndexer(indexDir);
		SourceQuery querier = new SimpleMailQuery(indexDir);
		SentimentAnalyzer analyzer = new SimplePolarityAnalyzer(indexer, querier);		
		
		SourceHandler localHandler = new LocalMailHandler(new File(JDOM));
		
		MailParser dummyParser = new SFMailParser(TEST);
		MailParser dom4jParser = new SFMailParser(DOM4J);
		MailParser jdomParser = new MMMailParser(JDOM);
		MailParser jbossParser = new JBossMLParser(JBOSS);
		
		String listName = "jdom";
		int pages = 50;
		ICrawlController jdomController = new MMCrawlController(jdomParser, listName, pages);
		
		
//		ICrawlController jbossController = new JBossMLCrawlController(jbossParser);
//		
		SourceHandler webHandler = new WebMailHandler(jdomController);
		
		
		SoftwareSystem ss = new SoftwareSystem(webHandler);
		
		
		System.out.println("Size: " + ss.getSources().length);
		
		AnalysisResult result = analyzer.analyze(ss);
		result.show();
		
//		SoftwareSystem localSS = new SoftwareSystem(localHandler);
//		Mail[] mails = (Mail[]) localSS.getSources();
//		System.out.println(mails[3]);
//		AnalysisResult localResult = analyzer.analyze(localSS);
//		localResult.show();
		
	}

}
