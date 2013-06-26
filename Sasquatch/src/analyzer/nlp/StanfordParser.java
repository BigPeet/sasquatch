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

	public StanfordParser(IDictionary dictionary) {
		this.dictionary = dictionary;
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

	public POSTaggedWord[] getGeneralTaggedWords(String[] sentences) {
		ArrayList<POSTaggedWord> words = new ArrayList<POSTaggedWord>();
		for (String s : sentences) {
			Tokenizer<? extends HasWord> toke = tlp.getTokenizerFactory().getTokenizer(new StringReader(s));
			List<? extends HasWord> sentence = toke.tokenize();
			words.addAll(getGeneralTaggedWords(sentence));
		}
		return words.toArray(new POSTaggedWord[words.size()]);
	}
	
	public POSTaggedWord[] getSpecialTaggedWords(String[] sentences, String[] synonyms) {
		return null;
	}
	
	private ArrayList<POSTaggedWord> getGeneralTaggedWords(List<? extends HasWord> sentence) {
		ArrayList<POSTaggedWord> words = new ArrayList<POSTaggedWord>();
		Tree parse = lp.parse(sentence);
		GrammaticalStructure gs = gsf.newGrammaticalStructure(parse);
		List<TypedDependency> tdl = gs.typedDependenciesCCprocessed();
		for (TaggedWord tw : parse.taggedYield()) {
			String pos = tw.tag();
			String text = tw.value();
			POSTaggedWord ptw = new POSTaggedWord(text, pos);
			if (!isPunctuation(text)) {
				double score = dictionary.getScore(ptw);
				ptw.setValue(score);
			}
			words.add(ptw);
		}
		for (TypedDependency td : tdl) {
			if (isNegation(td.reln().getShortName())) {
				int index = td.gov().index() - 1;
				POSTaggedWord ptw = words.get(index);
				ptw.setValue(-ptw.getValue());
			}
		}
		words = removePunctuation(words);
		return words;
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

	private static boolean isPunctuation(String s) {
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
