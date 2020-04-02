package ca.iam.entity;

public class Provision {
private String date;
private String type;

public Provision(String date, String type) {
	this.date = date;
	this.type = type;
	
}

public Provision() {
	this.date = "";
	this.type ="";
}

public String getDate() {
	return date;
}

public void setDate(String date) {
	this.date = date;
}

public String getType() {
	return type;
}

public void setType(String type) {
	this.type = type;
}

}
