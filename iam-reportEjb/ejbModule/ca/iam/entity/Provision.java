package ca.iam.entity;

import java.text.DateFormatSymbols;

public class Provision {
private String date;
private String type;
private String appsName;

public Provision(String date, String type) {
	this.date = date;
	this.type = type;
	
}

public Provision(String date, String type,String apps,boolean isApp) {
	this.appsName = apps;
	this.date = date;
	this.type = type;
	
}
public Provision(String type, int month, int year, String appsName) {
	this.type = type;
	this.date =getMonth(month)+" "+year;
	this.appsName = appsName;
}

public Provision(String type, int month, int year) {
	this.type = type;
	this.date =getMonth(month)+" "+year;
}

public Provision(String begin, String end, String desc) {
	setProvision(begin, end, desc);
}

public Provision(String begin, String end, String desc, String apps) {
	setProvision(begin, end, desc);
	this.appsName = apps;
}


private void setProvision(String begin, String end, String  desc) {
	this.type = desc;
	this.date = formatDate(begin) + " - " + formatDate(end);	
}

private String formatDate(String date) {
	String [] arr = date.split("-");
	int month_temp = Integer.parseInt(arr[1]);
	return arr[2] + " " + new DateFormatSymbols().getMonths()[month_temp-1] + " " + arr[0];
}

public String getAppsName() {
	return appsName;
}
public void setAppsName(String appsName) {
	this.appsName = appsName;
}
public Provision() {
	this.date = "";
	this.type ="";
}
public String getMonth(int month) {
    return new DateFormatSymbols().getMonths()[month-1];
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
