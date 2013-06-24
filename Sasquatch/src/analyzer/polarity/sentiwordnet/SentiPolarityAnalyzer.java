package analyzer.polarity.sentiwordnet;

import java.io.File;

import manager.systems.SoftwareSystem;
import manager.systems.source.Source;
import analyzer.dictionary.IDictionary;
import analyzer.dictionary.POSTagger;
import analyzer.dictionary.SentiWordNetDictionary;
import analyzer.dictionary.TaggedWord;
import analyzer.dictionary.Word;
import analyzer.interfaces.IAnalysisResult;
import analyzer.polarity.PolarityAnalyzer;
import analyzer.polarity.simple.SimplePolarityResult;

public class SentiPolarityAnalyzer extends PolarityAnalyzer {
	
	private static final File dictFile = new File("res/dict/SentiWordNet.txt");
	private IDictionary dict;
	
	public SentiPolarityAnalyzer() {
		dict = new SentiWordNetDictionary(dictFile);
	}

	@Override
	public IAnalysisResult analyze(SoftwareSystem ss) {
		SimplePolarityResult res = new SimplePolarityResult(ss.getSources());
		POSTagger tagger = new POSTagger();
		for (Source s : ss.getSources()) {
			String text = s.getText();
			Word[] words = getWords(text);
			TaggedWord[] taggedWords = tagger.tagWords(words);
			for (TaggedWord tw : taggedWords) {
				double score = dict.getScore(tw);
				tw.setValue(score);
				System.out.println(tw + " - " + tw.getValue());
			}
			res.addSource(s, taggedWords);
		}
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
