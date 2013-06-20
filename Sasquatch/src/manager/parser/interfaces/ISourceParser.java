package manager.parser.interfaces;

import manager.systems.source.Source;

public interface ISourceParser {
	
	public Source parseSource(String text);
	public void saveSource(Source source);
}
