package analyzer.polarity;

import java.util.ArrayList;

import manager.systems.source.Source;

import analyzer.AnalysisResult;
import analyzer.dictionary.Word;
import analyzer.polarity.simple.ScoredSource;

public abstract class PolarityResult extends AnalysisResult {

	private ArrayList<ScoredSource> scoredSources = new ArrayList<ScoredSource>();
	private int positive = 0;
	private int negative = 0;
	private int neutral = 0;
	private int notUsed = 0;

	public PolarityResult() {

	}

	public void updateScore() {
		setPositive(0);
		setNegative(0);
		setNeutral(0);
		setNotUsed(0);
		for (ScoredSource ss : getScoredSources()) {
			if (ss.hasBeenUsed()) {
				double score = ss.getScore();
				if (score > 0) {
					setPositive(getPositive() + 1);
				} else if (score < 0) {
					setNegative(getNegative() + 1);
				} else {
					setNeutral(getNeutral() + 1);
				}
			}
			else {
				setNotUsed(getNotUsed() + 1);
			}
		}
	}

	public abstract void addSource(Source s, Word[] words);

	/**
	 * @return the scoredSources
	 */
	public ScoredSource[] getResults() {
		return scoredSources.toArray(new ScoredSource[scoredSources.size()]);
	}
	/**
	 * @return the positive
	 */
	public int getPositive() {
		return positive;
	}
	/**
	 * @return the negative
	 */
	public int getNegative() {
		return negative;
	}
	/**
	 * @return the neutral
	 */
	public int getNeutral() {
		return neutral;
	}
	/**
	 * @return the notUsed
	 */
	public int getNotUsed() {
		return notUsed;
	}
	/**
	 * @param positive the positive to set
	 */
	public void setPositive(int positive) {
		this.positive = positive;
	}
	/**
	 * @param negative the negative to set
	 */
	public void setNegative(int negative) {
		this.negative = negative;
	}
	/**
	 * @param neutral the neutral to set
	 */
	public void setNeutral(int neutral) {
		this.neutral = neutral;
	}
	/**
	 * @param notUsed the notUsed to set
	 */
	public void setNotUsed(int notUsed) {
		this.notUsed = notUsed;
	}

	/**
	 * @return the scoredSources
	 */
	protected ArrayList<ScoredSource> getScoredSources() {
		return scoredSources;
	}

}
