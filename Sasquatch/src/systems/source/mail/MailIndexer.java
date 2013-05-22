package systems.source.mail;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import systems.source.Source;
import systems.source.SourceIndexer;

public class MailIndexer extends SourceIndexer {
	
	private static Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_43);
	
	public MailIndexer(File indexDir) {
		if (indexDir.isDirectory()) {
			//clearDirectory(indexDir);
			try {
				Directory dir = FSDirectory.open(indexDir);
				IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_43, analyzer);
				IndexWriter writer = new IndexWriter(dir, config);
				setWriter(writer);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	
	@SuppressWarnings("unused")
	private void clearDirectory(File dir) {
		for (File f : dir.listFiles()) {
			f.delete();
		}
	}
	
	@Override
	public void addSource(Source s) {
		if (s instanceof Mail) {
			addMail((Mail) s);
		}
	}

	private void addMail(Mail m) {
		Document doc = new Document();
		doc.add(new TextField("body", m.getBody(), Field.Store.YES));
		doc.add(new TextField("header", m.getHeader(), Field.Store.YES));
		try {
			getWriter().addDocument(doc);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
