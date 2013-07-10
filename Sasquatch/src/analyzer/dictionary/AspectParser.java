package analyzer.dictionary;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import analyzer.polarity.sentiwordnet.Aspect;

public class AspectParser {

	private File[] sourceFiles;
	private Aspect[] aspects;
	
	public AspectParser(File[] sourceFiles) {
		this.sourceFiles = sourceFiles;
	}
	
	public void extractAspects() {
		aspects = new Aspect[sourceFiles.length];
		for (int i = 0; i < sourceFiles.length; i++) {
			String[] words = getWords(sourceFiles[i]);
			String name = sourceFiles[i].getName();
			int index = name.indexOf(".");
			if (index != -1) {
				name = name.substring(0, index);
			}
			aspects[i] = new Aspect(name, words);
		}
	}
	
	private static String[] getWords(File file) {
		ArrayList<String> words = new ArrayList<String>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = "";
			while ((line = reader.readLine()) != null) {
				if (!line.isEmpty() && !isComment(line)) {
					words.add(line);
				}
			}
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return words.toArray(new String[words.size()]);
	}
	
	private static boolean isComment(String line) {
		return line.startsWith("#");
	}

	public Aspect[] getAspects() {
		return aspects;
	}
}
