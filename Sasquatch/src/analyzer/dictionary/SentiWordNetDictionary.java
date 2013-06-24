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
	private ArrayList<TaggedWord> words = new ArrayList<TaggedWord>();
	
	
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
			TaggedWord tw = getTaggedWordFromString(word);
			System.out.print(tw);
			Vector<Double> v = vectorScores.get(word);
			System.out.println(" " + v);
			double score = calculateScore(v);
			tw.setValue(score);
			dict.put(tw.toString(), score);
			words.add(tw);
		}
	}
	
	public TaggedWord getTaggedWordFromString(String word) {
		TaggedWord tw = null;
		String[] token = word.split(IN_WORD_SEPARATOR);
		if (token.length == 2) {
			String text = token[0];
			String pos = token[1];
			tw = new TaggedWord(text, pos);
		}
		return tw;
	}
	
	public TaggedWord[] getWords() {
		return words.toArray(new TaggedWord[words.size()]);
	}
	
	public double getScore(String word, String pos) {
		TaggedWord tw = new TaggedWord(word, pos);
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

	public double getScore(TaggedWord word) {
		double score = 0.0;
		TaggedWord sentiWord = getSentiTaggedWord(word.getWord(), word.getPOS());
		if (dict.containsKey(sentiWord.toString())) {
			score = dict.get(sentiWord.toString());
		}
		return score;
	}


	private TaggedWord getSentiTaggedWord(String text, String pos) {
		String sentiTag = SentiWordPOS.convertToSentiWordTag(pos);
		return new TaggedWord(text, sentiTag);
	}

	public static void main(String[] args) {
		File f = new File("res/dict/SentiWordNetTest.txt");
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
			TaggedWord tw = new TaggedWord(word, pos);
			double score = calculateScore(posScore, negScore);
			Vector<Double> v = null;
			if (map.containsKey(tw.toString())) {
				v = map.get(tw.toString());
			} else {
				v = new Vector<Double>();
			}
			addScore(v, score, index);
			map.put(tw.toString(), v);
		}
	}

	private void addScore(Vector<Double> v, double score, int index) {
		if (index > v.size()) {
			for (int i = v.size(); i < index; i++) {
				v.add(0.0);
			}
		}
		v.add(index, score);
	}

	private double calculateScore(Vector<Double> v) {
		double score = 0.0;
		double sum = 0.0;
		for(int i = 0; i < v.size(); i++)
			score += ((double) 1 / (double)(i + 1)) * v.get(i);
		for(int i = 1; i <= v.size(); i++)
			sum += (double) 1 / (double) i;
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
