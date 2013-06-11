package manager.systems;

import manager.systems.source.SourceHandler;
import manager.systems.source.Source;


public class SoftwareSystem {

	private Source[] sources;
	
	public SoftwareSystem(SourceHandler handler) {
		sources = handler.getSources();
	}
	
	public Source[] getSources() {
		return sources;
	}

}
