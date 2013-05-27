package analyze.polarity.simple;

import java.io.File;

import systems.source.SimpleSourceIndexer;
import systems.source.SimpleSourceQuery;
import systems.source.Source;
import systems.source.mail.Mail;
import systems.source.mail.SimpleMailIndexer;
import systems.source.mail.SimpleMailQuery;

public class AnalyzerTest {

	private static String BODY_1 = "My good is trashy";
	private static String INDEX_PATH = "res/lucene/mail";
	
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
		
		Mail m = new Mail("header", BODY_1);
		Source[] sources = {m};
		SimpleResult result = new SimpleResult(sources);

		File indexDir = new File(INDEX_PATH);
		SimpleSourceIndexer indexer = new SimpleMailIndexer(indexDir);
		for (Source s : sources) {
			indexer.addSource(s);
		}
		indexer.close();
		SimpleSourceQuery querier = new SimpleMailQuery(indexDir);

		/*Maybe use BooleanQueries to combine words to one query instead.
		Somehow I need to get the score applied differently. 
		IDEA: 1. Do BooleanQuerie where you combine words via OR.
			  2. Combine Source with score of search. (e.g. Wrapper-Class)
			  3. Return that and then use it here to update the Result.
		 */
		for (File dict : dictionaries) {
			DictionaryParser parser = new DictionaryParser(dict);
			Word[] words = parser.getWords();
			for (Word word : words) {
				Source[] results = querier.query(word.getWord());
				result.addQueryResult(word, results);
			}
		}

		querier.close();
		
		result.show();

	}

}
