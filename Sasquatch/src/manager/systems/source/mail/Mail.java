package manager.systems.source.mail;

import java.util.Date;

import manager.systems.source.Source;

public class Mail extends Source {
	
	private String header = "";
	private String body = "";
	private String sender = "";
	
	public Mail(String header, String body) {
		super(body);
		this.header = header;
		this.body = body;
	}

	public Mail(String header, String body, Date date) {
		super(body, date);
		this.header = header;
		this.body = body;
	}
	
	@Override
	public String toString() {
		return "Header: " + header + "\nBody: " + body;
	}

	/**
	 * @return the text
	 */
	public String getBody() {
		return body;
	}

	/**
	 * @param text the text to set
	 */
	public void setBody(String text) {
		this.body = text;
	}

	/**
	 * @return the header
	 */
	public String getHeader() {
		return header;
	}

	/**
	 * @param header the header to set
	 */
	public void setHeader(String header) {
		this.header = header;
	}
	
	@Override
	public boolean equals(Object o) {
		boolean equals = false;
		if (o instanceof Mail) {
			Mail m = (Mail) o;
			if (header != null && body != null) {
				equals = header.equals(m.header) 
						&& body.equals(m.body);
			}
			if (equals && getDate() != null) {
				equals = getDate().equals(m.getDate());
			}
		}
		return equals;
	}
	
	
}
