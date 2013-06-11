package manager.parser.html;

import java.util.ArrayList;

public class HTMLMMCollector extends HTMLCollector {

	private ArrayList<String> texts = new ArrayList<String>();
	
	public HTMLMMCollector() {
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
			texts.add((String) o);
		}
	}
}
