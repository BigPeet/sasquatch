package analyzer.polarity.sentiwordnet;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

import manager.systems.SoftwareSystem;
import manager.systems.source.Source;
import analyzer.dictionary.IDictionary;
import analyzer.dictionary.POSTagger;
import analyzer.dictionary.SentiWordNetDictionary;
import analyzer.dictionary.POSTaggedWord;
import analyzer.dictionary.Word;
import analyzer.interfaces.IAnalysisResult;
import analyzer.nlp.StanfordParser;
import analyzer.polarity.PolarityAnalyzer;
import analyzer.polarity.simple.SimplePolarityResult;

public class SentiPolarityAnalyzer extends PolarityAnalyzer {
	
	private static final File dictFile = new File("res/dict/SentiWordNet.txt");
	private IDictionary dict;
	private Date limit;
	//about 3 years
	private static int time = 3;
	private static int MAX_SOURCES = 500;
	
	public SentiPolarityAnalyzer() {
		dict = new SentiWordNetDictionary(dictFile);
		Calendar now = Calendar.getInstance();
		now.add(Calendar.YEAR, -time);
		limit = now.getTime();
	}

	@Override
	public IAnalysisResult analyze(SoftwareSystem ss) {
		SimplePolarityResult res = new SimplePolarityResult();
		StanfordParser parser = new StanfordParser(dict);
//		POSTagger tagger = new POSTagger();
		int counter = 0;
		int x = 1;
		for (Source s : ss.getSources()) {
			if (counter > MAX_SOURCES - 1) {
				break;
			}
			if (s.getDate() != null && s.getDate().after(limit)) {
				if (x % 100 == 0) 
					System.out.println(x);
				String text = s.getText();
				
				String[] sentences = StanfordParser.getSentences(text);
				POSTaggedWord[] taggedWords = parser.getGeneralTaggedWords(sentences);
				
//				for (String sentence : sentences) {
//					System.out.println("Satz:" + sentence);
//				}
//				Word[] words = getWords(text);
//				TaggedWord[] taggedWords = tagger.tagWords(words);
//				for (TaggedWord tw : taggedWords) {
//					double score = dict.getScore(tw);
//					tw.setValue(score);
//					System.out.println(tw + " - " + tw.getValue());
//				}
				counter++;
				res.addSource(s, taggedWords);
			} else {
				Word[] words = new Word[0];
				res.addSource(s, words);
			}
			x++;
		}
		res.updateScore();
		return res;
	}

	private Word[] getWords(String text) {
		text = replacePunctuation(text);
		String[] wordTexts = text.split("\\s+");
		Word[] words = new Word[wordTexts.length];
		for (int i = 0; i < wordTexts.length; i++) {
			words[i] = new Word(wordTexts[i]);
		}
		return words;
	}

	private String replacePunctuation(String text) {
		//ReplaceAll non-char, non-numbers?
		text = text.replace(".", " ");
		text = text.replace("!", " ");
		text = text.replace("?", " ");
		text = text.replace(",", " ");
		text = text.replace(":", " ");
		text = text.replace(";", " ");
		text = text.replace("(", " ");
		text = text.replace(")", " ");
		text = text.replace("[", " ");
		text = text.replace("]", " ");
		text = text.replace("}", " ");
		text = text.replace("{", " ");
		return text.trim();
	}

}
