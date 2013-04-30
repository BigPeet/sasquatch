package crawler.mailinglist;

import java.io.File;

import crawler.general.AbstractCrawler;

public abstract class MailingListCrawler extends AbstractCrawler {
	
	private static File storageFolder;
	
	public static void configure(String[] domains, String storageFolderName) {
		AbstractCrawler.configure(domains);
		storageFolder = new File(storageFolderName);
        if (!storageFolder.exists()) {
                storageFolder.mkdirs();
        }
	}
}
