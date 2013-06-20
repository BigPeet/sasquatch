package analyzer.dictionary;

public class Word implements Comparable {

	//CUT THIS OUT!
	private String word;
	private int value;
	
	public Word(String word) {
		this.word = word;
		this.value = 0;
	}
	
	public Word(String word, int value) {
		this.word = word;
		this.value = value;
	}

	public boolean isPositive() {
		return value > 0;
	}
	
	public boolean isNegative() {
		return value < 0;
	}
	
	public boolean isNeutral() {
		return !isPositive() && !isNegative();
	}
	
	@Override
	public boolean equals(Object  o) {
		boolean equals = false;
		if (o instanceof Word) {
			Word w = (Word) o;
			equals = this.word.equals(w.word);
		}
		return equals;
	}

	/**
	 * @return the word
	 */
	public String getWord() {
		return word;
	}

	/**
	 * @return the value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * @param word the word to set
	 */
	public void setWord(String word) {
		this.word = word;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(int value) {
		this.value = value;
	}

	@Override
	public int compareTo(Object o) {
		int compare = 0;
		if (o instanceof Word) {
			Word w = (Word) o;
			compare = word.compareTo(w.word);
		}
		return compare;
	}
	
	public String toString() {
		return word + " : " + value;
	}
	
}
