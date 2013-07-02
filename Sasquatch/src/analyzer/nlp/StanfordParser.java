package analyzer.nlp;

import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import analyzer.dictionary.IDictionary;
import analyzer.dictionary.POSTaggedWord;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.process.DocumentPreprocessor;
import edu.stanford.nlp.process.Tokenizer;
import edu.stanford.nlp.trees.GrammaticalStructure;
import edu.stanford.nlp.trees.GrammaticalStructureFactory;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreebankLanguagePack;
import edu.stanford.nlp.trees.TypedDependency;

public class StanfordParser {

	private static String grammar = "edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz";
	private static String[] options = { "-maxLength", "80", "-retainTmpSubcategories" };
	private static LexicalizedParser lp = LexicalizedParser.loadModel(grammar, options);
	private static TreebankLanguagePack tlp = lp.getOp().langpack();
	private static GrammaticalStructureFactory gsf = tlp.grammaticalStructureFactory();
	private static String[] punctuation = {".", ",", "?", "!", ";" , ":", "(", ")", "{", "}", "[", "]"};

	private IDictionary dictionary;
	private static String[] relevantRel = {"pobj", "dobj", "nsubj", "nsubjpass", "amod"};

	public StanfordParser(IDictionary dictionary) {
		this.dictionary = dictionary;
	}

	public StanfordParser() {
	}

	public static String[] getSentences(String text) {
		text = preproccess(text);
		Reader reader = new StringReader(text);
		DocumentPreprocessor dp = new DocumentPreprocessor(reader);
		List<String> sentenceList = new LinkedList<String>();
		Iterator<List<HasWord>> it = dp.iterator();
		while (it.hasNext()) {
			StringBuilder sentenceSb = new StringBuilder();
			List<HasWord> sentence = it.next();
			for (HasWord token : sentence) {
				if (sentenceSb.length() > 0) {
					sentenceSb.append(" ");
				}
				sentenceSb.append(token);
			}
			sentenceList.add(sentenceSb.toString());
		}
		return sentenceList.toArray(new String[sentenceList.size()]);
	}

	private static String preproccess(String text) {
		text = text.replaceAll("(\\d+)\\.", "");
		text = text.replace("...", "");
		text = text.replace("--", "");
		text = text.replace("`", "");
		text = text.replace("'" ,"");
		text = text.replace("/" , "");
		text = text.replace("\\" , "");
		text = text.replace("*" , "");
		text = text.replace("=", "");
		text = text.replaceAll("(\\s+|:)(\\d+)\\s*", "");
		text = text.replaceAll("(_{2,})", "");
		text = text.replaceAll("(http:).*\\s*", "");
		return text;
	}

	private List<? extends HasWord> tokenizeSentence(String sentence) {
		Tokenizer<? extends HasWord> toke = tlp.getTokenizerFactory().getTokenizer(new StringReader(sentence));
		List<? extends HasWord> stanfordSentence = toke.tokenize();
		return stanfordSentence;
	}

	public POSTaggedWord[] getTaggedWords(String[] sentences) {
		ArrayList<POSTaggedWord> words = new ArrayList<POSTaggedWord>();
		for (String s : sentences) {
			List<? extends HasWord> sentence = tokenizeSentence(s);
			words.addAll(getTaggedWords(sentence));
		}
		return words.toArray(new POSTaggedWord[words.size()]);
	}

	public StanfordSentence[] parseSentences(String[] sentences) {
		ArrayList<StanfordSentence> stanfordSentences = new ArrayList<StanfordSentence>();
		for (String s : sentences) {
			List<? extends HasWord> sentence = tokenizeSentence(s);
			stanfordSentences.add(parseSentence(sentence));
		}
		return stanfordSentences.toArray(new StanfordSentence[stanfordSentences.size()]);
	}

	private StanfordSentence parseSentence(List<? extends HasWord> sentence) {
		ArrayList<POSTaggedWord> words = new ArrayList<POSTaggedWord>();
		Tree parse = lp.parse(sentence);
		GrammaticalStructure gs = gsf.newGrammaticalStructure(parse);
		List<TypedDependency> tdl = gs.typedDependenciesCCprocessed();
		for (TaggedWord tw : parse.taggedYield()) {
			POSTaggedWord ptw = new POSTaggedWord(tw.value(), tw.tag());//getScoredWord(tw);
			words.add(ptw);
		}
		return new StanfordSentence(words, tdl);
	}

	public POSTaggedWord[] getSpecialTaggedWords(String[] sentences) {
		ArrayList<POSTaggedWord> words = new ArrayList<POSTaggedWord>();
		for (String s : sentences) {
			List<? extends HasWord> sentence = tokenizeSentence(s);
			words.addAll(getSpecialTaggedWords(sentence));	
		}
		return words.toArray(new POSTaggedWord[words.size()]);
	}

	private ArrayList<POSTaggedWord> getSpecialTaggedWords(List<? extends HasWord> sentence) {
		ArrayList<POSTaggedWord> words = new ArrayList<POSTaggedWord>();
		Tree parse = lp.parse(sentence);
		GrammaticalStructure gs = gsf.newGrammaticalStructure(parse);
		List<TypedDependency> tdl = gs.typedDependenciesCCprocessed();
		ArrayList<TaggedWord> wordList = parse.taggedYield();
		System.out.println(tdl);
		for (TypedDependency td : tdl) {
			if (isRelevant(td)) {
				int govIndex = td.gov().index() - 1;
				int depIndex = td.dep().index() - 1;
				POSTaggedWord dep = getScoredWord(wordList.get(depIndex));
				POSTaggedWord gov = getScoredWord(wordList.get(govIndex));
				if (dep.getValue() != 0) {
					gov.setValue(dep.getValue());
					words.add(gov);
				} else if (gov.getValue() != 0) {
					dep.setValue(gov.getValue());
					words.add(dep);
				}
			}
		}
		return words;
	}

	private POSTaggedWord getScoredWord(TaggedWord tw) {
		String pos = tw.tag();
		String text = tw.value();
		POSTaggedWord ptw = new POSTaggedWord(text, pos);
		double score = dictionary.getScore(ptw);
		ptw.setValue(score);
		return ptw;
	}

	private boolean isRelevant(TypedDependency td) {
		String det = td.reln().getShortName();
		boolean equal = false;
		for (int i = 0; i < relevantRel.length && !equal; i++) {
			equal = det.equals(relevantRel[i]);
		}
		return equal;
	}

	private ArrayList<POSTaggedWord> getTaggedWords(List<? extends HasWord> sentence) {
		ArrayList<POSTaggedWord> words = new ArrayList<POSTaggedWord>();
		Tree parse = lp.parse(sentence);
		GrammaticalStructure gs = gsf.newGrammaticalStructure(parse);
		List<TypedDependency> tdl = gs.typedDependenciesCCprocessed();
		for (TaggedWord tw : parse.taggedYield()) {
			if (!isPunctuation(tw.value())) {
				POSTaggedWord ptw = getScoredWord(tw);
				words.add(ptw);
			}
		}
		//		System.out.println(tdl);
		for (TypedDependency td : tdl) {
			handleNegations(td, words);
		}
		words = removePunctuation(words);
		return words;
	}

	private void handleNegations(TypedDependency td, ArrayList<POSTaggedWord> words) {
		if (isNegation(td.reln().getShortName())) {
			int index = td.gov().index() - 1;
			POSTaggedWord ptw = words.get(index);
			ptw.setValue(-ptw.getValue());
		}
	}

	private ArrayList<POSTaggedWord> removePunctuation(ArrayList<POSTaggedWord> words) {
		ArrayList<POSTaggedWord> tmp = new ArrayList<POSTaggedWord>();
		for (POSTaggedWord ptw : words) {
			if (!isPunctuation(ptw.getWord())) {
				tmp.add(ptw);
			}
		}
		return tmp;
	}

	private boolean isNegation(String shortName) {
		return shortName.equals("neg");
	}

	public static boolean isPunctuation(String s) {
		boolean isPunctuation = false;
		for (String p : punctuation) {
			if (s.equals(p)) {
				isPunctuation = true;
				break;
			}
		}
		return isPunctuation;
	}
}
