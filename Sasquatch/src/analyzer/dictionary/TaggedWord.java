package analyzer.dictionary;

public class TaggedWord extends Word {

	private String POS;
	
	public TaggedWord(String word, double value, String pos) {
		super(word, value);
		this.POS = pos;
	}
	
	public TaggedWord(String word, String pos) {
		super(word);
		this.POS = pos;
	}

	/**
	 * @return the pOS
	 */
	public String getPOS() {
		return POS;
	}

	/**
	 * @param pos the pos to set
	 */
	public void setPOS(String pos) {
		POS = pos;
	}
	
	public String toString() {
		return getWord() + "#" + POS;
	}
	
	@Override
	public boolean equals(Object o) {
		boolean equals = false;
		if (o instanceof TaggedWord) {
			equals = (POS == ((TaggedWord) o).POS) && super.equals(o);
		} 
		return equals;
	}

}
