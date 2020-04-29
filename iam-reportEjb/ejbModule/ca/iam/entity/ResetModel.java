package ca.iam.entity;

public class ResetModel {
	private String date;
	private int domain;
	private int apps;
	
	public ResetModel(String date, int domain, int apps) {
		this.date = date;
		this.domain = domain;
		this.apps = apps;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getDomain() {
		return domain;
	}

	public void setDomain(int domain) {
		this.domain = domain;
	}

	public int getApps() {
		return apps;
	}

	public void setApps(int apps) {
		this.apps = apps;
	}
	

}
