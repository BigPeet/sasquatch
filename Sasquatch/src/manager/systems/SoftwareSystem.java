package manager.systems;

import java.util.ArrayList;

import analyzer.AnalysisResult;
import analyzer.interfaces.IAnalysisResult;

import manager.systems.source.SourceHandler;
import manager.systems.source.Source;


public class SoftwareSystem {

	private Source[] sources;
	private SourceHandler handler;
	private String name;
	private Archive mainArchive;
	private ArrayList<Archive> archives = new ArrayList<Archive>();
	private ArrayList<IAnalysisResult> results = new ArrayList<IAnalysisResult>();
	
	public SoftwareSystem(String name, Archive archive) {
		this.name = name;
		mainArchive = archive;
		archives.add(archive);
	}
	
	public SoftwareSystem(String name, Archive[] archives) {
		this.name = name;
		if (archives.length > 0) {
			mainArchive = archives[0];
		}
		for (Archive a : archives) {
			this.archives.add(a);
		}
	}
	
	public SoftwareSystem(String name, String localPath) {
		this.name = name;
		mainArchive = new Archive(ArchiveType.LOCAL, localPath);
		this.archives.add(mainArchive);
	}
	
	public SoftwareSystem(String name, String listName, int startYear, int endYear, ArchiveType archiveType) {
		this.name = name;
		mainArchive = new Archive(archiveType, listName);
		mainArchive.setStart(startYear);
		mainArchive.setEnd(endYear);
		archives.add(mainArchive);
	}
	
	public SoftwareSystem(String name, String listName, int startYear, int endYear, int pages, ArchiveType archiveType) {
		this.name = name;
		mainArchive = new Archive(archiveType, listName);
		mainArchive.setStart(startYear);
		mainArchive.setEnd(endYear);
		mainArchive.setPages(pages);
		archives.add(mainArchive);
	}
	
	public SoftwareSystem(String name, SourceHandler handler) {
		this.name = name;
		this.handler = handler;
	}
	
	public SoftwareSystem(String name, Source[] sources) {
		this.name = name;
		this.sources = sources;
	}
	
	public Source[] getSources() {
		if (sources == null && handler == null && mainArchive != null) {
			handler = mainArchive.getSourceHandler();
		}
		if (sources == null && handler != null) {
			sources = handler.getSources();
		} else if (sources == null){
			return new Source[0];
		}
		return sources;
	}

	/**
	 * @return the handler
	 */
	public SourceHandler getHandler() {
		return handler;
	}

	public void clear() {
		this.handler = null;
		this.sources = null;
	}
	
	/**
	 * @param sources the sources to set
	 */
	public void setSources(Source[] sources) {
		this.sources = sources;
	}

	/**
	 * @param handler the handler to set
	 */
	public void setHandler(SourceHandler handler) {
		this.handler = handler;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	public void addArchive(Archive a) {
		if (!archives.contains(a)) {
			archives.add(a);
		}
	}
	
	public void removeArchive(Archive a) {
		archives.remove(a);
		if (mainArchive.equals(a)) {
			mainArchive = new Archive();
		}
	}
	
	public void setMainArchive(Archive a) {
		this.mainArchive = a;
		if (!archives.contains(a)) {
			archives.add(a);
		}
	}
	
	public Archive getMainArchive() {
		return mainArchive;
	}

	
	public Archive[] getArchives() {
		return archives.toArray(new Archive[archives.size()]);
	}
	
	public void addResult(IAnalysisResult result) {
		if (!results.contains(result))
			results.add(result);
	}
	
	public void removeResult(IAnalysisResult result) {
		results.remove(result);
	}
	
	public IAnalysisResult[] getResults() {
		return results.toArray(new IAnalysisResult[results.size()]);
	}

}
