package ca.iam.eao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;

import ca.iam.entity.CountList;
import ca.iam.entity.Provision;
import ca.iam.entity.SummaryModel;
import ca.iam.entity.UserModel;
import ca.iam.entity.UserUpdates;
import ca.iam.util.ADConn;
import ca.iam.util.Helper;
import ca.iam.util.SQLConn;
import ca.iam.util.Settings;
import ca.iam.util.ReportConn;

@Stateless
@LocalBean
@Named

public class SummaryEao {
	public ArrayList<CountList> countDeprovMonthly(Date begin_date, Date end_date) throws SQLException, ParseException {

		ResultSet rs = null;
		Connection conn = SQLConn.getConnectionSql();
		ArrayList<CountList> resultCount = new ArrayList<CountList>();

		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(begin_date);
			int month_begin = begin_date.getMonth();
			int month_end = end_date.getMonth();
			int year_begin = cal.get(Calendar.YEAR);
			cal.setTime(end_date);
			int year_end = cal.get(Calendar.YEAR);
			System.out.println(month_begin+1);
			System.out.println(month_end+1);
			System.out.println("before exec sql");
			CallableStatement stmt = conn.prepareCall("{call countDeprovSummary(?,?,?,?)}");
			stmt.setInt(1, month_begin+1);
			stmt.setInt(2, year_begin);
			stmt.setInt(3, month_end+1);
			stmt.setInt(4, year_end);
			stmt.execute();
			rs = stmt.getResultSet();

			try {
				while (rs.next()) {
					int year = rs.getInt(1);
					int month = rs.getInt(2);
					int countSAP = rs.getInt(3);
					int countDTKBM = rs.getInt(4);
					int countDTOBM = rs.getInt(5);
					System.out.println(month);
					System.out.println(year);
					String date_str = new DateFormatSymbols().getMonths()[month-1]+ " "  + year;
					CountList temp = new CountList(date_str, countSAP,"SAP");
					resultCount.add(temp);
					temp =   new CountList(date_str, countDTOBM,"DTOBM");
					resultCount.add(temp);
					temp =   new CountList(date_str, countDTKBM,"DTKBM");
					resultCount.add(temp);
				}
			} finally {
				stmt.close();
			}

		} finally {
			conn.close();
		}
		System.out.println(resultCount.size());

		return resultCount;
	}
	
	public ArrayList<CountList> countDeprovWeek(Date begin_date, Date end_date) throws SQLException, ParseException {

		ResultSet rs = null;
		Connection conn = SQLConn.getConnectionSql();
		ArrayList<CountList> resultCount = new ArrayList<CountList>();

		try {

			java.sql.Date begin = new java.sql.Date(begin_date.getTime());
			java.sql.Date end = new java.sql.Date(end_date.getTime());
			System.out.println("before exec sql");
			CallableStatement stmt = conn.prepareCall("{call countDeprovWeek(?,?)}");
			stmt.setDate(1, (java.sql.Date) begin);
			stmt.setDate(2, (java.sql.Date) end);

			stmt.execute();
			rs = stmt.getResultSet();

			try {
				while (rs.next()) {
					Date a = rs.getDate(1);
					Date b = rs.getDate(2);
					int countSAP = rs.getInt(3);
					int countDTOBM = rs.getInt(4);
					int countDTKBM = rs.getInt(5);
					String a_str = Helper.dateToString(a);
					String b_str = Helper.dateToString(b);
					CountList temp = new CountList(a_str,b_str, countSAP,"SAP");
					resultCount.add(temp);
					temp =   new CountList(a_str,b_str, countDTOBM,"DTOBM");
					resultCount.add(temp);
					temp =   new CountList(a_str,b_str, countDTKBM,"DTKBM");
					resultCount.add(temp);
				}
			} finally {
				stmt.close();
			}

		} finally {
			conn.close();
		}
		System.out.println(resultCount.size());

		return resultCount;
	}
	
	public int getUserActive(String apps) throws SQLException, ParseException {

		ResultSet rs = null;
		Connection conn = ADConn.getConnectionSql();
		int res = 0;

		try {


			CallableStatement stmt = conn.prepareCall("{call getCountUser}");
			System.out.println(stmt);

			stmt.execute();
			rs = stmt.getResultSet();
			System.out.println("exec getUseractive");
			try {
				while (rs.next()) {
					if(apps.equalsIgnoreCase("DTKBM")) {
						res = rs.getInt(1);
						System.out.println(res);
					}

				}
			} finally {
				stmt.close();
			}
		} finally {
			conn.close();
		}

		return res;
	}
	
	

}
