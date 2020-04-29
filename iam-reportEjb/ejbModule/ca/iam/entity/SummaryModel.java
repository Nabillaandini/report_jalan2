package ca.iam.entity;

public class SummaryModel {
	private int countOnboard;
	private int countUpdate;
	private int countDisabled;
	private String date;
	
	
	
	public SummaryModel(int countOnboard,int countUpdate,int countDisabled, String date) {
		this.countOnboard=countOnboard;
		this.countUpdate=countUpdate;
		this.countDisabled=countDisabled;
		this.date = date;
		
	}
	
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public SummaryModel() {
		
	}

	public int getCountOnboard() {
		return countOnboard;
	}

	public void setCountOnboard(int countOnboard) {
		this.countOnboard = countOnboard;
	}

	public int getCountUpdate() {
		return countUpdate;
	}

	public void setCountUpdate(int countUpdate) {
		this.countUpdate = countUpdate;
	}

	public int getCountDisabled() {
		return countDisabled;
	}

	public void setCountDisabled(int countDisabled) {
		this.countDisabled = countDisabled;
	}

}
