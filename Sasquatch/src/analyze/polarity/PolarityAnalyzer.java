package analyze.polarity;

import analyze.SentimentAnalyzer;
import systems.source.Source;

public abstract class PolarityAnalyzer extends SentimentAnalyzer {
	
	public abstract int analyze(Source s);

}
