package main;

import analyzer.SentimentAnalyzer;
import analyzer.interfaces.IAnalysisResult;
import analyzer.polarity.sentiwordnet.SentiPolarityAnalyzer;
import manager.systems.SoftwareSystem;
import manager.systems.source.Source;
import manager.systems.source.mail.Mail;

public class AnalyzerTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		
		
//		String text = "This isn't good.";
//		Mail m = new Mail("", text);
//		Source[] sources = {m};
//		SoftwareSystem system = new SoftwareSystem("testSystem", sources);
//		SentimentAnalyzer analyzer = new SentiPolarityAnalyzer();
//
//		IAnalysisResult res = analyzer.analyze(system);
//		res.show();
	}

}
