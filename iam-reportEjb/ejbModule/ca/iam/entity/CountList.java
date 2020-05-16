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
	String [] arr = begin.split("-");
	int month_temp = Integer.parseInt(arr[1]);
	String temp_begin = arr[2] + " " + new DateFormatSymbols().getMonths()[month_temp-1] + " " + arr[0];
	arr = end.split("-");
	String temp_end = arr[2] + " " + new DateFormatSymbols().getMonths()[month_temp-1] + " " + arr[0];
	month_temp = Integer.parseInt(arr[1]);
	this.appsName = appsName;
	this.count = count;
	this.date = temp_begin + " - " + temp_end;
	
	
	
	
}

public CountList(String begin, String end, int count) {
	String [] arr = begin.split("-");
	int month_temp = Integer.parseInt(arr[1]);
	String temp_begin = arr[2] + " " + new DateFormatSymbols().getMonths()[month_temp-1] + " " + arr[0];
	arr = end.split("-");
	String temp_end = arr[2] + " " + new DateFormatSymbols().getMonths()[month_temp-1] + " " + arr[0];
	month_temp = Integer.parseInt(arr[1]);
	this.count = count;
	this.date = temp_begin + " - " + temp_end;
	
	
	
	
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
