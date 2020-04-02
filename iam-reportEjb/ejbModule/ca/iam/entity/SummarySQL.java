package ca.iam.entity;

public class SummarySQL {
	private int prov;
	private int deprov;
	private String date;

	public SummarySQL(int prov, int deprov, String date) {
		this.date = date;
		this.prov = prov;
		this.deprov = deprov;

	}

	public SummarySQL() {
		this.date = "";
		this.prov = 0;
		this.deprov = 0;
	}

	public int getProv() {
		return prov;
	}

	public void setProv(int prov) {
		this.prov = prov;
	}

	public int getDeprov() {
		return deprov;
	}

	public void setDeprov(int deprov) {
		this.deprov = deprov;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}
