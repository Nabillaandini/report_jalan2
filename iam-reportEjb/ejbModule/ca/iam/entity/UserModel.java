package ca.iam.entity;

import java.text.DateFormatSymbols;
import java.util.Date;

public class UserModel {
	private String date;
	private String name;
	
	public UserModel(String date, String name) {
		this.date= date;
		this.name =name;
	}
	public UserModel(String type, int month, int year) {
		this.name = type;
		this.date =getMonth(month)+" "+year;
	}
	

public String getMonth(int month) {
    return new DateFormatSymbols().getMonths()[month-1];
}

public UserModel(String begin, String end, String desc) {
	String [] arr = begin.split("-");
	int month_temp = Integer.parseInt(arr[1]);
	String temp_begin = arr[2] + " " + new DateFormatSymbols().getMonths()[month_temp-1] + " " + arr[0];
	arr = end.split("-");
	String temp_end = arr[2] + " " + new DateFormatSymbols().getMonths()[month_temp-1] + " " + arr[0];
	month_temp = Integer.parseInt(arr[1]);
	this.name = desc;
	this.date = temp_begin + " - " + temp_end;
	
	
	
	
}
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
