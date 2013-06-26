package manager.systems;

import manager.systems.source.SourceHandler;
import manager.systems.source.Source;


public class SoftwareSystem {

	private Source[] sources;
	private SourceHandler handler;
	private String name;
	private String path;
	private String listName;
	private int start;
	private int end;
	private int pages = -1;
	private Archive archive = Archive.LOCAL;
	
	public SoftwareSystem(String name, String path) {
		this.name = name;
		this.path = path;
	}
	
	public SoftwareSystem(String name, String listName, int startYear, int endYear, Archive archive) {
		this.name = name;
		this.listName = listName;
		this.start = startYear;
		this.end = endYear;
		this.archive = archive;
	}
	
	public SoftwareSystem(String name, String listName, int startYear, int endYear, int pages, Archive archive) {
		this.name = name;
		this.listName = listName;
		this.start = startYear;
		this.end = endYear;
		this.pages = pages;
		this.archive = archive;
	}
	
	public SoftwareSystem(String name, SourceHandler handler) {
		this.name = name;
		this.listName = "";
		this.handler = handler;
	}
	
	public SoftwareSystem(String name, Source[] sources) {
		this.name = name;
		this.listName = "";
		this.sources = sources;
	}
	
	public Source[] getSources() {
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
	 * @return the listName
	 */
	public String getListName() {
		return listName;
	}

	/**
	 * @param listName the listName to set
	 */
	public void setListName(String listName) {
		this.listName = listName;
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

	/**
	 * @return the start
	 */
	public int getStart() {
		return start;
	}

	/**
	 * @return the end
	 */
	public int getEnd() {
		return end;
	}

	/**
	 * @return the pages
	 */
	public int getPages() {
		return pages;
	}

	/**
	 * @param start the start to set
	 */
	public void setStart(int start) {
		this.start = start;
	}

	/**
	 * @param end the end to set
	 */
	public void setEnd(int end) {
		this.end = end;
	}

	/**
	 * @param pages the pages to set
	 */
	public void setPages(int pages) {
		this.pages = pages;
	}

	/**
	 * @return the archive
	 */
	public Archive getArchive() {
		return archive;
	}

	/**
	 * @param archive the archive to set
	 */
	public void setArchive(Archive archive) {
		this.archive = archive;
	}

	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @param path the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}

}
