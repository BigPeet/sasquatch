package systems.source;

import org.apache.lucene.index.IndexWriter;

public abstract class SourceIndexer {
	
	private IndexWriter writer;
	
	public abstract void addSource(Source s);
	
	public IndexWriter getWriter() {
		return writer;
	}
	
	protected void setWriter(IndexWriter writer) {
		this.writer = writer;
	}

}
