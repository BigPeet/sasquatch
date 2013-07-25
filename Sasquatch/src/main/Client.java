package main;

import java.io.File;

import analyzer.SentimentAnalyzer;
import analyzer.interfaces.IAnalysisResult;
import analyzer.polarity.sentiwordnet.SentiPolarityAnalyzer;

import manager.parser.SystemParser;
import manager.systems.SoftwareSystem;
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

	public void saveResults(SoftwareSystem[] systems, IAnalysisResult[] results) {
		if (systems.length == results.length) {
			for (int i = 0; i < systems.length; i++) {
				parser.addResultsToSoftwareSystem(systems[i], results[i]);
			}
		}
	}

	public void removeSoftwareSystem(SoftwareSystem ss) {
		parser.removeSoftwareSystem(ss);
	}

	public SoftwareSystem[] getSoftwareSystems() {
		return parser.getSoftwareSystems();
	}

	public void addSoftwareSystem(SoftwareSystem ss) {
		parser.addSoftwareSystem(ss);
	}

	public void removeSoftwareSystems(SoftwareSystem[] toBeRemoved) {
		parser.removeSoftwareSystems(toBeRemoved);
	}

	public void editSoftwareSystem(String name, SoftwareSystem created) {
		parser.editSoftwareSystem(name, created);
	}
}
