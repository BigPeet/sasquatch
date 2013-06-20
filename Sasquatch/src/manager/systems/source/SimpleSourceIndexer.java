package manager.systems.source;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class SimpleSourceIndexer extends SourceIndexer {

	private static Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_43);

	private File indexDir;

	public SimpleSourceIndexer(File indexDir) {
		this.indexDir = indexDir;
	}

	private void clearDirectory(File dir) {
		for (File f : dir.listFiles()) {
			f.delete();
		}
	}

	@Override
	public void addSource(Source s) {
		Document doc = getDocument(s);
		try {
			getWriter().addDocument(doc);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected Document getDocument(Source s) {
		Document doc = new Document();
		doc.add(new TextField("text", s.getText(), Field.Store.YES));
		return doc;
	}

	@Override
	public boolean open() {
		boolean success = false;
		if (indexDir.isDirectory()) {
			clearDirectory(indexDir);
			try {
				Directory dir = FSDirectory.open(indexDir);
				IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_43, analyzer);
				IndexWriter writer = new IndexWriter(dir, config);
				setWriter(writer);
				success = true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return success;
	}

}
