package ca.iam.entity;

public class MonthReq {
	private String date;
	private String desc;
	
	public MonthReq(String desc, int month, int year) {
		this.desc = desc;
		this.date = month+" "+year;
	}
	public MonthReq() {
		
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
}
