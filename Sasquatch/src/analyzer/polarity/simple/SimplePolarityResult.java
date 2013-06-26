package analyzer.polarity.simple;

import java.util.ArrayList;

import manager.systems.source.Source;
import analyzer.dictionary.Word;
import analyzer.polarity.PolarityResult;

public class SimplePolarityResult extends PolarityResult {

	private ArrayList<ScoredSource> scoredSources = new ArrayList<ScoredSource>();
	private int positive = 0;
	private int negative = 0;
	private int neutral = 0;
	private int notUsed = 0;

	public SimplePolarityResult() {

	}

	public SimplePolarityResult(Source[] sources) {
		for (Source s : sources) {
			scoredSources.add(new ScoredSource(s));
		}
	}

	public SimplePolarityResult(Source s) {
		scoredSources.add(new ScoredSource(s));
	}

	public ScoredSource[] getResults() {
		return scoredSources.toArray(new ScoredSource[scoredSources.size()]);
	}

	public void addSource(Source s, Word[] words) {
		ScoredSource ss = new ScoredSource(s);
		scoredSources.add(ss);
		ss.addWords(words);
	}

	public void addQueryResult(Word w, Source[] results) {
		for (Source s : results) {
			//get corresponding scoredSource
			ScoredSource ss = getCorrespondingScoredSource(s);

			//addWord to this ss
			if (ss != null) {
				ss.addWord(w);
				//change pos or negative
				updateScore();
			}
		}
	}

	public void updateScore() {
		positive = 0;
		negative = 0;
		neutral = 0;
		notUsed = 0;
		for (ScoredSource ss : scoredSources) {
			if (ss.hasBeenUsed()) {
				double score = ss.getScore();
				if (score > 0) {
					positive++;
				} else if (score < 0) {
					negative++;
				} else {
					neutral++;
				}
			}
			else {
				notUsed++;
			}
		}
	}

	private ScoredSource getCorrespondingScoredSource(Source s) {
		ScoredSource ret = null;
		for (ScoredSource ss : scoredSources) {
			if (ss.getSource().equals(s)) {
				ret = ss;
				break;
			}
		}
		return ret;
	}

	@Override
	public void show() {
		System.out.println("Positive: " + positive);
		System.out.println("Negative: " + negative);
		System.out.println("Neutral: " + neutral);
		System.out.println("Not Used: " + notUsed);
	}


}
