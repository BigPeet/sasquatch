package analyzer.polarity.simple;

import java.io.File;

import manager.systems.SoftwareSystem;
import manager.systems.source.SimpleSourceIndexer;
import manager.systems.source.SimpleSourceQuery;
import manager.systems.source.Source;
import manager.systems.source.SourceIndexer;
import manager.systems.source.SourceQuery;
import manager.systems.source.mail.SimpleMailIndexer;
import manager.systems.source.mail.SimpleMailQuery;

import analyzer.AnalysisResult;
import analyzer.dictionary.SimpleFileDictionary;
import analyzer.dictionary.Word;
import analyzer.polarity.PolarityAnalyzer;

public class SimplePolarityAnalyzer extends PolarityAnalyzer {

	private static final String DICT_PATH = "res/dict/";
	private static final File ADJECTIVES = new File(DICT_PATH + "adj_dictionary1.11.txt");
	private static final File ADVERBS = new File(DICT_PATH + "adv_dictionary1.11.txt");
	private static final File INTS = new File(DICT_PATH + "int_dictionary1.11.txt");
	private static final File NOUNS = new File(DICT_PATH + "noun_dictionary1.11.txt");
	private static final File VERBS = new File(DICT_PATH + "verb_dictionary1.11.txt");

	//Some of the dictionaries need some work...parse better in Word-Class.
	private static File[] dictionaries = {ADJECTIVES, NOUNS, ADVERBS, INTS, VERBS};

	private SourceIndexer indexer;
	private SourceQuery querier;

	public SimplePolarityAnalyzer(File indexDir) {
		this.indexer = new SimpleSourceIndexer(indexDir);
		this.querier = new SimpleSourceQuery(indexDir);
	}

	public SimplePolarityAnalyzer(SourceIndexer indexer, SourceQuery querier) {
		this.indexer = indexer;
		this.querier = querier;
	}

	@Override
	public AnalysisResult analyze(SoftwareSystem ss) {

		SimplePolarityResult result = new SimplePolarityResult(ss.getSources());

		//Create index
		if (indexer.open()) {
			for (Source s : ss.getSources()) {
				indexer.addSource(s);
			}
			indexer.close();
		}

		//Query index
		if (querier.open()) {
			for (File dict : dictionaries) {
				SimpleFileDictionary dictionary = new SimpleFileDictionary(dict);
				for (Word word : dictionary.getWords()) {
					Source[] results = querier.query(word.getWord());
					result.addQueryResult(word, results);
				}
			}
			querier.close();
		}
		return result;
	}

}
