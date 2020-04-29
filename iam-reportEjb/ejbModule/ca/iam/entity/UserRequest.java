package ca.iam.entity;

public class UserRequest {
	private String date;
	private String appsName;
	private String temp;
	
	public UserRequest(String date, String appsName, String temp) {
		this.date = date;
		this.appsName = appsName;
		this.temp = temp;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getAppsName() {
		return appsName;
	}

	public void setAppsName(String appsName) {
		this.appsName = appsName;
	}

	public String getTemp() {
		return temp;
	}

	public void setTemp(String temp) {
		this.temp = temp;
	}

	

}
