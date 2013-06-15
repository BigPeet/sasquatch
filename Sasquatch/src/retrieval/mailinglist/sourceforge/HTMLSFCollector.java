package retrieval.mailinglist.sourceforge;

import java.util.ArrayList;

import org.htmlparser.Parser;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.tags.FormTag;
import org.htmlparser.tags.TableTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import retrieval.mailinglist.TextCollector;


public class HTMLSFCollector extends TextCollector {

	private static String TABLE_WITDH = "width";
	private static String TABLE_BORDER = "border";
	private static String TABLE_PADDING = "cellpadding";
	private static String TABLE_SPACING = "cellspacing";

	private static String TABLE_WITDH_VALUE = "100%";
	private static String TABLE_BORDER_VALUE = "0";
	private static String TABLE_PADDING_VALUE = "0";
	private static String TABLE_SPACING_VALUE = "0";

	private Parser parser;

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

	@Override
	public void addData(String text) {
		String[] tables = extractLocalData(text);
		for (String t : tables) {
			super.addData(t);
		}
	}

}
