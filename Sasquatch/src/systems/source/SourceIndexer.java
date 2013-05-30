package systems.source;

import java.io.IOException;

import org.apache.lucene.index.IndexWriter;

public abstract class SourceIndexer {
	
	private IndexWriter writer;
	
	public abstract void addSource(Source s);
	public abstract boolean open();
	
	public IndexWriter getWriter() {
		return writer;
	}
	
	protected void setWriter(IndexWriter writer) {
		this.writer = writer;
	}
	
	
	
	public void close() {
		if (writer != null) {
			try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
