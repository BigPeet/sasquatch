package main;

import java.io.File;

import analyze.AnalysisResult;
import analyze.SentimentAnalyzer;
import analyze.polarity.simple.SimplePolarityAnalyzer;


import systems.SoftwareSystem;
import systems.source.SourceHandler;
import systems.source.SourceIndexer;
import systems.source.SourceQuery;
import systems.source.mail.LocalMailHandler;
import systems.source.mail.SimpleMailIndexer;
import systems.source.mail.SimpleMailQuery;

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
		
		jbossResult.show();
		System.out.println("-------------");
		jdomResult.show();
		System.out.println("-------------");
		dom4jResult.show();
	}

}
