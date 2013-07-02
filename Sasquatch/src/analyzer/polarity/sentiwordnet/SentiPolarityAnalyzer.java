package analyzer.polarity.sentiwordnet;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.tartarus.snowball.SnowballStemmer;

import edu.stanford.nlp.trees.TypedDependency;

import manager.systems.SoftwareSystem;
import manager.systems.source.Source;
import analyzer.dictionary.AspectParser;
import analyzer.dictionary.IDictionary;
import analyzer.dictionary.SentiWordNetDictionary;
import analyzer.dictionary.POSTaggedWord;
import analyzer.dictionary.Word;
import analyzer.interfaces.IAnalysisResult;
import analyzer.nlp.StanfordParser;
import analyzer.nlp.StanfordSentence;
import analyzer.polarity.PolarityAnalyzer;

public class SentiPolarityAnalyzer extends PolarityAnalyzer {
	
	private static int time = 3;
	private static int MAX_SOURCES = 500;
	private static String[] relevantRel = {"pobj", "dobj", "nsubj", "nsubjpass", "amod"};
	private static final File mainDictFile = new File("res/dict/SentiWordNet.txt");
	private static final File contextDictFile = new File("res/dict/SQ.txt");
	private static final String aspectURL = "res/dict/aspects/";
	private static final File[] contextDictFiles = {contextDictFile};
	
	
	private IDictionary dict;
	private Date limit;
	private Aspect[] aspects = null;

	public SentiPolarityAnalyzer() {
		dict = new SentiWordNetDictionary(mainDictFile, contextDictFiles);
		aspects = initAspects();
		Calendar now = Calendar.getInstance();
		now.add(Calendar.YEAR, -time);
		limit = now.getTime();
	}

	private static Aspect[] initAspects() {
		File documentation = new File(aspectURL + "Documentation" + ".txt");
		File lightweight = new File(aspectURL + "Leightweight" + ".txt");
		File interFace = new File(aspectURL + "Interface" + ".txt");
		File usability = new File(aspectURL + "Usability" + ".txt");
		File flexibility = new File(aspectURL + "Flexibility" + ".txt");
		File reliability = new File(aspectURL + "Reliability" + ".txt");
		File effectiveness = new File(aspectURL + "Effectiveness" + ".txt");
		File[] sourceFiles = {documentation, lightweight, interFace, usability, flexibility, reliability, effectiveness};
		AspectParser aspectParser = new AspectParser(sourceFiles);
		aspectParser.extractAspects();
		return aspectParser.getAspects();
	}

	@Override
	public IAnalysisResult analyze(SoftwareSystem ss) {
		AspectPolarityResult res = new AspectPolarityResult(aspects);
		StanfordParser parser = new StanfordParser();
		int counter = 0;
		for (Source s : ss.getSources()) {
			if (counter > MAX_SOURCES - 1) {
				break;
			}			
			ArrayList<POSTaggedWord> taggedWords = new ArrayList<POSTaggedWord>();
//			ArrayList<Word> aspectWords = new ArrayList<Word>();
			if (s.getDate() != null && s.getDate().after(limit)) {
				String text = s.getText();
				String[] sentences = StanfordParser.getSentences(text);
				
				StanfordSentence[] stanfordSentences = parser.parseSentences(sentences);
				for (StanfordSentence sentence : stanfordSentences) {
					setGeneralScore(sentence);
					setAspectScore(sentence, res.getAspects());
//					aspectWords.addAll(aspectWordList);
					taggedWords.addAll(removePunctuation(sentence.getWords()));
				}
				
//				POSTaggedWord[] taggedWords = parser.getTaggedWords(sentences);
//				POSTaggedWord[] specialWords = parser.getSpecialTaggedWords(sentences);
//				for (POSTaggedWord ptw : specialWords) {
//					System.out.println(ptw);
//				}
				counter++;
//				res.addAspectWords(aspectWords.toArray(new Word[aspectWords.size()]));
				res.addSource(s, taggedWords.toArray(new POSTaggedWord[taggedWords.size()]));
			} else {
				Word[] words = new Word[0];
				res.addSource(s, words);
			}
		}
		res.updateScore();
		return res;
	}

	private ArrayList<POSTaggedWord> removePunctuation(ArrayList<POSTaggedWord> words) {
		ArrayList<POSTaggedWord> tmp = new ArrayList<POSTaggedWord>();
		for (POSTaggedWord ptw : words) {
			if (!StanfordParser.isPunctuation(ptw.getWord())) {
				tmp.add(ptw);
			}
		}
		return tmp;
	}
	
	private ArrayList<Word> getAspectWords(StanfordSentence sentence) {
		ArrayList<Word> aspectWords = new ArrayList<Word>();
		for (TypedDependency td : sentence.getTdl()) {
			if (isRelevant(td)) {
				int govIndex = td.gov().index() - 1;
				int depIndex = td.dep().index() - 1;
				Word dep = sentence.getWords().get(depIndex);
				Word gov = sentence.getWords().get(govIndex);
				Word tmp = null;
				if (dep.getValue() != 0) {
					tmp = new Word(gov.getWord(), dep.getValue());
				} else if (gov.getValue() != 0) {
					tmp = new Word(dep.getWord(), gov.getValue());
				}
				if (tmp != null) {
					aspectWords.add(tmp);
				}
			}
		}
		return aspectWords;
	}
	
	private void setGeneralScore(StanfordSentence sentence) {
		for (POSTaggedWord ptw : sentence.getWords()) {
			ptw.setValue(getScoreForWord(ptw));
		}
		for (TypedDependency td : sentence.getTdl()) {
			handleAdvMod(td, sentence.getWords());
		}
		handleNegations(sentence.getTdl(), sentence.getWords());
	}

	private void setAspectScore(StanfordSentence sentence, Aspect[] aspects) {
		for (TypedDependency td : sentence.getTdl()) {
			if (isRelevant(td)) {
				int govIndex = td.gov().index() - 1;
				int depIndex = td.dep().index() - 1;
				Word dep = sentence.getWords().get(depIndex);
				Word gov = sentence.getWords().get(govIndex);
				for (Aspect a : aspects) {
					Word tmp = getWord(a, dep, gov);
					if (tmp != null) {
						a.count(tmp);
					}
				}
			}
		}
	}
	
	private Word getWord(Aspect a, Word dep, Word gov) {
		Word tmp = null;
		if (a.contains(gov.getWord()) || a.contains(getStemmedWord(gov).getWord())) {
			tmp = new Word(gov.getWord(), gov.getValue());
		} else if (a.contains(dep.getWord()) || a.contains(getStemmedWord(dep).getWord())) {
			tmp = new Word(dep.getWord(), gov.getValue());
		}
		return tmp;
	}

	private boolean isRelevant(TypedDependency td) {
		String det = td.reln().getShortName();
		boolean equal = false;
		for (int i = 0; i < relevantRel.length && !equal; i++) {
			equal = det.equals(relevantRel[i]);
		}
		return equal;
	}
	
	private void handleAdvMod(TypedDependency td, ArrayList<POSTaggedWord> words) {
		if (isAdvMod(td.reln().getShortName())) {
			int govIndex = td.gov().index() - 1;
			int depIndex = td.dep().index() - 1;
			POSTaggedWord gov = words.get(govIndex);
			POSTaggedWord dep = words.get(depIndex);
			gov.setValue(gov.getValue() + dep.getValue());
			dep.setValue(0);
		}
	}
	
	private double getScoreForWord(POSTaggedWord ptw) {
		double score = 0.0;
		if (dict.contains(ptw)) {
			score = dict.getScore(ptw);
		} else {
			POSTaggedWord tmp = getStemmedWord(ptw);
			if (dict.contains(tmp)) {
				score = dict.getScore(tmp);
			}
		}
		return score;
	}

	private void handleNegations(List<TypedDependency> tdl, ArrayList<POSTaggedWord> words) {
		for (TypedDependency td : tdl) {
			if (isNegation(td.reln().getShortName())) {
				int index = td.gov().index() - 1;
				POSTaggedWord ptw = words.get(index);
				ptw.setValue(-ptw.getValue());
				index = td.dep().index() - 1;
				ptw = words.get(index);
				ptw.setValue(0);
			}
		}
	}
	
	private boolean isNegation(String shortName) {
		return shortName.equals("neg");
	}

	private boolean isAdvMod(String shortName) {
		return shortName.equals("advmod");
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
	
	private POSTaggedWord getStemmedWord(POSTaggedWord sentiWord) {
		String text = sentiWord.getWord();
		//	System.out.print(text + " -> ");
		SnowballStemmer stemmer = getStemmer();
		if (stemmer != null) {
			stemmer.setCurrent(text);
			stemmer.stem();
			text = stemmer.getCurrent(); 
		}
		//	System.out.println(text);
		sentiWord.setWord(text);
		return sentiWord;
	}
	
	private Word getStemmedWord(Word sentiWord) {
		String text = sentiWord.getWord();
		//	System.out.print(text + " -> ");
		SnowballStemmer stemmer = getStemmer();
		if (stemmer != null) {
			stemmer.setCurrent(text);
			stemmer.stem();
			text = stemmer.getCurrent(); 
		}
		//	System.out.println(text);
		sentiWord.setWord(text);
		return sentiWord;
	}

	private String replacePunctuation(String text) {
		//ReplaceAll non-char, non-numbers?
		text = text.replace(".", " ");
		text = text.replace("!", " ");
		text = text.replace("?", " ");
		text = text.replace(",", " ");
		text = text.replace(":", " ");
		text = text.replace(";", " ");
		text = text.replace("(", " ");
		text = text.replace(")", " ");
		text = text.replace("[", " ");
		text = text.replace("]", " ");
		text = text.replace("}", " ");
		text = text.replace("{", " ");
		return text.trim();
	}

}
