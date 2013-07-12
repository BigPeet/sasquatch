package main;

import java.io.File;

import analyzer.SentimentAnalyzer;
import analyzer.polarity.sentiwordnet.SentiPolarityAnalyzer;

import manager.parser.SystemParser;
import gui.MainFrame;

public class Client {
	
	private static SentimentAnalyzer[] analyzer = {new SentiPolarityAnalyzer()};
	private static String path = "res/test1.xml";
	private SystemParser parser;
	private MainFrame mf;
	
	private static Client client = new Client();
	
	private Client() {
		parser = new SystemParser(new File(path));
		mf = new MainFrame();
		mf.setSystems(parser.getSoftwareSystems());
		mf.setAnalyzer(analyzer);
	}

	public static Client getInstance() {
		return client;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		client.mf.run();
	}
}
