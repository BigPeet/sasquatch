package main;

import java.io.File;

import analyzer.AnalysisResult;
import analyzer.SentimentAnalyzer;
import analyzer.polarity.simple.SimplePolarityAnalyzer;


import manager.systems.SoftwareSystem;
import manager.systems.source.SourceHandler;
import manager.systems.source.SourceIndexer;
import manager.systems.source.SourceQuery;
import manager.systems.source.mail.LocalMailHandler;
import manager.systems.source.mail.SimpleMailIndexer;
import manager.systems.source.mail.SimpleMailQuery;

public class Start {

	private static String JBOSS = "res/mails/jboss.xml";
	private static String DOM4J = "res/mails/dom4j.xml";
	private static String JDOM = "res/mails/jdom.xml";
	private static String INDEX_PATH = "res/lucene/mail";
	
	public static void main(String[] args) {
	
		/* Initialize Handlers. In this case we use local handlers using the given xml-files. */
		SourceHandler jdomHandler = new LocalMailHandler(new File(JDOM));
		SourceHandler jbossHandler = new LocalMailHandler(new File(JBOSS));
		SourceHandler dom4jHandler = new LocalMailHandler(new File(DOM4J));
		
		/* Create software systems using the handlers. */
		SoftwareSystem jboss = new SoftwareSystem(jbossHandler);
		SoftwareSystem jdom = new SoftwareSystem(jdomHandler);
		SoftwareSystem dom4j = new SoftwareSystem(dom4jHandler);
		
		/* We will use a SimplePolarityAnalzyer. We need Lucene-Indexes.*/
		File indexDir = new File(INDEX_PATH);
		SourceIndexer indexer = new SimpleMailIndexer(indexDir);
		SourceQuery querier = new SimpleMailQuery(indexDir);
		SentimentAnalyzer analyzer = new SimplePolarityAnalyzer(indexer, querier);	
		
		/* Now we will analyze the 3 systems and show the results.*/
		AnalysisResult jbossResult = analyzer.analyze(jboss);
		AnalysisResult jdomResult = analyzer.analyze(jdom);
		AnalysisResult dom4jResult = analyzer.analyze(dom4j);
		
		System.out.println("JBoss Size: " + jboss.getSources().length);
		jbossResult.show();
		System.out.println("-------------");
		System.out.println("JDom Size: " + jdom.getSources().length);
		jdomResult.show();
		System.out.println("-------------");
		System.out.println("Dom4j Size: " + dom4j.getSources().length);
		dom4jResult.show();
	}

}
