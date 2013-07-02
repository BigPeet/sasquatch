package analyzer.dictionary;

import java.io.File;
import java.util.ArrayList;

public interface IDictionary {

	public void extractWords(File dict);
	public Word[] getWords();
	public double getScore(Word w);
	public double getScore(POSTaggedWord word);
	public boolean contains(Word w);
	public boolean contains(POSTaggedWord ptw);
}
