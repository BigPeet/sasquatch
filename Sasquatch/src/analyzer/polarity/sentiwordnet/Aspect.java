package analyzer.polarity.sentiwordnet;

import java.util.ArrayList;

import org.tartarus.snowball.SnowballStemmer;

import analyzer.dictionary.Word;

public class Aspect {

	private String name;
	private ArrayList<String> synonyms = new ArrayList<String>();
	private ArrayList<Word> words = new ArrayList<Word>();
	private int positive = 0;
	private int negative = 0;
	
	public Aspect(String name) {
		this.name = name;
	}
	
	public Aspect(String name, String[] synonyms) {
		this.name = name;
		for (String synonym : synonyms) {
			this.synonyms.add(synonym.toLowerCase());
		}
	}
	
	public boolean contains(String word) {
		return name.equals(word) || synonyms.contains(word.toLowerCase());
	}
	
	@Override
	public boolean equals(Object o) {
		boolean equals = false;
		if (o instanceof Aspect) {
			equals = name.equals(((Aspect)o).name);
		}
		return equals;
	}
	
	public String toString() {
		return name + ": +" + positive + ", -" + negative; 
	}
	
	public void count(Word w) {
		if (w.isNegative()) {
			incrementNegative();
			words.add(w);
		} else if (w.isPositive()) {
			incrementPositive();
			words.add(w);
		}
	}
	
	public void incrementPositive() {
		positive++;
	}
	
	public void incrementNegative() {
		negative++;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the synonyms
	 */
	public String[] getSynonyms() {
		return synonyms.toArray(new String[synonyms.size()]);
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param synonyms the synonyms to set
	 */
	public void setSynonyms(String[] synonyms) {
		this.synonyms.clear();
		for (String syn : synonyms) {
			this.synonyms.add(syn.toLowerCase());
		}
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
	 * @return the words
	 */
	public Word[] getWords() {
		return words.toArray(new Word[words.size()]);
	}
}
