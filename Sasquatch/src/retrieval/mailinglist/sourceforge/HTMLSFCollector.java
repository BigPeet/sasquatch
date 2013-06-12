package retrieval.mailinglist.sourceforge;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.htmlparser.Parser;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.tags.FormTag;
import org.htmlparser.tags.TableTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import retrieval.general.CrawlStat;


public class HTMLSFCollector extends CrawlStat {
	
	private static String TABLE_WITDH = "width";
	private static String TABLE_BORDER = "border";
	private static String TABLE_PADDING = "cellpadding";
	private static String TABLE_SPACING = "cellspacing";

	private static String TABLE_WITDH_VALUE = "100%";
	private static String TABLE_BORDER_VALUE = "0";
	private static String TABLE_PADDING_VALUE = "0";
	private static String TABLE_SPACING_VALUE = "0";

	private Parser parser;
	private ArrayList<String> texts = new ArrayList<String>();
	
	
	public HTMLSFCollector() {
		this.parser = new Parser();
	}

	public HTMLSFCollector(String path) {
		try {
			this.parser = new Parser(path);
		} catch (ParserException e) {
			e.printStackTrace();
		}
	}

	public void setResource(String path) {
		try {
			this.parser.setResource(path);
		} catch (ParserException e) {
			e.printStackTrace();
		}
	}

	public String[] extractLocalData(String text) {
		try {
			this.parser.setResource(text);
		} catch (ParserException e) {
			e.printStackTrace();
		}
		return extractLocalData();
	}

	public String[] extractLocalData() {
		ArrayList<String> mailTables = new ArrayList<String>();
		FormTag form = getForm();
		if (form != null) {
			NodeList tables = form.searchFor(TableTag.class, true);
			for (int i = 0; i < tables.size(); i++) {
				TableTag table = (TableTag) tables.elementAt(i);
				if (isDataTable(table) && !mailTables.contains(table.getStringText())) {
					mailTables.add(table.getStringText());
				}
			}
		}
		return mailTables.toArray(new String[mailTables.size()]);
	}

	private boolean isDataTable(TableTag table) {
		return correctBorder(table) 
				&& correctPadding(table) 
				&& correctSpacing(table)
				&& correctWidth(table);
	}

	private boolean correctWidth(TableTag table) {
		String width = table.getAttribute(TABLE_WITDH);
		return (width != null) && width.equals(TABLE_WITDH_VALUE);
	}

	private boolean correctSpacing(TableTag table) {
		String spacing = table.getAttribute(TABLE_SPACING);
		return (spacing != null) && spacing.equals(TABLE_SPACING_VALUE);
	}

	private boolean correctPadding(TableTag table) {
		String padding = table.getAttribute(TABLE_PADDING);
		return (padding != null) && padding.equals(TABLE_PADDING_VALUE);
	}

	private boolean correctBorder(TableTag table) {
		String border = table.getAttribute(TABLE_BORDER);
		return (border != null) && border.equals(TABLE_BORDER_VALUE);
	}

	private FormTag getForm() {
		HasAttributeFilter formFilter = new HasAttributeFilter("action", "/mailarchive/forum.php");
		FormTag form = null;
		try {
			NodeList list = parser.parse(formFilter);
			form = (FormTag) list.elementAt(0);
		} catch (ParserException e) {
			e.printStackTrace();
		}
		return form;
	}

	@SuppressWarnings("unused")
	private static String readFile(String path) {
		String content = "";
		String line = "";
		File f = new File(path);
		if (f.exists() && f.canRead()) {
			try {
				BufferedReader reader = new BufferedReader(new FileReader(f));
				while((line = reader.readLine()) != null) {
					content += line;
				}
				reader.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return content;
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
			String[] tables = extractLocalData((String) o);
			for (String t : tables) {
				texts.add(t);
			}
		}
	}

}
