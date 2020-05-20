package ca.iam.entity;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CountList {
private String date;
private int count;
private String appsName;

public CountList(String date, int count) {
	this.date = date;
	this.count = count;
}

public CountList(String date, int count, String appsName) {
	this.date = date;
	this.count = count;
	this.appsName = appsName;
}
public CountList(String begin, String end, int count, String appsName) {
	setCountList(begin,end,count);
	this.appsName = appsName;	
}


public CountList(String begin, String end, int count) {
	setCountList(begin, end, count);
}

private void setCountList(String begin, String end, int count) {
	this.count = count;
	this.date = formatDate(begin) + " - " + formatDate(end);	
}

private String formatDate(String date) {
	String [] arr = date.split("-");
	int month_temp = Integer.parseInt(arr[1]);
	return arr[2] + " " + new DateFormatSymbols().getMonths()[month_temp-1] + " " + arr[0];
}

public CountList(int sum, int month, int year,String appsName) {
	this.count = sum;
	this.date =getMonth(month)+" "+year;	
	this.appsName = appsName;
}

public CountList(int sum, int month, int year) {
	this.count = sum;
	this.date =getMonth(month)+" "+year;	
}
public String getMonth(int month) {
    return new DateFormatSymbols().getMonths()[month-1];
}
public String getAppsName() {
	return appsName;
}

public void setAppsName(String appsName) {
	this.appsName = appsName;
}

public int getCount() {
	return count;
}

public void setCount(int count) {
	this.count = count;
}



public String getDate() {
	return date;
}



public void setDate(String date) {
	this.date = date;
}
}
