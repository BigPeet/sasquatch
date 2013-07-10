package manager.parser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import manager.systems.Archive;
import manager.systems.ArchiveType;
import manager.systems.SoftwareSystem;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import analyzer.interfaces.IAnalysisResult;
import analyzer.polarity.sentiwordnet.Aspect;
import analyzer.polarity.sentiwordnet.AspectPolarityResult;

public class SystemParser {

	private File target;
	private static final String DATE_FORMAT = "dd/MM/yyyy HH:mm:ss";

	public SystemParser(File target) {
		this.target = target;
		if (!target.exists()) {
			createEmptyFile(target);
		}
	}

	public File getFile() {
		return target;
	}

	private void createEmptyFile(File f) {
		if (!f.exists()) {
			try {
				f.createNewFile();
				BufferedWriter writer = new BufferedWriter(new FileWriter(f));
				writer.write("<systems></systems>");
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void clear() {
		if (target.exists()) {
			Document doc = getDocument(target);
			if (doc != null) {
				Element root = doc.getRootElement();
				root.removeContent();
				writeDocument(doc, target);
			}
		}
	}

	public void addSoftwareSystem(SoftwareSystem ss) {
		Document doc = getDocument(target);
		if (!contains(doc, ss)) {
			if (doc != null) {
				Element root = doc.getRootElement();
				Element system = createSystemElement(ss);
				root.addContent(system);
				if (system != null) {
					writeDocument(doc, target);
				}
			}
		}
	}
	
	private Element createSystemElement(SoftwareSystem ss) {
		Element system = null;
		try {
			system = new Element("system");
			system.setAttribute("name" , ss.getName());
			Element archives = getArchives(ss);
			system.addContent(archives);
			Element results = getResults();
			system.addContent(results);
		}  catch (org.jdom.IllegalDataException e) {
			system = null;
		}
		return system;
	}
	
	public void addResultsToSoftwareSystem(SoftwareSystem ss, IAnalysisResult result) {
		Document doc = getDocument(target);
		if (doc != null) {
			Element system = getSystemElement(doc, ss);
			if (system != null) {
				Element results = system.getChild("results");
				Element res = getResult(result);
				if (res != null) {
					results.addContent(res);
					writeDocument(doc, target);
				}
			}
		}
	}
	
	public void addArchiveToSoftwareSystem(SoftwareSystem ss, Archive a) {
		Document doc = getDocument(target);
		if (doc != null) {
			Element system = getSystemElement(doc, ss);
			if (system != null) {
				Element archives = system.getChild("archives");
				Element archive = getArchive(a);
				if (archive != null) {
					archives.addContent(archive);
					writeDocument(doc, target);
				}
			}
		}
	}
	
	private Element getResult(IAnalysisResult result) {
		Element res = null;
		if (result instanceof AspectPolarityResult) {
			AspectPolarityResult asResult = (AspectPolarityResult) result;
			res = new Element("result");
			res.setAttribute("type", "AspectPolarity");
			res.setAttribute("date" , new Date().toString());
			Element pos = new Element("positive");
			pos.setText(asResult.getPositive() + "");
			Element neg = new Element("negative");
			neg.setText(asResult.getNegative() + "");
			Element neut = new Element("neutral");
			neut.setText(asResult.getNeutral() + "");
			Element notUsed = new Element("notUsed");
			notUsed.setText(asResult.getNotUsed() + "");
			Element aspects = getAspects(asResult);
			res.addContent(pos);
			res.addContent(neg);
			res.addContent(neut);
			res.addContent(notUsed);
			res.addContent(aspects);
		}
		return res;
	}

	private Element getAspects(AspectPolarityResult asResult) {
		Element aspects = new Element("aspects");
		for (Aspect a : asResult.getAspects()) {
			Element aspect = new Element("aspect");
			aspect.setAttribute("name", a.getName());
			Element pos = new Element("positive");
			pos.setText(a.getPositive() + "");
			Element neg = new Element("negative");
			neg.setText(a.getNegative() + "");
			aspect.addContent(pos);
			aspect.addContent(neg);
			aspects.addContent(aspect);
		}
		return aspects;
	}

	private Element getSystemElement(Document doc, SoftwareSystem ss) {
		Element system = null;
		if (doc != null) {
			Element root = doc.getRootElement();
			for (Object o : root.getChildren()) {
				if (o instanceof Element) {
					Element child = (Element) o;
					if (child.getAttribute("name").equals(ss.getName())) {
						system = child;
						break;
					}
				}
			}
		}
		return system;
	}
	
	private boolean contains(Document doc, SoftwareSystem ss) {
		boolean contains = false;
		if (doc != null) {
			Element root = doc.getRootElement();
			for (Object o : root.getChildren()) {
				if (o instanceof Element) {
					Element child = (Element) o;
					if (child.getAttribute("name").equals(ss.getName())) {
						contains = true; 
						break;
					}
				}
			}
		}
		return contains;
	}

	private Element getResults() {
		return new Element("results");
	}

	private Element getArchives(SoftwareSystem ss) {
		Element archives = new Element("archives");
		String path = ss.getPath();
		if (ss.getArchiveType() == ArchiveType.LOCAL) {
			Element archive = getLocalArchive(SourceType.MAIL, path);
			archives.addContent(archive);
		}
		if (ss.getArchiveType() != ArchiveType.LOCAL) {
			Element archive = getArchive(SourceType.MAIL, ss.getArchiveType(), 
					ss.getListName(), ss.getStart(), ss.getEnd(), ss.getPages());
			archives.addContent(archive);
		}
		return archives;
	}

	private Element getArchive(Archive a) {
		return getArchive(SourceType.MAIL, a.getType(), a.getReference(), a.getStart(), a.getEnd(), a.getPages());
	}

	private Element getArchive(SourceType sourceType, ArchiveType type, String listName,
			int start, int end, int pages) {
		Element archive = new Element("archive");
		archive.setAttribute("sourcetype", sourceType.ordinal() + "");
		archive.setAttribute("type", type.ordinal() + "");
		Element link = new Element("listName");
		link.setText(listName);
		Element startDate = new Element("start");
		startDate.setText(start + "");
		Element endDate = new Element("end");
		endDate.setText(end + "");
		Element noOfPages = new Element("pages");
		noOfPages.setText(pages + "");
		archive.addContent(link);
		archive.addContent(startDate);
		archive.addContent(endDate);
		archive.addContent(noOfPages);
		return archive;
	}

	private Element getLocalArchive(SourceType sourceType, String path) {
		Element archive = new Element("archive");
		archive.setAttribute("sourcetype", sourceType.ordinal() + "");
		archive.setAttribute("type", ArchiveType.LOCAL.ordinal() + "");
		Element link = new Element("path");
		link.setText(path);
		archive.addContent(link);
		return archive;
	}

	public void addSoftwareSystems(SoftwareSystem[] systems) {
		Document doc = getDocument(target);
		if (doc != null) {
			Element root = doc.getRootElement();
			Element system = null;
			for (int j = 0; j < systems.length; j++) {
				try {
					SoftwareSystem ss = systems[j];
					system = createSystemElement(ss);
					root.addContent(system);
				}  catch (org.jdom.IllegalDataException e) {
					system = null;
				}
			}
			writeDocument(doc, target);
		}
	}

	public SoftwareSystem[] getSoftwareSystems() {
		ArrayList<SoftwareSystem> systems = new ArrayList<SoftwareSystem>();
		if (target.exists()) {
			Document doc = getDocument(target);
			if (doc != null) {
				Element root = doc.getRootElement();
				for (Object o : root.getChildren("mail")) {
					Element e = (Element) o;
					SoftwareSystem ss = getSoftwareSystem(e);
					if (ss != null) {
						systems.add(ss);
					}
				}
			}
		}
		return systems.toArray(new SoftwareSystem[systems.size()]);
	}

	private SoftwareSystem getSoftwareSystem(Element e) {
		String name = e.getAttributeValue("name");
		Element archives = e.getChild("archives");
		for (Object o : archives.getChildren()) {
			Element child = (Element) o;
			String type = child.getAttributeValue("type");
		}
		return null;
	}

	private Date convertDate(String childText) {
		Date date = null;
		DateFormat formatter = new SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH);
		try {
			date = formatter.parse(childText);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	private static void writeDocument(Document doc, File f) {
		XMLOutputter xmlOutput = new XMLOutputter();
		xmlOutput.setFormat(Format.getPrettyFormat());
		try {
			FileOutputStream os = new FileOutputStream(f);
			OutputStreamWriter writer = new OutputStreamWriter(os, "UTF-8");
			xmlOutput.output(doc, writer);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static Document getDocument(File f) {
		SAXBuilder builder = new SAXBuilder();
		Document doc = null;
		try {
			InputStream inputStream= new FileInputStream(f);
			Reader reader = new InputStreamReader(inputStream,"UTF-8");
			doc = builder.build(reader);
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return doc;
	}

}
