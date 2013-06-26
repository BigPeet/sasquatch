package analyzer.dictionary;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.Vector;

public class SentiWordNetDictionary implements IDictionary {
	
	private static final String IN_WORD_SEPARATOR = "#";
	private static final String COLUMN_SEPARATOR = "\t";
	private static final String TERM_SEPARATOR = " ";
	private static final int POS_COLUMN = 0;
	private static final int POSITIVE_SCORE_COLUMN = 2;
	private static final int NEGATIVE_SCORE_COLUMN = 3;
	private static final int TERMS_COLUMN = 4;
	private static final int WORD_INDEX = 0;
	private static final int INDEX_INDEX = 1;
	
	private HashMap<String, Double> dict = new HashMap<String, Double>();
	private ArrayList<POSTaggedWord> words = new ArrayList<POSTaggedWord>();
	
	
	public SentiWordNetDictionary() {
		
	}
	
	public SentiWordNetDictionary(File dictFile) {
		extractWords(dictFile);
	}
	

	@Override
	public void extractWords(File dictFile) {
		HashMap<String, Vector<Double>> vectorScores = getVectorScoreMap(dictFile);
		Set<String> keys = vectorScores.keySet();
		for (String word : keys) {
			POSTaggedWord tw = getTaggedWordFromString(word);
			Vector<Double> v = vectorScores.get(word);
			double score = calculateScore(v);
			tw.setValue(score);
			dict.put(tw.toString(), score);
			words.add(tw);
		}
	}
	
	public POSTaggedWord getTaggedWordFromString(String word) {
		POSTaggedWord tw = null;
		String[] token = word.split(IN_WORD_SEPARATOR);
		if (token.length == 2) {
			String text = token[0];
			String pos = token[1];
			tw = new POSTaggedWord(text, pos);
		}
		return tw;
	}
	
	public POSTaggedWord[] getWords() {
		return words.toArray(new POSTaggedWord[words.size()]);
	}
	
	public double getScore(String word, String pos) {
		POSTaggedWord tw = new POSTaggedWord(word, pos);
		return getScore(tw);
	}
	
	@Override
	public double getScore(Word w) {
		double score = 0.0;
		int counter = 0;
		for (SentiWordPOS tag : SentiWordPOS.TAGS) {
			double tmp = getScore(w.getWord(), tag.getTag());
			if (tmp != 0.0) {
				counter++;
				score += tmp;
			}
		}
		if (counter != 0) {
			score /= counter;
		}
		return score;
	}

	@Override
	public double getScore(POSTaggedWord word) {
		double score = 0.0;
		POSTaggedWord sentiWord = getSentiTaggedWord(word.getWord(), word.getPOS());
		if (dict.containsKey(sentiWord.toString())) {
			score = dict.get(sentiWord.toString());
		}
		return score;
	}


	private POSTaggedWord getSentiTaggedWord(String text, String pos) {
		text = text.toLowerCase();
		Stemmer stemmer = new Stemmer();
		stemmer.add(text);
		stemmer.stem();
		text = stemmer.toString();
		String sentiTag = SentiWordPOS.convertToSentiWordTag(pos);
		return new POSTaggedWord(text, sentiTag);
	}

	public static void main(String[] args) {
		File f = new File("res/dict/SentiWordNet.txt");
		SentiWordNetDictionary dict = new SentiWordNetDictionary();
		dict.extractWords(f);
		double score = dict.getScore("shit", "n");
		System.out.println(score);
	}

	private HashMap<String, Vector<Double>> getVectorScoreMap(File dictFile) {
		HashMap<String, Vector<Double>> temp = new HashMap<String, Vector<Double>>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(dictFile));
			String line = "";
			while ((line = reader.readLine()) != null) {
				if (!isComment(line)) {
					addLine(temp, line);
				}
			}
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return temp;
	}

	private void addLine(HashMap<String, Vector<Double>> map, String line) {
		
		String[] columns = line.split(COLUMN_SEPARATOR);
		String pos = columns[POS_COLUMN];
		double posScore = Double.parseDouble(columns[POSITIVE_SCORE_COLUMN]);
		double negScore = Double.parseDouble(columns[NEGATIVE_SCORE_COLUMN]);
		String termString = columns[TERMS_COLUMN];
		String[] terms = termString.split(TERM_SEPARATOR);
		
		for (String term : terms) {
			String[] token = term.split(IN_WORD_SEPARATOR);
			int index = Integer.parseInt(token[INDEX_INDEX]) - 1;
			String word = token[WORD_INDEX].replace("_", " ");
			POSTaggedWord tw = new POSTaggedWord(word, pos);
			double score = calculateScore(posScore, negScore);
			Vector<Double> v = null;
			if (map.containsKey(tw.toString())) {
				v = map.get(tw.toString());
			} else {
				v = new Vector<Double>();
			}
			addScore(v, score);
			map.put(tw.toString(), v);
		}
	}

	private void addScore(Vector<Double> v, double score) {
		v.add(score);
	}

	private double calculateScore(Vector<Double> v) {
		double score = 0.0;
		double sum = v.size();
		for(int i = 0; i < v.size(); i++)
			score += v.get(i);
		score /= sum;
		return score;
	}

	private double calculateScore(double posScore, double negScore) {
		return posScore - negScore;
	}

	private boolean isComment(String line) {
		return line.trim().startsWith("#");
	}
}
