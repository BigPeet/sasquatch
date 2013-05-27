package analyze.polarity.simple;

import java.util.ArrayList;

import systems.source.Source;

public class ScoredSource extends Source {
	
	private Source source;
	private int score;
	
	//maybe use a map...Word -> # of appearance
	private ArrayList<Word> words = new ArrayList<Word>();
	
	public ScoredSource(Source s) {
		this.source = s;
		this.score = 0;
	}
	
	public boolean hasBeenUsed() {
		return words.size() != 0;
	}

	@Override
	public String getText() {
		return source.getText();
	}

	/**
	 * @return the source
	 */
	public Source getSource() {
		return source;
	}
	
	public int getScore() {
		return score;
	}

	/**
	 * @param source the source to set
	 */
	public void setSource(Source source) {
		this.source = source;
	}
	
	public void addWord(Word w) {
		if (!words.contains(w)) {
			words.add(w);
		}
		int n = getNumberOfApperances(getText(), w.getWord());
		score += n * w.getValue();
	}
	
	private int getNumberOfApperances(String text, String sub) {
		int n = 0;
		int index = text.indexOf(sub);
		while(index != -1 && (index + 1) < text.length()) {
			n++;
			text = text.substring(index + 1);
			index = text.indexOf(sub);
		}
		return n;
	}
	
	public boolean equals(Object o) {
		boolean equals = false;
		if (o instanceof ScoredSource) {
			ScoredSource ss = (ScoredSource) o;
			equals = source.equals(ss.source);
		} else if (o instanceof Source) {
			equals = source.equals(o);
		}
		return equals;
	}

}
