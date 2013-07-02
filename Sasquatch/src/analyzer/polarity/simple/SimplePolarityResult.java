package analyzer.polarity.simple;

import manager.systems.source.Source;
import analyzer.dictionary.Word;
import analyzer.polarity.PolarityResult;

public class SimplePolarityResult extends PolarityResult {

	public SimplePolarityResult() {

	}

	public SimplePolarityResult(Source[] sources) {
		for (Source s : sources) {
			getScoredSources().add(new ScoredSource(s));
		}
	}

	public SimplePolarityResult(Source s) {
		getScoredSources().add(new ScoredSource(s));
	}

	@Override
	public void addSource(Source s, Word[] words) {
		ScoredSource ss = new ScoredSource(s);
		getScoredSources().add(ss);
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

	private ScoredSource getCorrespondingScoredSource(Source s) {
		ScoredSource ret = null;
		for (ScoredSource ss : getScoredSources()) {
			if (ss.getSource().equals(s)) {
				ret = ss;
				break;
			}
		}
		return ret;
	}

	@Override
	public void show() {
		System.out.println("Positive: " + getPositive());
		System.out.println("Negative: " + getNegative());
		System.out.println("Neutral: " + getNeutral());
		System.out.println("Not Used: " + getNotUsed());
	}


}
