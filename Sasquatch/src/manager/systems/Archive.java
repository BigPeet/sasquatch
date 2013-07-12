package manager.systems;

import java.io.File;

import manager.systems.source.SourceHandler;
import manager.systems.source.mail.LocalMailHandler;
import manager.systems.source.mail.WebMailHandler;
import retrieval.interfaces.ICrawlController;
import retrieval.mailinglist.apache.ApacheCrawlController;
import retrieval.mailinglist.appleList.AppleListCrawlController;
import retrieval.mailinglist.eclipseList.EclipseListCrawlController;
import retrieval.mailinglist.javanet.JavaNetCrawlController;
import retrieval.mailinglist.mailArchive.MailArchiveCrawlController;
import retrieval.mailinglist.markmail.MMCrawlController;
import retrieval.mailinglist.pipermail.PiperMailCrawlController;
import retrieval.mailinglist.sourceforge.SFCrawlController;
import retrieval.mailinglist.yahoo.YahooCrawlController;

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

	@Override
	public boolean equals(Object o) {
		boolean equals = false;
		if (o instanceof Archive) {
			Archive other = (Archive) o;
			equals = this.type == other.type && this.reference.equals(other.reference);
		}
		return equals;
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
	
	public SourceHandler getSourceHandler() {
		SourceHandler handler = null;
		if (type == ArchiveType.LOCAL) {
			handler = new LocalMailHandler(new File(reference));
		} else {
			ICrawlController controller = getCrawlController();
			if (controller != null) {
				handler = new WebMailHandler(controller);
			}
		}
		return handler;
	}
	
	private ICrawlController getCrawlController() {
		ICrawlController controller = null;
		switch(type) {
		case APACHE: controller = new ApacheCrawlController(reference, start, end); break;
		case ECLIPSE_LIST: controller = new EclipseListCrawlController(reference, start, end, pages); break;
		case APPLE_LIST : controller = new AppleListCrawlController(new String[]{reference}, pages); break;
		case JAVANET : controller = new JavaNetCrawlController(reference, start, end); break;
		case MAIL_ARCHIVE : controller = new MailArchiveCrawlController(reference, start, end, pages); break;
		case MARK_MAIL : controller = new MMCrawlController(reference, start, end, pages); break;
		case PIPERMAIL : controller = new PiperMailCrawlController(new String[]{reference}, pages); break;
		case SOURCEFORGE : controller = new SFCrawlController(reference, start, end); break;
		case YAHOO : controller = new YahooCrawlController(reference, start, end, pages); break;
		case LOCAL: 
		default: controller = null;
		}
		return controller;
	}

}
