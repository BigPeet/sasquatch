package analyzer.dictionary;

import java.io.File;

public class DictionaryTester {
	
	private static final String DICT_PATH = "res/dict/";
	private static final File ADJECTIVES = new File(DICT_PATH + "adj_dictionary1.11.txt");
	private static final File ADVERBS = new File(DICT_PATH + "adv_dictionary1.11.txt");
	private static final File INTS = new File(DICT_PATH + "int_dictionary1.11.txt");
	private static final File NOUNS = new File(DICT_PATH + "noun_dictionary1.11.txt");
	private static final File VERBS = new File(DICT_PATH + "verb_dictionary1.11.txt");
	private static final File SENTI = new File(DICT_PATH + "SentiWordNet.txt");
	private static final File contextDictFile = new File("res/dict/SQ.txt");
	private static final File[] contextDictFiles = {contextDictFile};
	
	//Some of the dictionaries need some work...parse better in Word-Class.
	private static File[] dictionaries = {ADJECTIVES, NOUNS};

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		SimpleFileDictionary parser = new SimpleFileDictionary(VERBS);
//		parser.printDict();
		SentiWordNetDictionary dict = new SentiWordNetDictionary(SENTI, contextDictFiles);
		double score = dict.getScore(new POSTaggedWord("document", "n"));
		System.out.println(score);

	}

}
