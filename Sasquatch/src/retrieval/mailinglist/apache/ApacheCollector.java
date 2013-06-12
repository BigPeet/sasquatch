package retrieval.mailinglist.apache;

import java.util.ArrayList;

import retrieval.general.CrawlStat;

public class ApacheCollector extends CrawlStat {

	private ArrayList<String> texts = new ArrayList<String>();
	

	public ApacheCollector() {

	}

	@Override
	public ArrayList<Object> getData() {
		ArrayList<Object> retList = new ArrayList<Object>();
		for (String m : texts) {
			retList.add(m);
		}
		return retList;
	}

	@Override
	public void addData(Object o) {
		if (o instanceof String) {
			String tmp = (String) o;
			texts.add(tmp);
		}
	}

}
