package manager.systems.source.mail;

import manager.systems.source.Source;

public class Mail extends Source {
	
	private String header = "";
	private String body = "";
	private String sender = "";
	private String date = "";
	
	public Mail(String header, String body) {
		super(header + "\n" + body);
		this.header = header;
		this.body = body;
	}
	
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
			equals = header.equals(m.header) && body.equals(m.body) 
					&& sender.equals(m.sender) && date.equals(m.date);
		}
		return equals;
	}
	
	
}
