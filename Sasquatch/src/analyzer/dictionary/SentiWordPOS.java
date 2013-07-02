package analyzer.dictionary;

public class SentiWordPOS {

	public static final SentiWordPOS ADJECTIVE = new SentiWordPOS("a");
	public static final SentiWordPOS NOUN = new SentiWordPOS("n");
	public static final SentiWordPOS VERB = new SentiWordPOS("v");
	public static final SentiWordPOS ADVERB = new SentiWordPOS("r");
	public static final SentiWordPOS UNDEF = new SentiWordPOS("#");
	
	public static final SentiWordPOS[] TAGS = {ADJECTIVE, NOUN, VERB, ADVERB};
	
	
	private String tag;
	
	private SentiWordPOS(String tag) {
		this.tag = tag;
	}
	
	public String getTag() {
		return tag;
	}
	
	public static SentiWordPOS getSentiWordPOS(String tag) {
		SentiWordPOS ret = null;
		tag = convertToSentiWordTag(tag);
		switch(tag) {
		case "a": ret = ADJECTIVE; break;
		case "n": ret = NOUN; break;
		case "v": ret = VERB; break;
		case "r": ret = ADVERB; break;
		default: ret = UNDEF;
		}
		return ret;
	}
	
	public static String convertToSentiWordTag(String qtag) {
		String tag = "";
		switch(qtag) {
		case "v":
		case "BE":
		case "BEDR":
		case "BEG":
		case "BEM":
		case "BEN":
		case "BER":
		case "BEZ":
		case "D O":
		case "D O D":
		case "DOG":
		case "DON":
		case "D O Z":
		case "HV":
		case "HVD":
		case "HVG":
		case "HVN":
		case "HVZ":
		case "M D":
		case "VB":
		case "VBD":
		case "VBG":
		case "VBN":
		case "VBZ": tag = SentiWordPOS.VERB.getTag(); break;
		case "a":
		case "JJ":
		case "JJR":
		case "JJS": tag = SentiWordPOS.ADJECTIVE.getTag(); break;
		case "n":
		case "NN":
		case "NNP":
		case "NNS":
		case "NP":
		case "NPS": tag = SentiWordPOS.NOUN.getTag(); break;
		case "r":
		case "RB":
		case "RBS":
		case "RBR":
		case "RP": tag = SentiWordPOS.ADVERB.getTag(); break;
		default: tag = SentiWordPOS.UNDEF.getTag();
		}
		return tag;
	}
}
