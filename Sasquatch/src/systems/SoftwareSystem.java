package systems;

import systems.source.SourceHandler;
import systems.source.Source;


public class SoftwareSystem {

	private Source[] sources;
	
	public SoftwareSystem(SourceHandler handler) {
		sources = handler.getSources();
	}
	
	public Source[] getSources() {
		return sources;
	}

}
