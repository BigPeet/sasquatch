package analyze.polarity.simple;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class DictionaryParser {
	
	private ArrayList<Word> words = new ArrayList<Word>();
	
	public DictionaryParser(File dict) {
		this.words = extractWords(dict);
	}
	
	private ArrayList<Word> extractWords(File dict) {
		ArrayList<Word> wordList = new ArrayList<Word>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(dict));
			String line = "";
			while ((line = br.readLine()) != null) {
				String[] token = line.split("\\s+", 2);
				String txt = token[0];
				int value = Integer.parseInt(token[1]);
				Word w = new Word(txt, value);
				wordList.add(w);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException ie) {
			ie.printStackTrace();
		}
		return wordList;
	}
	
	public Word[] getWords() {
		return words.toArray(new Word[words.size()]);
	}
	
}
