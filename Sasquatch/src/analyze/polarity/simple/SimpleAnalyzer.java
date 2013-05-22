package analyze.polarity.simple;

import java.io.File;

import systems.SoftwareSystem;
import systems.source.Source;

import analyze.AnalysisResult;
import analyze.polarity.PolarityAnalyzer;

public class SimpleAnalyzer extends PolarityAnalyzer {

	private static final String DICT_PATH = "res/dict/";
	private static final File ADJECTIVES = new File(DICT_PATH + "adj_dictionary1.11.txt");
	private static final File ADVERBS = new File(DICT_PATH + "adv_dictionary1.11.txt");
	private static final File INTS = new File(DICT_PATH + "int_dictionary1.11.txt");
	private static final File NOUNS = new File(DICT_PATH + "noun_dictionary1.11.txt");
	private static final File VERBS = new File(DICT_PATH + "verb_dictionary1.11.txt");
	
	private static File[] dictionaries = {ADJECTIVES, ADVERBS, INTS, NOUNS, VERBS};
	
	
	public SimpleAnalyzer() {
		
	}


	@Override
	public int analyze(Source s) {
		String text = s.getText();
		return 0;
	}


	@Override
	public AnalysisResult analyze(SoftwareSystem ss) {
		
		return null;
	}

}
