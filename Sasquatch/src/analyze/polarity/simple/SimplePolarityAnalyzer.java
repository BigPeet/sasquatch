package analyze.polarity.simple;

import java.io.File;

import systems.SoftwareSystem;
import systems.source.Source;
import systems.source.SourceIndexer;
import systems.source.SourceQuery;
import systems.source.mail.SimpleMailIndexer;
import systems.source.mail.SimpleMailQuery;

import analyze.AnalysisResult;
import analyze.polarity.PolarityAnalyzer;

public class SimplePolarityAnalyzer extends PolarityAnalyzer {

	private static final String DICT_PATH = "res/dict/";
	private static final File ADJECTIVES = new File(DICT_PATH + "adj_dictionary1.11.txt");
	private static final File ADVERBS = new File(DICT_PATH + "adv_dictionary1.11.txt");
	private static final File INTS = new File(DICT_PATH + "int_dictionary1.11.txt");
	private static final File NOUNS = new File(DICT_PATH + "noun_dictionary1.11.txt");
	private static final File VERBS = new File(DICT_PATH + "verb_dictionary1.11.txt");
	
	//Some of the dictionaries need some work...parse better in Word-Class.
	private static File[] dictionaries = {ADJECTIVES, NOUNS, ADVERBS, INTS, VERBS};
	private static String INDEX_PATH = "res/lucene/mail";
	
	public SimplePolarityAnalyzer() {
		
	}

	//TODO: Indexer und Query irgendwo setzen, damit man hier nicht festlegen muss, was das ist.
	@Override
	public AnalysisResult analyze(SoftwareSystem ss) {
		
		SimplePolarityResult result = new SimplePolarityResult(ss.getSources());
		
		File indexDir = new File(INDEX_PATH);
		SourceIndexer indexer = new SimpleMailIndexer(indexDir);
		for (Source s : ss.getSources()) {
			indexer.addSource(s);
		}
		indexer.close();
		SourceQuery querier = new SimpleMailQuery(indexDir);
		
		/*Maybe use BooleanQueries to combine words to one query instead.
		Somehow I need to get the score applied differently. 
		IDEA: 1. Do BooleanQuerie where you combine words via OR.
			  2. Combine Source with score of search. (e.g. Wrapper-Class)
			  3. Return that and then use it here to update the Result.
		*/
		for (File dict : dictionaries) {
			Dictionary dictionary = new Dictionary(dict);
			Word[] words = dictionary.getWords();
			for (Word word : words) {
				Source[] results = querier.query(word.getWord());
				result.addQueryResult(word, results);
			}
		}
		
		querier.close();
		
		return result;
	}

}
