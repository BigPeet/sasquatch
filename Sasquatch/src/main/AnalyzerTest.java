package main;

import java.io.File;

import analyzer.SentimentAnalyzer;
import analyzer.interfaces.IAnalysisResult;
import analyzer.polarity.sentiwordnet.SentiPolarityAnalyzer;
import manager.systems.Archive;
import manager.systems.ArchiveType;
import manager.systems.SoftwareSystem;
import manager.systems.source.Source;
import manager.systems.source.SourceHandler;
import manager.systems.source.mail.LocalMailHandler;
import manager.systems.source.mail.Mail;

public class AnalyzerTest {

	private static String baseURL = "res/mails/";
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Archive a = new Archive(ArchiveType.LOCAL, baseURL + "APS.xml");
//		LocalMailHandler handler = new LocalMailHandler(new File(baseURL + "APS.xml"));
		SourceHandler handler = a.getSourceHandler();
		SoftwareSystem system = new SoftwareSystem("APS", handler);
		SentimentAnalyzer analyzer = new SentiPolarityAnalyzer();
		IAnalysisResult res = analyzer.analyze(system);
		res.show();
	}

}
