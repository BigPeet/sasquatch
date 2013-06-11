package analyzer;

import analyzer.interfaces.IAnalysisResult;
import analyzer.interfaces.IAnalyzer;
import manager.systems.SoftwareSystem;

public abstract class SentimentAnalyzer implements IAnalyzer {
	
	public abstract IAnalysisResult analyze(SoftwareSystem ss);

}
