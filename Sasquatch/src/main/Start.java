package main;

import java.io.File;

import systems.SoftwareSystem;
import systems.source.Source;
import systems.source.mail.LocalMailHandler;

public class Start {

	private static String PATH = "res/mails/jboss.xml";
	
	public static void main(String[] args) {
		//SoftwareSystem jboss = new SoftwareSystem(new WebMailHandler(new JBossMLCrawlController()));
		SoftwareSystem jboss = new SoftwareSystem(new LocalMailHandler(new File(PATH)));
		System.out.println(jboss.getSources().length);
		for (Source m : jboss.getSources()) {
			System.out.println(m);
		}
	}

}
