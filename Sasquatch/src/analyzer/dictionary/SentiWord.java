package analyzer.dictionary;

public class SentiWord extends Word {

	private char POS;
	private int id;
	private int index;
	private double positive;
	private double negative;
	
	public SentiWord(String word) {
		super(word);
	}

}
