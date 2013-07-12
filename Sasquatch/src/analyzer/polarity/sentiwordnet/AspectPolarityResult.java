package analyzer.polarity.sentiwordnet;

import java.util.ArrayList;

import manager.systems.source.Source;
import analyzer.dictionary.Word;
import analyzer.polarity.PolarityResult;
import analyzer.polarity.simple.ScoredSource;

public class AspectPolarityResult extends PolarityResult {

	private ArrayList<Aspect> aspects = new ArrayList<Aspect>();

	public AspectPolarityResult() {

	}
	
	public AspectPolarityResult(int positive, int negative, int neutral, int notUsed, Aspect[] aspects) {
		setPositive(positive);
		setNegative(negative);
		setNeutral(neutral);
		setNotUsed(notUsed);
		for (Aspect a : aspects) {
			Aspect tmp = new Aspect(a.getName(), a.getSynonyms());
			this.aspects.add(tmp);
		}
	}

	public AspectPolarityResult(Aspect[] aspects) {
		for (Aspect a : aspects) {
			Aspect tmp = new Aspect(a.getName(), a.getSynonyms());
			this.aspects.add(tmp);
		}
	}

	@Override
	public void addSource(Source s, Word[] words) {
//		for (Word w : words) {
//			System.out.printf("(%s : %.3f)\n", w.getWord(), w.getValue()); 
//		}
		ScoredSource ss = new ScoredSource(s);
		ss.addWords(words);
		getScoredSources().add(ss);
//		System.out.println("TOTAL: " + ss.getScore());
//		System.out.println();
	}

	public void addAspect(Aspect a) {
		if (!aspects.contains(a)) {
			Aspect tmp = new Aspect(a.getName(), a.getSynonyms());
			aspects.add(tmp);
		} else {
			int index = aspects.indexOf(a);
			Aspect tmp = aspects.get(index);
			tmp.setPositive(tmp.getPositive() + a.getPositive());
			tmp.setNegative(tmp.getNegative() + a.getNegative());
		}
	}

	@Override
	public void show() {
		System.out.println("Positive: " + getPositive());
		System.out.println("Negative: " + getNegative());
		System.out.println("Neutral: " + getNeutral());
		System.out.println("Not Used: " + getNotUsed());
		System.out.println("Aspects:");
		for (Aspect a : aspects) {
			System.out.println(a);
			for (Word w : a.getWords()) {
				System.out.println("\t" + w);
			}
		}
	}

	/**
	 * @return the aspects
	 */
	public Aspect[] getAspects() {
		return aspects.toArray(new Aspect[aspects.size()]);
	}

}
