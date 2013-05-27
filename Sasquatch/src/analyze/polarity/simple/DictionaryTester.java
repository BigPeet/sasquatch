package analyze.polarity.simple;

import java.io.File;

public class DictionaryTester {
	
	private static final String DICT_PATH = "res/dict/";
	private static final File ADJECTIVES = new File(DICT_PATH + "adj_dictionary1.11.txt");
	private static final File ADVERBS = new File(DICT_PATH + "adv_dictionary1.11.txt");
	private static final File INTS = new File(DICT_PATH + "int_dictionary1.11.txt");
	private static final File NOUNS = new File(DICT_PATH + "noun_dictionary1.11.txt");
	private static final File VERBS = new File(DICT_PATH + "verb_dictionary1.11.txt");
	
	//Some of the dictionaries need some work...parse better in Word-Class.
	private static File[] dictionaries = {ADJECTIVES, NOUNS};

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Dictionary parser = new Dictionary(VERBS);
		parser.printDict();

	}

}
