package ca.iam.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CountList {
private String date;
private int count;

public CountList(String date, int count) {
	this.date = date;
	this.count = count;
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
