package manager.systems.source;

import java.util.Date;

public class Source {
	
	private String text;
	private Date date;
	
	public Source() {
		this.text = "";
		this.date = new Date();
	}
	
	public Source(String text) {
		this.text = text;
		this.date = new Date();
	}
	
	public Source(String text, Date date) {
		this.text = text;
		this.date = date;
	}
	
	public String getText() {
		return text;
	}
	
	protected void setText(String text) {
		this.text = text;
	}
	
	@Override
	public boolean equals(Object o) {
		boolean equals = false;
		if (o instanceof Source) {
			Source s = (Source) o;
			equals = text.equals(s.text);
		}
		
		return equals;
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}
}
