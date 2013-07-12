package main;

import java.io.File;

import analyzer.SentimentAnalyzer;
import analyzer.interfaces.IAnalysisResult;
import analyzer.polarity.sentiwordnet.AspectPolarityResult;
import analyzer.polarity.sentiwordnet.SentiPolarityAnalyzer;
import manager.parser.SystemParser;
import manager.systems.SoftwareSystem;
import manager.systems.source.SourceHandler;
import manager.systems.source.mail.LocalMailHandler;

public class AnalyzeSystems {
	
	private static String userURL = "res/mails/users/19-06-2013/";
	
	private static SoftwareSystem httpClientUsers = new SoftwareSystem("httpclient", userURL + "httpclient.xml");
	private static SoftwareSystem log4jUsers = new SoftwareSystem("log4j", userURL + "log4j.xml");
	private static SoftwareSystem httpunitUsers = new SoftwareSystem("httpunit", userURL + "httpunit.xml");
	private static SoftwareSystem htmlunitUsers = new SoftwareSystem("htmlunit", userURL + "htmlunit.xml");
	private static SoftwareSystem jettyUsers = new SoftwareSystem("jetty", userURL + "jetty.xml");
	private static SoftwareSystem tomcatUsers = new SoftwareSystem("tomcat", userURL + "tomcat.xml"); 
	private static SoftwareSystem jpaUsers = new SoftwareSystem("jpa", userURL + "jpa.xml");
	private static SoftwareSystem tapestryUsers = new SoftwareSystem("tapestry", userURL + "tapestry.xml"); 
	private static SoftwareSystem jsfUsers = new SoftwareSystem("jsf", userURL + "jsf.xml");
	private static SoftwareSystem strutsUsers = new SoftwareSystem("struts", userURL + "struts.xml"); 
	private static SoftwareSystem nekohtmlUsers = new SoftwareSystem("nekohtml", userURL + "nekohtml.xml");
	private static SoftwareSystem htmlparserUsers = new SoftwareSystem("htmlparser", userURL + "htmlparser.xml");
	private static SoftwareSystem dom4jUsers = new SoftwareSystem("dom4j", userURL + "dom4j.xml");
	private static SoftwareSystem jbossUsers = new SoftwareSystem("jboss", userURL + "jboss.xml");
	private static SoftwareSystem glassFishUsers = new SoftwareSystem("glassfish", userURL + "glassfish.xml"); 
	private static SoftwareSystem resinUsers = new SoftwareSystem("resin", userURL + "resin.xml");

	
	
	private static SoftwareSystem[] userSystems = {httpClientUsers, log4jUsers, httpunitUsers,
		htmlunitUsers, jettyUsers, tomcatUsers, jpaUsers, tapestryUsers, jsfUsers, strutsUsers,
		nekohtmlUsers, htmlparserUsers, dom4jUsers, jbossUsers, glassFishUsers, resinUsers};
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SystemParser parser = new SystemParser(new File("res/test.xml"));
		SentiPolarityAnalyzer analyzer = new SentiPolarityAnalyzer();
		for (SoftwareSystem ss : userSystems) {
			SourceHandler handler = ss.getMainArchive().getSourceHandler();
			ss.setHandler(handler);
			AspectPolarityResult res = analyzer.analyze(ss);
//			System.out.println(ss.getName());
//			res.show();
			parser.addSoftwareSystem(ss);
			parser.addResultsToSoftwareSystem(ss, res);
		}
	}

}
