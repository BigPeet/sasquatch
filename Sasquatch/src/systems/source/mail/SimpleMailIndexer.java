package systems.source.mail;

import java.io.File;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;

import systems.source.SimpleSourceIndexer;
import systems.source.Source;

public class SimpleMailIndexer extends SimpleSourceIndexer {

	public SimpleMailIndexer(File indexDir) {
		super(indexDir);
	}
	
	@Override
	protected Document getDocument(Source s) {
		Document doc = null;
		if (s instanceof Mail) {
			doc = new Document();
			Mail m = (Mail) s;
			doc.add(new TextField("header", m.getHeader(), Field.Store.YES));
			doc.add(new TextField("body", m.getBody(), Field.Store.YES));
		} else {
			doc = super.getDocument(s);
		}
		return doc;
	}

}
