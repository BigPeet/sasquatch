package analyzer.dictionary;

import java.io.IOException;

import qtag.Tagger;

public class POSTagger {

	private Tagger tagger;
	
	public POSTagger() {
		try {
			this.tagger = new Tagger();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	public TaggedWord[] tagWords(Word[] words) {
		TaggedWord[] tagged = new TaggedWord[words.length];
		String[] texts = new String[words.length];
		for (int i = 0; i < words.length; i++) {
			texts[i] = words[i].getWord();
		}
		String[] tags = this.tagger.tag(texts);
		for (int i = 0; i < words.length; i++) {
			tagged[i] = new TaggedWord(texts[i], tags[i]);
		}
		
		return tagged;
	}
	
	
	public static void main(String[] args) {
		String s = "These are excellent texts";
		
		POSTagger t = new POSTagger();
		try {
			t.tagger = new Tagger();
			String[] data = s.split("\\s+");
			String[] tags = t.tagger.tag(data);
			String[] tokens = t.tagger.getTokenArray();
			
			for (String tag : tags) {
				System.out.println("Tag: " + tag);
			}
			for (String token : tokens) {
				System.out.println("Token: " + token);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
