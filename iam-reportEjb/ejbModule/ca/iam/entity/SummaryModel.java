package ca.iam.entity;

public class SummaryModel {
	private int countOnboard;
	private int countUpdate;
	private int countDisabled;
	private int phone;
	private int email;
	private int fullname;
	
	public SummaryModel(int countOnboard,int countUpdate,int countDisabled,int phone,int email,int fullname) {
		this.countOnboard=countOnboard;
		this.countUpdate=countUpdate;
		this.countDisabled=countDisabled;
		this.phone=phone;
		this.email=email;
		this.fullname=fullname;
		
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

	public int getPhone() {
		return phone;
	}

	public void setPhone(int phone) {
		this.phone = phone;
	}

	public int getEmail() {
		return email;
	}

	public void setEmail(int email) {
		this.email = email;
	}

	public int getFullname() {
		return fullname;
	}

	public void setFullname(int fullname) {
		this.fullname = fullname;
	}
}
