package analyzer.dictionary;

import java.io.File;

public interface IDictionary {

	public void extractWords(File dict);
	public double getScore(Word w);
}
