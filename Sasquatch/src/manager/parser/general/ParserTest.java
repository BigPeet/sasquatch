package manager.parser.general;

import java.io.File;



import manager.systems.source.mail.Mail;
import manager.systems.source.mail.LocalMailHandler;

public class ParserTest {

	
	private static String PATH = "res/mails/jboss.xml";
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		File f = new File(PATH);
		LocalMailHandler handler = new LocalMailHandler(f);
		Mail[] mails = handler.getMails();
		
		System.out.println(mails.length);

	}

}