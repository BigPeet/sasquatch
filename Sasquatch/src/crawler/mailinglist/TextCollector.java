package crawler.mailinglist;

import java.util.ArrayList;
import crawler.general.CrawlStat;

public class TextCollector extends CrawlStat {
	
	private ArrayList<String> texts = new ArrayList<String>();

	@Override
	public ArrayList<Object> getData() {
		ArrayList<Object> retList = new ArrayList<Object>();
		for (String m : texts) {
			retList.add(m);
		}
		return retList;
	}
	
	public ArrayList<String> getTextData() {
		return texts;
	}
	
	@Override
	public void addData(Object o) {
		if (o instanceof String) {
			String m = (String) o;
			addData(m);
		}
	}
	
	public void addData(String text) {
		texts.add(text);
	}
	
	public void remove(String text) {
		texts.remove(text);
	}
	
	public int getSize() {
		return texts.size();
	}
	
	
}
