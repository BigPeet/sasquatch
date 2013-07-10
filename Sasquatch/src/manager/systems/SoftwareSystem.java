package manager.systems;

import manager.systems.source.SourceHandler;
import manager.systems.source.Source;


public class SoftwareSystem {

	private Source[] sources;
	private SourceHandler handler;
	private String name;
	private Archive archive;
	
	public SoftwareSystem(String name, String localPath) {
		this.name = name;
		this.archive = new Archive(ArchiveType.LOCAL, localPath);
	}
	
	public SoftwareSystem(String name, String listName, int startYear, int endYear, ArchiveType archive) {
		this.name = name;
		this.archive = new Archive(archive, listName);
		this.archive.setStart(startYear);
		this.archive.setEnd(endYear);
	}
	
	public SoftwareSystem(String name, String listName, int startYear, int endYear, int pages, ArchiveType archive) {
		this.name = name;
		this.archive = new Archive(archive, listName);
		this.archive.setStart(startYear);
		this.archive.setEnd(endYear);
		this.archive.setPages(pages);
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
		return archive.getReference();
	}

	/**
	 * @param listName the listName to set
	 */
	public void setListName(String listName) {
		this.archive.setReference(listName);
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
		return archive.getStart();
	}

	/**
	 * @return the end
	 */
	public int getEnd() {
		return archive.getEnd();
	}

	/**
	 * @return the pages
	 */
	public int getPages() {
		return archive.getPages();
	}

	/**
	 * @param start the start to set
	 */
	public void setStart(int start) {
		archive.setStart(start);
	}

	/**
	 * @param end the end to set
	 */
	public void setEnd(int end) {
		archive.setEnd(end);
	}

	/**
	 * @param pages the pages to set
	 */
	public void setPages(int pages) {
		archive.setPages(pages);
	}

	/**
	 * @return the archive
	 */
	public ArchiveType getArchiveType() {
		return archive.getType();
	}

	/**
	 * @param archive the archive to set
	 */
	public void setArchive(ArchiveType type) {
		this.archive.setType(type);
	}

	/**
	 * @return the path
	 */
	public String getPath() {
		String path = "";
		if (archive.getType() == ArchiveType.LOCAL) {
			path = archive.getReference();
		}
		return path;
	}

	/**
	 * @param path the path to set
	 */
	public void setPath(String path) {
		if (archive.getType() == ArchiveType.LOCAL) {
			archive.setReference(path);
		}
	}

}
