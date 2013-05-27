package systems.source.mail;

import java.io.File;

import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.search.Query;
import org.apache.lucene.util.Version;

import systems.source.SimpleSourceQuery;
import systems.source.Source;

public class SimpleMailQuery extends SimpleSourceQuery {
	
	private static final String[] MAIL_KEY_WORDS = {"body", "header"};

	public SimpleMailQuery(File indexDir) {
		super(indexDir);
	}

	@Override
	protected Source parseDocument(Document doc) {
		return new Mail(doc.get("header"), doc.get("body"));
	}
	
	@Override
	protected Query parseQuery(String queryStr) {
		Query q = null;
		try {
			q = new MultiFieldQueryParser(Version.LUCENE_43, MAIL_KEY_WORDS, analyzer).parse(queryStr);
		} catch (org.apache.lucene.queryparser.classic.ParseException e) {
			e.printStackTrace();
		}
		return q;
	}

}
