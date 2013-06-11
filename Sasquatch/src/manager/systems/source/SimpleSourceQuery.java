package manager.systems.source;

import java.io.File;
import java.io.IOException;

import org.apache.james.mime4j.field.language.parser.ParseException;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.Collector;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import manager.systems.source.mail.Mail;

public class SimpleSourceQuery extends SourceQuery {

	protected static final Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_43);
	private static int hitsPerPage = 10000;
	
	private File indexDir;
	
	public SimpleSourceQuery(File indexDir) {
		this.indexDir = indexDir;
	}
	
	@Override
	public Source[] query(String queryStr) {
		IndexSearcher searcher = getSearcher();
		TopScoreDocCollector collector = getCollector();
		Query q = parseQuery(queryStr);
		try {
			searcher.search(q, collector);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		ScoreDoc[] hits = collector.topDocs().scoreDocs;
		Source[] sources = new Source[hits.length];
		
		for (int i = 0; i < hits.length; i++) {
			int docId = hits[i].doc;
			Document d = null;
			try {
				d = searcher.doc(docId);
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (d != null) {
				Source s = parseDocument(d);
				sources[i] = s;
			}
		}
		return sources;
	}
	
	protected Source parseDocument(Document doc) {
		return new Source(doc.get("text"));
	}

	protected Query parseQuery(String query) {
		Query q = null;
		try {
			q = new QueryParser(Version.LUCENE_43, "text", analyzer).parse(query);
		} catch (org.apache.lucene.queryparser.classic.ParseException e) {
			e.printStackTrace();
		}
		return q;
	}
	
	private IndexSearcher getSearcher() {
		return new IndexSearcher(getReader());
	}
	
	private TopScoreDocCollector getCollector() {
		return TopScoreDocCollector.create(hitsPerPage, true);
	}

	@Override
	public boolean open() {
		boolean success = false;
		if (indexDir.isDirectory()) {
			try {
				Directory dir = FSDirectory.open(indexDir);
				IndexReader reader = DirectoryReader.open(dir);
				setReader(reader);
				success = true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return success;
	}

}
