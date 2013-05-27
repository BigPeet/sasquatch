package analyze.polarity.simple;

import java.util.ArrayList;

import systems.source.Source;
import analyze.polarity.PolarityResult;

public class SimpleResult extends PolarityResult {
	
	private ArrayList<ScoredSource> scoredSources = new ArrayList<ScoredSource>();
	private int positive = 0;
	private int negative = 0;
	private int neutral = 0;
	
	public SimpleResult(Source[] sources) {
		for (Source s : sources) {
			scoredSources.add(new ScoredSource(s));
		}
	}
	
	public void addQueryResult(Word w, Source[] results) {
		for (Source s : results) {
			//get corresponding scoredSource
			ScoredSource ss = getCorrespondingScoredSource(s);

			//addWord to this ss
			if (ss != null) {
				ss.addWord(w);
			}
			
			//change pos or negative
			updateScore();
		}
	}

	private void updateScore() {
		positive = 0;
		negative = 0;
		neutral = 0;
		for (ScoredSource ss : scoredSources) {
			int score = ss.getScore();
			if (score > 0) {
				positive++;
			} else if (score < 0) {
				negative++;
			} else {
				neutral++;
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
	}
	
	
}
