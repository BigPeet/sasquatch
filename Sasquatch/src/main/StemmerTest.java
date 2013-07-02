package main;

import org.tartarus.snowball.SnowballStemmer;

public class StemmerTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String text = "effective";
		
		SnowballStemmer stemmer = getStemmer();
		stemmer.setCurrent(text);
		stemmer.stem();
		String ret = stemmer.getCurrent(); 
		System.out.println(text + " -> " + ret);

	}

	
	private static SnowballStemmer getStemmer() {
		SnowballStemmer stemmer = null;
		Class<? extends SnowballStemmer> stemClass = null;
		try {
			stemClass = (Class<? extends SnowballStemmer>) Class.forName("org.tartarus.snowball.ext." + "english" + "Stemmer");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		if (stemClass != null) {
			try {
				stemmer = (SnowballStemmer) stemClass.newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return stemmer;
	}
}
