package analyzer.nlp;

import java.util.ArrayList;
import java.util.List;

import analyzer.dictionary.POSTaggedWord;
import edu.stanford.nlp.trees.TypedDependency;

public class StanfordSentence {

	private ArrayList<POSTaggedWord> words;
	private List<TypedDependency> tdl;
	
	public StanfordSentence(ArrayList<POSTaggedWord> words, List<TypedDependency> tdl) {
		this.words = words;
		this.tdl = tdl;
	}
	
	/**
	 * @return the words
	 */
	public ArrayList<POSTaggedWord> getWords() {
		return words;
	}
	/**
	 * @return the tdl
	 */
	public List<TypedDependency> getTdl() {
		return tdl;
	}
	
}
