package manager.systems;

public class Archive {

	private ArchiveType type;
	private String reference = "";
	private int start = -1;
	private int end = -1;
	private int pages = -1;
	
	public Archive() {
		
	}
	
	public Archive(ArchiveType type, String reference) {
		this.type = type;
		this.reference = reference;
	}

	/**
	 * @return the type
	 */
	public ArchiveType getType() {
		return type;
	}

	/**
	 * @return the reference
	 */
	public String getReference() {
		return reference;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(ArchiveType type) {
		this.type = type;
	}

	/**
	 * @param reference the reference to set
	 */
	public void setReference(String reference) {
		this.reference = reference;
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

}
