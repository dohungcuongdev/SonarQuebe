package dxc.technology.model;

public class ShortData {

	private String date;
	private String value;

	public ShortData() {

	}

	public ShortData(String date, String value) {
		super();
		this.date = date;
		this.value = value;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
