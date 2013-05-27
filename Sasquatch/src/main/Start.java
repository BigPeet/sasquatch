package main;

import java.io.File;

import analyze.AnalysisResult;
import analyze.polarity.simple.SimplePolarityAnalyzer;


import systems.SoftwareSystem;
import systems.source.SimpleSourceIndexer;
import systems.source.SimpleSourceQuery;
import systems.source.Source;
import systems.source.mail.LocalMailHandler;
import systems.source.mail.SimpleMailIndexer;
import systems.source.mail.SimpleMailQuery;

public class Start {

	private static String PATH = "res/mails/jboss.xml";
	private static String INDEX_PATH = "res/lucene/mail";
	private static String QUERY = "good";
	
	public static void main(String[] args) {
		//SoftwareSystem jboss = new SoftwareSystem(new WebMailHandler(new JBossMLCrawlController()));
		SoftwareSystem jboss = new SoftwareSystem(new LocalMailHandler(new File(PATH)));
		
		
//		System.out.println(jboss.getSources().length);
//		File indexDir = new File(INDEX_PATH);
//		SimpleSourceIndexer indexer = new SimpleMailIndexer(indexDir);
//		for (Source s : jboss.getSources()) {
//			indexer.addSource(s);
//		}
//		indexer.close();
		
		
//		SimpleSourceQuery querier = new SimpleMailQuery(indexDir);
//		Source[] results = querier.query(QUERY);
//		querier.close();
//		System.out.println("Size of Results: " + results.length);
//		for (Source s : results) {
//			System.out.println(s);
//		}
		
		
		SimplePolarityAnalyzer analyzer = new SimplePolarityAnalyzer();
		AnalysisResult result = analyzer.analyze(jboss);
		result.show();
		
	}

}
