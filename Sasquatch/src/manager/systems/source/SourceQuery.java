package manager.systems.source;

import java.io.IOException;

import org.apache.lucene.index.IndexReader;

public abstract class SourceQuery {
	
	private IndexReader reader;
	
	public abstract Source[] query(String q);
	public abstract boolean open();
	
	public IndexReader getReader() {
		return reader;
	}
	
	protected void setReader(IndexReader reader) {
		this.reader = reader;
	}
	
	public void close() {
		if (reader != null) {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
