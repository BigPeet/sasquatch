package analyze.polarity.simple;

public class Word implements Comparable {

	private String word;
	private int value;
	private boolean isPositive;
	private boolean isNegative;
	
	public Word(String word, int value) {
		this.word = word;
		this.value = value;
		isPositive = value > 0;
		isNegative = value < 0;
	}
	
	public boolean isPositive() {
		return isPositive;
	}
	
	public boolean isNegative() {
		return isNegative;
	}
	
	public boolean isNeutral() {
		return !isPositive && !isNegative;
	}
	
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
		isPositive = value > 0;
		isNegative = value < 0;
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
	
}
