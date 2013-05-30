package systems.source;

public class Source {
	
	private String text;
	
	public Source() {
		this.text = "";
	}
	
	public Source(String text) {
		this.text = text;
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
}
