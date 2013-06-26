package analyzer.dictionary;

public class POSTaggedWord extends Word {

	private String POS;
	
	public POSTaggedWord(String word, double value, String pos) {
		super(word, value);
		this.POS = pos;
	}
	
	public POSTaggedWord(String word, String pos) {
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
		if (o instanceof POSTaggedWord) {
			equals = (POS == ((POSTaggedWord) o).POS) && super.equals(o);
		} 
		return equals;
	}

}
