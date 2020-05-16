package ca.iam.util;

import java.sql.Connection;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class ADConn {
	static DataSource datasource = null;
	static Locale[] supportedLocales = {
		Locale.ENGLISH
	};
	
	static Locale activeLocale = Locale.ENGLISH;
	
	static ResourceBundle resourceBundle = null;
	static ResourceBundle messageBundle = null;

	static Boolean isLocaleChanged = true;
	
	

	public static String getMessageString(String key) {
		try {
			return getMessages().getString(key);
		}
		catch (MissingResourceException e) {
			return "!" + key + "!";
		}
		catch (Exception e) {
			return "#!" + key + "!#";
		}
	}
	
	public static ResourceBundle getMessages(){
		if ((messageBundle == null) || (isLocaleChanged)){
			messageBundle = ResourceBundle.getBundle("ca.iam.util.Messages", activeLocale);
		}
		return messageBundle;
	}
	public static Connection getConnectionSql(){
		Connection conn = null;
		int count = 0;
		while ((conn == null)&&(count < 10)){
			try{
				System.out.println("test connection "+count);
				if (datasource==null){
					Context context = new InitialContext();
					datasource = (DataSource) context.lookup("jdbc/AD");
				}
				conn = datasource.getConnection();
			}
			catch (Exception e){
				System.out.println("Exception getConnection(jdbc) : count = "+count);
				e.printStackTrace();
			}
			count ++;
		}
		return conn;
	}
}