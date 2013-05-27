package main;

import java.io.File;

import analyze.AnalysisResult;
import analyze.polarity.simple.SimplePolarityAnalyzer;


import systems.SoftwareSystem;
import systems.source.mail.LocalMailHandler;

public class Start {

	private static String PATH = "res/mails/jboss.xml";
	
	public static void main(String[] args) {
		//SoftwareSystem jboss = new SoftwareSystem(new WebMailHandler(new JBossMLCrawlController()));
		SoftwareSystem jboss = new SoftwareSystem(new LocalMailHandler(new File(PATH)));
		
		SimplePolarityAnalyzer analyzer = new SimplePolarityAnalyzer();
		AnalysisResult result = analyzer.analyze(jboss);
		result.show();
		
	}

}
