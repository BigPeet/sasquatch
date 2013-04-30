package imagecrawler;

import java.io.File;
import java.util.regex.Pattern;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.BinaryParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import edu.uci.ics.crawler4j.util.IO;

/**
 * @author Yasser Ganjisaffar <lastname at gmail dot com>
 */

/*
 * This class shows how you can crawl images on the web and store them in a
 * folder. This is just for demonstration purposes and doesn't scale for large
 * number of images. For crawling millions of images you would need to store
 * downloaded images in a hierarchy of folders
 */
public class ImageCrawler extends WebCrawler {

        private static final Pattern filters = Pattern.compile(".*(\\.(css|js|mid|mp2|mp3|mp4|wav|avi|mov|mpeg|ram|m4v|pdf"
                        + "|rm|smil|wmv|swf|wma|zip|rar|gz))$");

        private static final Pattern imgPatterns = Pattern.compile(".*(\\.(bmp|gif|jpe?g|png|tiff?))$");

        private static File storageFolder;
        private static String[] crawlDomains;

        public static void configure(String[] domain, String storageFolderName) {
                ImageCrawler.crawlDomains = domain;

                storageFolder = new File(storageFolderName);
                if (!storageFolder.exists()) {
                        storageFolder.mkdirs();
                }
        }

        @Override
        public boolean shouldVisit(WebURL url) {
                String href = url.getURL().toLowerCase();
                if (filters.matcher(href).matches()) {
                        return false;
                }

                if (imgPatterns.matcher(href).matches()) {
                        return true;
                }

                for (String domain : crawlDomains) {
                        if (href.startsWith(domain)) {
                                return true;
                        }
                }
                return false;
        }

        @Override
        public void visit(Page page) {
                String url = page.getWebURL().getURL();

                // We are only interested in processing images
                if (!(page.getParseData() instanceof BinaryParseData)) {
                        return;
                }

                if (!imgPatterns.matcher(url).matches()) {
                        return;
                }

                // Not interested in very small images
                if (page.getContentData().length < 10 * 1024) {
                        return;
                }

                // get a unique name for storing this image
                String extension = url.substring(url.lastIndexOf("."));
                String hashedName = Cryptography.MD5(url) + extension;

                // store image
                IO.writeBytesToFile(page.getContentData(), storageFolder.getAbsolutePath() + "/" + hashedName);

                System.out.println("Stored: " + url);
        }
}

