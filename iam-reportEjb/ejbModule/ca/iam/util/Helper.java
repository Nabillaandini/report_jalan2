package ca.iam.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Helper {
	public static java.sql.Date util2sql(java.util.Date date){
		if(date != null)
			return new java.sql.Date(date.getTime());
		else
			return null;
	}
	public static String dateToString(Date date) {
		  DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
          String strDate = dateFormat.format(date);  
          return strDate;
	}
	public static int roundUpToNearestMultipleOfSix(int i) {
		if(i%6 != 0) {
			int multiple = i / 6;
			i = 6 * (multiple + 1);
		}
		return i;
	}
	
	public static String dateToStringWeek(Date date) {
		  DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
        String strDate = dateFormat.format(date);  
        return strDate;
	}
}
