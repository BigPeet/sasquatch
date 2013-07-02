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

import org.tartarus.snowball.SnowballProgram;
import org.tartarus.snowball.SnowballStemmer;

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
	private ArrayList<Word> words = new ArrayList<Word>();

	public SentiWordNetDictionary() {
		
	}

	public SentiWordNetDictionary(File dictFile) {
		extractWords(dictFile);
	}
	
	public SentiWordNetDictionary(File mainDictFile, File[] contextDictFiles) {
		extractWords(mainDictFile, contextDictFiles);
	}


	public void extractWords(File mainDictFile, File[] contextDictFiles) {
		HashMap<String, Vector<Double>> mainScoreVectors = getVectorScoreMap(mainDictFile);
		HashMap<String, Vector<Double>> contextScoreVectors = getVectorScoreMap(contextDictFiles);
		
		ArrayList<String> keys = new ArrayList<String>();
		keys.addAll(mainScoreVectors.keySet());
		for (String key : contextScoreVectors.keySet()) {
			if (!keys.contains(key)) {
				keys.add(key);
			}
		}
		for (String word : keys) {
			POSTaggedWord tw = getTaggedWordFromString(word);
			Vector<Double> mainVector = new Vector<Double>();
			Vector<Double> contextVector = new Vector<Double>();
			if (mainScoreVectors.containsKey(word)) {
				mainVector = mainScoreVectors.get(word);
			}
			if (contextScoreVectors.containsKey(word)) {
				contextVector = contextScoreVectors.get(word);
			}
			double score = calculateScore(mainVector, contextVector);
			tw.setValue(score);
			dict.put(tw.toString(), score);
			words.add(tw);
		}
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

	@Override
	public  Word[] getWords() {
		return words.toArray(new Word[words.size()]);
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
		String sentiTag = SentiWordPOS.convertToSentiWordTag(pos);
		return new POSTaggedWord(text, sentiTag);
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

	private HashMap<String, Vector<Double>> getVectorScoreMap(File[] contextDictFiles) {
		HashMap<String, Vector<Double>> temp = new HashMap<String, Vector<Double>>();
		for (File f : contextDictFiles) {
			try {
				BufferedReader reader = new BufferedReader(new FileReader(f));
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

	private double calculateScore(Vector<Double> mainVector, Vector<Double> contextVector) {
		double score = 0.0;
		double sum = mainVector.size() + contextVector.size();
		for (int i = 0; i < Math.max(mainVector.size(), contextVector.size()); i++) {
			if (i < mainVector.size()) {
				score += mainVector.get(i);
			}
			if (i < contextVector.size()) {
				score += contextVector.get(i);
			}
		}
		score /= sum;
		return score;
	}

	private double calculateScore(Vector<Double> v) {
		double score = 0.0;
		double sum = v.size();
		for (int i = 0; i < v.size(); i++)
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

	@Override
	public boolean contains(Word w) {
		return words.contains(w);
	}

	@Override
	public boolean contains(POSTaggedWord ptw) {
		POSTaggedWord sentiWord = getSentiTaggedWord(ptw.getWord(), ptw.getPOS());
		return dict.containsKey(sentiWord.toString());
	}
}
