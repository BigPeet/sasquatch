package analyze.polarity.simple;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Dictionary {
	
	private ArrayList<Word> words = new ArrayList<Word>();
	
	public Dictionary(File dict) {
		this.words = extractWords(dict);
	}
	
	private ArrayList<Word> extractWords(File dict) {
		ArrayList<Word> wordList = new ArrayList<Word>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(dict));
			String line = "";
			while ((line = br.readLine()) != null) {
				String[] token = line.split("\\s+", 2);
				String[] texts = parseText(token[0]);
				double dValue = Double.parseDouble(token[1]);
				int value = (int) Math.round(dValue);//Integer.parseInt(token[1]);
				for (String txt : texts) {
					Word w = new Word(txt, value);
					wordList.add(w);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException ie) {
			ie.printStackTrace();
		}
		return wordList;
	}
	
	public void printDict() {
		for (Word word : words) {
			System.out.println(word);
		}
	}
	
	//TODO: needs improvement
	private String[] parseText(String text) {
		ArrayList<String> texts = new ArrayList<String>();
		//in case there are multiple possiblities (e.g. ['d|would] or _(WORD)_ or '#')
		if (text.contains("(") || text.contains("#")) {
			return new String[0];
		}
		
		if (text.contains("[") && text.contains("]")) {
			int begin = text.indexOf("[");
			int end = text.indexOf("]");
			if (begin < end) {
				String sub = text.substring(begin + 1, end);	
				String[] token = sub.split("\\|");
				for (String s : token) {
					String tmp = text.replace("[" + sub + "]", s);
					texts.add(tmp);
				}
			}
		} else {
			texts.add(text);
		}
		//make words bound by '_' into multipe words
		ArrayList<String> tmp = new ArrayList<String>();
		for (String s : texts) {
			tmp.add(s.replace("_", " "));
		}
		texts = tmp;
		return texts.toArray(new String[texts.size()]);
	}

	public Word[] getWords() {
		return words.toArray(new Word[words.size()]);
	}
	
}
