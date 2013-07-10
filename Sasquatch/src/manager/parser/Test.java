package manager.parser;

import java.io.File;

import manager.systems.ArchiveType;
import manager.systems.SoftwareSystem;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SoftwareSystem ss = new SoftwareSystem("TestSystem", "testList", 2010, 2013, ArchiveType.SOURCEFORGE);
		SystemParser parser = new SystemParser(new File("res/test.xml"));
		parser.addSoftwareSystem(ss);
	}

}
