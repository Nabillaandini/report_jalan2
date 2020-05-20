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
import ca.iam.entity.UserUpdates;
import ca.iam.util.Helper;
import ca.iam.util.SQLConn;
import ca.iam.util.Settings;
import ca.iam.util.ReportConn;

@Stateless
@LocalBean
@Named

public class ChangeEao {
	public ArrayList<CountList> singleResetADMonthly(Date beginDate, Date endDate)
			throws SQLException, ParseException {

		ResultSet rs = null;
		Connection conn = SQLConn.getConnectionSql();
		ArrayList<CountList> resultCount = new ArrayList<CountList>();
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(beginDate);
			int month_begin = beginDate.getMonth();
			int month_end = endDate.getMonth();
			int year_begin = cal.get(Calendar.YEAR);
			cal.setTime(endDate);
			int year_end = cal.get(Calendar.YEAR);
			System.out.println(month_begin + 1);
			System.out.println(month_end + 1);
			System.out.println("before exec sql");
			CallableStatement stmt = conn.prepareCall("{call singleResetPassADMonthly(?,?,?,?)}");
			stmt.setInt(1, month_begin + 1);
			stmt.setInt(2, year_begin);
			stmt.setInt(3, month_end + 1);
			stmt.setInt(4, year_end);
			stmt.execute();
			rs = stmt.getResultSet();

			try {
				while (rs.next()) {
					int year = rs.getInt(1);
					int month = rs.getInt(2);
					int countPass = rs.getInt(3);
					CountList temp = new CountList(countPass, month, year);
					resultCount.add(temp);

				}
			} finally {
				stmt.close();
			}

		} finally {
			conn.close();
		}
		return resultCount;

	}
	
	public ArrayList<CountList> multiResetADMonthly(Date beginDate, Date endDate) throws SQLException, ParseException {

		ResultSet rs = null;
		Connection conn = SQLConn.getConnectionSql();
		ArrayList<CountList> resultCount = new ArrayList<CountList>();
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(beginDate);
			int month_begin = beginDate.getMonth();
			int month_end = endDate.getMonth();
			int year_begin = cal.get(Calendar.YEAR);
			cal.setTime(endDate);
			int year_end = cal.get(Calendar.YEAR);
			System.out.println(month_begin+1);
			System.out.println(month_end+1);
			System.out.println("before exec sql");
			CallableStatement stmt = conn.prepareCall("{call multiResetPassADMonthly(?,?,?,?)}");
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
					int countPass = rs.getInt(3);
					CountList temp = new CountList(countPass, month,year);
					resultCount.add(temp);
					
					
					
				}
			} finally {
				stmt.close();
			}

		} finally {
			conn.close();
		}
			return resultCount;
		

	}
	
	public ArrayList<CountList> singleResetPassADDaily(Date beginDate, Date endDate)
			throws SQLException, ParseException {

		ResultSet rs = null;
		Connection conn = SQLConn.getConnectionSql();
		ArrayList<CountList> resultCount = new ArrayList<CountList>();

		try {

			java.sql.Date begin = new java.sql.Date(beginDate.getTime());
			java.sql.Date end = new java.sql.Date(endDate.getTime());

			CallableStatement stmt = conn.prepareCall("{call singleResetPassADDaily(?,?)}");
			stmt.setDate(1, (java.sql.Date) begin);
			stmt.setDate(2, (java.sql.Date) end);

			stmt.execute();
			rs = stmt.getResultSet();

			try {
				while (rs.next()) {
					int count = rs.getInt(2);
					Date date = rs.getDate(1);
					if (date == null) {
						continue;
					}
					String date_str = Helper.dateToString(date);
					CountList temp = new CountList(date_str, count);
					resultCount.add(temp);
				}
			} finally {
				stmt.close();
			}

		} finally {
			conn.close();
		}

		return resultCount;
	}
	
	public ArrayList<CountList> multiResetPassADDaily(Date beginDate, Date endDate)
			throws SQLException, ParseException {

		ResultSet rs = null;
		Connection conn = SQLConn.getConnectionSql();
		ArrayList<CountList> resultCount = new ArrayList<CountList>();

		try {

			java.sql.Date begin = new java.sql.Date(beginDate.getTime());
			java.sql.Date end = new java.sql.Date(endDate.getTime());

			CallableStatement stmt = conn.prepareCall("{call multiResetPassADDaily(?,?)}");
			stmt.setDate(1, (java.sql.Date) begin);
			stmt.setDate(2, (java.sql.Date) end);

			stmt.execute();
			rs = stmt.getResultSet();

			try {
				while (rs.next()) {
					int count = rs.getInt(2);
					Date date = rs.getDate(1);
					if (date == null) {
						continue;
					}
					String date_str = Helper.dateToString(date);
					CountList temp = new CountList(date_str, count);
					resultCount.add(temp);
				}
			} finally {
				stmt.close();
			}

		} finally {
			conn.close();
		}

		return resultCount;
	}
	
	public ArrayList<CountList> singleResetPassADWeekly(Date beginDate, Date endDate) throws SQLException, ParseException {

		ResultSet rs = null;
		Connection conn = SQLConn.getConnectionSql();
		ArrayList<CountList> resultCount = new ArrayList<CountList>();

		try {

			java.sql.Date begin = new java.sql.Date(beginDate.getTime());
			java.sql.Date end = new java.sql.Date(endDate.getTime());
			System.out.println("before exec sql");
			CallableStatement stmt = conn.prepareCall("{call singleResetPassADWeekly(?,?)}");
			stmt.setDate(1, (java.sql.Date) begin);
			stmt.setDate(2, (java.sql.Date) end);

			stmt.execute();
			rs = stmt.getResultSet();

			try {
				while (rs.next()) {
					Date a = rs.getDate(1);
					Date b = rs.getDate(2);
					int count = rs.getInt(3);
					String a_str = Helper.dateToStringWeek(a);
					String b_str = Helper.dateToStringWeek(b);
					CountList temp = new CountList(a_str,b_str,count);
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
	
	

	public ArrayList<CountList> singleResetPassAppsDaily(Date beginDate, Date endDate, String apps)
			throws SQLException, ParseException {

		ResultSet rs = null;
		Connection conn = SQLConn.getConnectionSql();
		ArrayList<CountList> resultCount = new ArrayList<CountList>();

		try {

			java.sql.Date begin = new java.sql.Date(beginDate.getTime());
			java.sql.Date end = new java.sql.Date(endDate.getTime());

			CallableStatement stmt = conn.prepareCall("{call singleResetPassAppsDaily(?,?,?,?)}");
			String appsName = "%" +apps+"%";
			String query ="% - Reset Password%";
			if(apps.equalsIgnoreCase("MMS")) {
				query = "% - RESET PASSWORD%";
			}
			stmt.setDate(1, (java.sql.Date) begin);
			stmt.setDate(2, (java.sql.Date) end);
			stmt.setString(3, appsName);
			stmt.setString(4, query);
			
			stmt.execute();
			rs = stmt.getResultSet();

			try {
				while (rs.next()) {
					int count = rs.getInt(2);
					Date date = rs.getDate(1);
					if (date == null) {
						continue;
					}
					String date_str = Helper.dateToString(date);
					CountList temp = new CountList(date_str, count);
					resultCount.add(temp);
				}
			} finally {
				stmt.close();
			}

		} finally {
			conn.close();
		}

		return resultCount;
	}
	
	public ArrayList<CountList> multiResetPassAppsDaily(Date beginDate, Date endDate, String apps)
			throws SQLException, ParseException {

		ResultSet rs = null;
		Connection conn = SQLConn.getConnectionSql();
		ArrayList<CountList> resultCount = new ArrayList<CountList>();

		try {

			java.sql.Date begin = new java.sql.Date(beginDate.getTime());
			java.sql.Date end = new java.sql.Date(endDate.getTime());

			CallableStatement stmt = conn.prepareCall("{call multiResetPassAppsDaily(?,?,?,?)}");
			String appsName = "%" +apps+"%";
			String query ="% - Reset Password%";
			if(apps.equalsIgnoreCase("MMS")) {
				query = "% - RESET PASSWORD%";
			}
			stmt.setDate(1, (java.sql.Date) begin);
			stmt.setDate(2, (java.sql.Date) end);
			stmt.setString(3, appsName);
			stmt.setString(4, query);
			
			stmt.execute();
			rs = stmt.getResultSet();

			try {
				while (rs.next()) {
					int count = rs.getInt(2);
					Date date = rs.getDate(1);
					if (date == null) {
						continue;
					}
					String date_str = Helper.dateToString(date);
					CountList temp = new CountList(date_str, count);
					resultCount.add(temp);
				}
			} finally {
				stmt.close();
			}

		} finally {
			conn.close();
		}

		return resultCount;
	}
	
	public ArrayList<CountList> singleResetPassAPPWeekly(Date beginDate, Date endDate, String apps) throws SQLException, ParseException {

		ResultSet rs = null;
		Connection conn = SQLConn.getConnectionSql();
		ArrayList<CountList> resultCount = new ArrayList<CountList>();

		try {

			java.sql.Date begin = new java.sql.Date(beginDate.getTime());
			java.sql.Date end = new java.sql.Date(endDate.getTime());
			System.out.println("before exec sql");
			CallableStatement stmt = conn.prepareCall("{call singleResetPassAPPWeekly(?,?,?,?)}");
			String appsName = "%" +apps+"%";
			String query ="% - Reset Password%";
			if(apps.equalsIgnoreCase("MMS")) {
				query = "% - RESET PASSWORD%";
			}
			stmt.setDate(1, (java.sql.Date) begin);
			stmt.setDate(2, (java.sql.Date) end);
			stmt.setString(3, appsName);
			stmt.setString(4, query);
			
			stmt.execute();
			rs = stmt.getResultSet();

			try {
				while (rs.next()) {
					Date a = rs.getDate(1);
					Date b = rs.getDate(2);
					int count = rs.getInt(3);
					String a_str = Helper.dateToStringWeek(a);
					String b_str = Helper.dateToStringWeek(b);
					CountList temp = new CountList(a_str,b_str,count);
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
	
	public ArrayList<CountList> multiResetPassAPPWeekly(Date beginDate, Date endDate, String apps) throws SQLException, ParseException {

		ResultSet rs = null;
		Connection conn = SQLConn.getConnectionSql();
		ArrayList<CountList> resultCount = new ArrayList<CountList>();

		try {

			java.sql.Date begin = new java.sql.Date(beginDate.getTime());
			java.sql.Date end = new java.sql.Date(endDate.getTime());
			System.out.println("before exec sql");
			CallableStatement stmt = conn.prepareCall("{call multiResetPassAPPWeekly(?,?,?,?)}");
			String appsName = "%" +apps+"%";
			String query ="% - Reset Password%";
			if(apps.equalsIgnoreCase("MMS")) {
				query = "% - RESET PASSWORD%";
			}
			stmt.setDate(1, (java.sql.Date) begin);
			stmt.setDate(2, (java.sql.Date) end);
			stmt.setString(3, appsName);
			stmt.setString(4, query);
			
			stmt.execute();
			rs = stmt.getResultSet();

			try {
				while (rs.next()) {
					Date a = rs.getDate(1);
					Date b = rs.getDate(2);
					int count = rs.getInt(3);
					String a_str = Helper.dateToStringWeek(a);
					String b_str = Helper.dateToStringWeek(b);
					CountList temp = new CountList(a_str,b_str,count);
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
	
	
	public ArrayList<CountList> multiResetPassADWeekly(Date beginDate, Date endDate) throws SQLException, ParseException {

		ResultSet rs = null;
		Connection conn = SQLConn.getConnectionSql();
		ArrayList<CountList> resultCount = new ArrayList<CountList>();

		try {

			java.sql.Date begin = new java.sql.Date(beginDate.getTime());
			java.sql.Date end = new java.sql.Date(endDate.getTime());
			System.out.println("before exec sql");
			CallableStatement stmt = conn.prepareCall("{call multiResetPassADWeekly(?,?)}");
			stmt.setDate(1, (java.sql.Date) begin);
			stmt.setDate(2, (java.sql.Date) end);

			stmt.execute();
			rs = stmt.getResultSet();

			try {
				while (rs.next()) {
					Date a = rs.getDate(1);
					Date b = rs.getDate(2);
					int count = rs.getInt(3);
					String a_str = Helper.dateToStringWeek(a);
					String b_str = Helper.dateToStringWeek(b);
					CountList temp = new CountList(a_str,b_str,count);
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
	
	public ArrayList<Provision> getmultiResetPassADWeekly(Date beginDate, Date endDate) throws SQLException, ParseException {

		ResultSet rs = null;
		Connection conn = SQLConn.getConnectionSql();
		ArrayList<Provision> userList = new ArrayList<Provision>();

		try {

			java.sql.Date begin = new java.sql.Date(beginDate.getTime());
			java.sql.Date end = new java.sql.Date(endDate.getTime());
			System.out.println("before exec sql");
			CallableStatement stmt = conn.prepareCall("{call getmultiResetPassADWeekly(?,?)}");
			stmt.setDate(1, (java.sql.Date) begin);
			stmt.setDate(2, (java.sql.Date) end);

			stmt.execute();
			rs = stmt.getResultSet();

			try {
				while (rs.next()) {
					Date a = rs.getDate(1);
					Date b = rs.getDate(2);
					String name = rs.getString(3);
					String a_str = Helper.dateToStringWeek(a);
					String b_str = Helper.dateToStringWeek(b);
					String[] arr = name.split(",");
					name = arr[1].substring(6);
					if (!(name.contains("failed") || name.contains(":"))) {
						Provision user = new Provision(a_str,b_str, name);
						userList.add(user);

					}
				}
			} finally {
				stmt.close();
			}

		} finally {
			conn.close();
		}

		return userList;
	}
	
	public ArrayList<Provision> getsingleResetPassADWeekly(Date beginDate, Date endDate) throws SQLException, ParseException {

		ResultSet rs = null;
		Connection conn = SQLConn.getConnectionSql();
		ArrayList<Provision> userList = new ArrayList<Provision>();

		try {

			java.sql.Date begin = new java.sql.Date(beginDate.getTime());
			java.sql.Date end = new java.sql.Date(endDate.getTime());
			System.out.println("before exec sql");
			CallableStatement stmt = conn.prepareCall("{call getsingleResetPassADWeekly(?,?)}");
			stmt.setDate(1, (java.sql.Date) begin);
			stmt.setDate(2, (java.sql.Date) end);

			stmt.execute();
			rs = stmt.getResultSet();

			try {
				while (rs.next()) {
					Date a = rs.getDate(1);
					Date b = rs.getDate(2);
					String name = rs.getString(3);
					String a_str = Helper.dateToStringWeek(a);
					String b_str = Helper.dateToStringWeek(b);
					String[] arr = name.split(",");
					name = arr[1].substring(6);
					if (!(name.contains("failed") || name.contains(":"))) {
						Provision user = new Provision(a_str,b_str, name);
						userList.add(user);

					}
				}
			} finally {
				stmt.close();
			}

		} finally {
			conn.close();
		}

		return userList;
	}
	public ArrayList<Provision> getmultiResetPassADMonthly(Date beginDate, Date endDate)
			throws SQLException, ParseException {

		ResultSet rs = null;
		Connection conn = SQLConn.getConnectionSql();
		ArrayList<Provision> userList = new ArrayList<Provision>();
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(beginDate);
			int month_begin = beginDate.getMonth();
			int month_end = endDate.getMonth();
			int year_begin = cal.get(Calendar.YEAR);
			cal.setTime(endDate);
			int year_end = cal.get(Calendar.YEAR);
			System.out.println(month_begin + 1);
			System.out.println(month_end + 1);
			System.out.println("before exec sql");
			CallableStatement stmt = conn.prepareCall("{call getmultiResetPassADMonthly(?,?,?,?)}");
			stmt.setInt(1, month_begin + 1);
			stmt.setInt(2, year_begin);
			stmt.setInt(3, month_end + 1);
			stmt.setInt(4, year_end);
			stmt.execute();
			rs = stmt.getResultSet();

			try {
				while (rs.next()) {
					int year = rs.getInt(1);
					int month = rs.getInt(2);
					String name = rs.getString(3);
					String[] arr = name.split(",");
					name = arr[1].substring(6);
					if (!(name.contains("failed") || name.contains(":"))) {
						Provision user = new Provision(name,month,year);
						userList.add(user);

					}

				}
			} finally {
				stmt.close();
			}

		} finally {
			conn.close();
		}
		return userList;

	}
	
	public ArrayList<Provision> getsingleResetPassADMonthly(Date beginDate, Date endDate)
			throws SQLException, ParseException {

		ResultSet rs = null;
		Connection conn = SQLConn.getConnectionSql();
		ArrayList<Provision> userList = new ArrayList<Provision>();
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(beginDate);
			int month_begin = beginDate.getMonth();
			int month_end = endDate.getMonth();
			int year_begin = cal.get(Calendar.YEAR);
			cal.setTime(endDate);
			int year_end = cal.get(Calendar.YEAR);
			System.out.println(month_begin + 1);
			System.out.println(month_end + 1);
			System.out.println("before exec sql");
			CallableStatement stmt = conn.prepareCall("{call getsingleResetPassADMonthly(?,?,?,?)}");
			stmt.setInt(1, month_begin + 1);
			stmt.setInt(2, year_begin);
			stmt.setInt(3, month_end + 1);
			stmt.setInt(4, year_end);
			stmt.execute();
			rs = stmt.getResultSet();

			try {
				while (rs.next()) {
					int year = rs.getInt(1);
					int month = rs.getInt(2);
					String name = rs.getString(3);
					String[] arr = name.split(",");
					name = arr[1].substring(6);
					if (!(name.contains("failed") || name.contains(":"))) {
						Provision user = new Provision(name,month,year);
						userList.add(user);

					}

				}
			} finally {
				stmt.close();
			}

		} finally {
			conn.close();
		}
		return userList;

	}
	
	public ArrayList<Provision> getmultiResetPassADDaily(Date beginDate, Date endDate)
			throws SQLException, ParseException {

		ResultSet rs = null;
		Connection conn = SQLConn.getConnectionSql();
		ArrayList<Provision> userList = new ArrayList<Provision>();
		try {
			java.sql.Date begin = new java.sql.Date(beginDate.getTime());
			java.sql.Date end = new java.sql.Date(endDate.getTime());

			CallableStatement stmt = conn.prepareCall("{call getmultiResetPassADDaily(?,?)}");
			stmt.setDate(1, (java.sql.Date) begin);
			stmt.setDate(2, (java.sql.Date) end);

			stmt.execute();
			rs = stmt.getResultSet();

			try {
				while (rs.next()) {
					Date date = rs.getDate(1);
					if (date == null) {
						continue;
					}
					String date_str = Helper.dateToString(date);
					
					String name = rs.getString(2);
					String[] arr = name.split(",");
					name = arr[1].substring(6);
					if (!(name.contains("failed") || name.contains(":"))) {
						Provision user = new Provision(date_str,name);
						userList.add(user);

					}

				}
			} finally {
				stmt.close();
			}

		} finally {
			conn.close();
		}
		return userList;

	}
	
	public ArrayList<Provision> getsingleResetPassAppsDaily(Date beginDate, Date endDate, String apps)
			throws SQLException, ParseException {

		ResultSet rs = null;
		Connection conn = SQLConn.getConnectionSql();
		ArrayList<Provision> userList = new ArrayList<Provision>();
		try {
			java.sql.Date begin = new java.sql.Date(beginDate.getTime());
			java.sql.Date end = new java.sql.Date(endDate.getTime());
			System.out.println("before exec sql");
			String appsName = "%" +apps+"%";
			String query ="% - Reset Password%";
			if(apps.equalsIgnoreCase("MMS")) {
				query = "% - RESET PASSWORD%";
			}
			CallableStatement stmt = conn.prepareCall("{call getsingleResetPassAppsDaily(?,?,?,?)}");
			stmt.setDate(1, (java.sql.Date) begin);
			stmt.setDate(2, (java.sql.Date) end);
			stmt.setString(3,appsName);
			stmt.setString(4, query);

			stmt.execute();
			rs = stmt.getResultSet();

			try {
				while (rs.next()) {
					Date date = rs.getDate(1);
					if (date == null) {
						continue;
					}
					String date_str = Helper.dateToString(date);
					
					String name = rs.getString(2);
					name = name.substring(name.indexOf("Assign user") + 13, name.indexOf("provisioning role")-2);
					if (!(name.contains("failed") || name.contains(":"))) {
						Provision user = new Provision(date_str,name);
						userList.add(user);

					}

				}
			} finally {
				stmt.close();
			}

		} finally {
			conn.close();
		}
		return userList;

	}
	public ArrayList<Provision> getmultiResetPassAppsDaily(Date beginDate, Date endDate, String apps)
			throws SQLException, ParseException {

		ResultSet rs = null;
		Connection conn = SQLConn.getConnectionSql();
		ArrayList<Provision> userList = new ArrayList<Provision>();
		try {
			java.sql.Date begin = new java.sql.Date(beginDate.getTime());
			java.sql.Date end = new java.sql.Date(endDate.getTime());
			System.out.println("before exec sql");
			String appsName = "%" +apps+"%";
			String query ="% - Reset Password%";
			if(apps.equalsIgnoreCase("MMS")) {
				query = "% - RESET PASSWORD%";
			}
			CallableStatement stmt = conn.prepareCall("{call getmultiResetPassAppsDaily(?,?,?,?)}");
			stmt.setDate(1, (java.sql.Date) begin);
			stmt.setDate(2, (java.sql.Date) end);
			stmt.setString(3,appsName);
			stmt.setString(4, query);

			stmt.execute();
			rs = stmt.getResultSet();

			try {
				while (rs.next()) {
					Date date = rs.getDate(1);
					if (date == null) {
						continue;
					}
					String date_str = Helper.dateToString(date);
					
					String name = rs.getString(2);
					name = name.substring(name.indexOf("Assign user") + 13, name.indexOf("provisioning role")-2);
					if (!(name.contains("failed") || name.contains(":"))) {
						Provision user = new Provision(date_str,name);
						userList.add(user);

					}

				}
			} finally {
				stmt.close();
			}

		} finally {
			conn.close();
		}
		return userList;

	}
	
	public ArrayList<Provision> getsingleResetPassAPPWeekly(Date beginDate, Date endDate, String apps)
			throws SQLException, ParseException {

		ResultSet rs = null;
		Connection conn = SQLConn.getConnectionSql();
		ArrayList<Provision> userList = new ArrayList<Provision>();
		try {
			java.sql.Date begin = new java.sql.Date(beginDate.getTime());
			java.sql.Date end = new java.sql.Date(endDate.getTime());
			System.out.println("before exec sql");
			String appsName = "%" +apps+"%";
			String query ="% - Reset Password%";
			if(apps.equalsIgnoreCase("MMS")) {
				query = "% - RESET PASSWORD%";
			}
			CallableStatement stmt = conn.prepareCall("{call getsingleResetPassAPPWeekly(?,?,?,?)}");
			stmt.setDate(1, (java.sql.Date) begin);
			stmt.setDate(2, (java.sql.Date) end);
			stmt.setString(3,appsName);
			stmt.setString(4, query);

			stmt.execute();
			rs = stmt.getResultSet();

			try {
				while (rs.next()) {
					Date a = rs.getDate(1);
					Date b = rs.getDate(2);
					String name = rs.getString(3);
					String a_str = Helper.dateToStringWeek(a);
					String b_str = Helper.dateToStringWeek(b);
					name = name.substring(name.indexOf("Assign user") + 13, name.indexOf("provisioning role")-2);
					if (!(name.contains("failed") || name.contains(":"))) {
						Provision user = new Provision(a_str,b_str, name);
						userList.add(user);

					}

				}
			} finally {
				stmt.close();
			}

		} finally {
			conn.close();
		}
		return userList;

	}
	
	public ArrayList<Provision> getmultiResetPassAPPWeekly(Date beginDate, Date endDate, String apps)
			throws SQLException, ParseException {

		ResultSet rs = null;
		Connection conn = SQLConn.getConnectionSql();
		ArrayList<Provision> userList = new ArrayList<Provision>();
		try {
			java.sql.Date begin = new java.sql.Date(beginDate.getTime());
			java.sql.Date end = new java.sql.Date(endDate.getTime());
			System.out.println("before exec sql");
			String appsName = "%" +apps+"%";
			String query ="% - Reset Password%";
			if(apps.equalsIgnoreCase("MMS")) {
				query = "% - RESET PASSWORD%";
			}
			CallableStatement stmt = conn.prepareCall("{call getmultiResetPassAPPWeekly(?,?,?,?)}");
			stmt.setDate(1, (java.sql.Date) begin);
			stmt.setDate(2, (java.sql.Date) end);
			stmt.setString(3,appsName);
			stmt.setString(4, query);

			stmt.execute();
			rs = stmt.getResultSet();

			try {
				while (rs.next()) {
					Date a = rs.getDate(1);
					Date b = rs.getDate(2);
					String name = rs.getString(3);
					String a_str = Helper.dateToStringWeek(a);
					String b_str = Helper.dateToStringWeek(b);
					name = name.substring(name.indexOf("Assign user") + 13, name.indexOf("provisioning role")-2);
					if (!(name.contains("failed") || name.contains(":"))) {
						Provision user = new Provision(a_str,b_str, name);
						userList.add(user);

					}

				}
			} finally {
				stmt.close();
			}

		} finally {
			conn.close();
		}
		return userList;

	}
	
	public ArrayList<Provision> getsingleResetPassAPPMonthly(Date beginDate, Date endDate, String apps)
			throws SQLException, ParseException {

		ResultSet rs = null;
		Connection conn = SQLConn.getConnectionSql();
		ArrayList<Provision> userList = new ArrayList<Provision>();
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(beginDate);
			int month_begin = beginDate.getMonth();
			int month_end = endDate.getMonth();
			int year_begin = cal.get(Calendar.YEAR);
			cal.setTime(endDate);
			int year_end = cal.get(Calendar.YEAR);
			System.out.println(month_begin+1);
			System.out.println(month_end+1);
			System.out.println("before exec sql");
			
			System.out.println("before exec sql");
			String appsName = "%" +apps+"%";
			String query ="% - Reset Password%";
			if(apps.equalsIgnoreCase("MMS")) {
				query = "% - RESET PASSWORD%";
			}
			CallableStatement stmt = conn.prepareCall("{call getsingleResetPassAPPMonthly(?,?,?,?,?,?)}");

			stmt.setInt(1, month_begin+1);
			stmt.setInt(2, year_begin);
			stmt.setInt(3, month_end+1);
			stmt.setInt(4, year_end);
			stmt.setString(5, appsName);
			stmt.setString(6, query);
			
			stmt.execute();
			rs = stmt.getResultSet();

			try {
				while (rs.next()) {
					int year = rs.getInt(1);
					int month = rs.getInt(2);
					String name = rs.getString(3);
					name = name.substring(name.indexOf("Assign user") + 13, name.indexOf("provisioning role")-2);
					if (!(name.contains("failed") || name.contains(":"))) {
						Provision user = new Provision(name,month,year);
						userList.add(user);

					}

				}
			} finally {
				stmt.close();
			}

		} finally {
			conn.close();
		}
		return userList;

	}
	
	public ArrayList<Provision> getmultiResetPassAPPMonthly(Date beginDate, Date endDate, String apps)
			throws SQLException, ParseException {

		ResultSet rs = null;
		Connection conn = SQLConn.getConnectionSql();
		ArrayList<Provision> userList = new ArrayList<Provision>();
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(beginDate);
			int month_begin = beginDate.getMonth();
			int month_end = endDate.getMonth();
			int year_begin = cal.get(Calendar.YEAR);
			cal.setTime(endDate);
			int year_end = cal.get(Calendar.YEAR);
			System.out.println(month_begin+1);
			System.out.println(month_end+1);
			System.out.println("before exec sql");
			
			System.out.println("before exec sql");
			String appsName = "%" +apps+"%";
			String query ="% - Reset Password%";
			if(apps.equalsIgnoreCase("MMS")) {
				query = "% - RESET PASSWORD%";
			}
			CallableStatement stmt = conn.prepareCall("{call getmultiResetPassAPPMonthly(?,?,?,?,?,?)}");

			stmt.setInt(1, month_begin+1);
			stmt.setInt(2, year_begin);
			stmt.setInt(3, month_end+1);
			stmt.setInt(4, year_end);
			stmt.setString(5, appsName);
			stmt.setString(6, query);
			
			stmt.execute();
			rs = stmt.getResultSet();

			try {
				while (rs.next()) {
					int year = rs.getInt(1);
					int month = rs.getInt(2);
					String name = rs.getString(3);
					name = name.substring(name.indexOf("Assign user") + 13, name.indexOf("provisioning role")-2);
					if (!(name.contains("failed") || name.contains(":"))) {
						Provision user = new Provision(name,month,year);
						userList.add(user);

					}

				}
			} finally {
				stmt.close();
			}

		} finally {
			conn.close();
		}
		return userList;

	}
	
	public ArrayList<Provision> getsingleResetPassADDaily(Date beginDate, Date endDate)
			throws SQLException, ParseException {

		ResultSet rs = null;
		Connection conn = SQLConn.getConnectionSql();
		ArrayList<Provision> userList = new ArrayList<Provision>();
		try {
			java.sql.Date begin = new java.sql.Date(beginDate.getTime());
			java.sql.Date end = new java.sql.Date(endDate.getTime());

			CallableStatement stmt = conn.prepareCall("{call getsingleResetPassADDaily(?,?)}");
			stmt.setDate(1, (java.sql.Date) begin);
			stmt.setDate(2, (java.sql.Date) end);

			stmt.execute();
			rs = stmt.getResultSet();

			try {
				while (rs.next()) {
					Date date = rs.getDate(1);
					if (date == null) {
						continue;
					}
					String date_str = Helper.dateToString(date);
					
					String name = rs.getString(2);
					String[] arr = name.split(",");
					name = arr[1].substring(6);
					if (!(name.contains("failed") || name.contains(":"))) {
						Provision user = new Provision(date_str,name);
						userList.add(user);

					}

				}
			} finally {
				stmt.close();
			}

		} finally {
			conn.close();
		}
		return userList;

	}
	
	public ArrayList<CountList> multiResetPassAPPMonthly(Date beginDate, Date endDate)
			throws SQLException, ParseException {

		ResultSet rs = null;
		Connection conn = SQLConn.getConnectionSql();
		ArrayList<CountList> resultCount = new ArrayList<CountList>();
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(beginDate);
			int month_begin = beginDate.getMonth();
			int month_end = endDate.getMonth();
			int year_begin = cal.get(Calendar.YEAR);
			cal.setTime(endDate);
			int year_end = cal.get(Calendar.YEAR);
			System.out.println(month_begin + 1);
			System.out.println(month_end + 1);
			System.out.println("before exec sql");
			CallableStatement stmt = conn.prepareCall("{call singleResetPassADMonthly(?,?,?,?)}");
			stmt.setInt(1, month_begin + 1);
			stmt.setInt(2, year_begin);
			stmt.setInt(3, month_end + 1);
			stmt.setInt(4, year_end);
			stmt.execute();
			rs = stmt.getResultSet();

			try {
				while (rs.next()) {
					int year = rs.getInt(1);
					int month = rs.getInt(2);
					int countPass = rs.getInt(3);
					CountList temp = new CountList(countPass, month, year);
					resultCount.add(temp);

				}
			} finally {
				stmt.close();
			}

		} finally {
			conn.close();
		}
		return resultCount;

	}
	
	public ArrayList<CountList> multiResetPassAPPMonthly(Date beginDate, Date endDate, String apps)
			throws SQLException, ParseException {

		ResultSet rs = null;
		Connection conn = SQLConn.getConnectionSql();
		ArrayList<CountList> resultCount = new ArrayList<CountList>();
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(beginDate);
			int month_begin = beginDate.getMonth();
			int month_end = endDate.getMonth();
			int year_begin = cal.get(Calendar.YEAR);
			cal.setTime(endDate);
			int year_end = cal.get(Calendar.YEAR);
			System.out.println(month_begin + 1);
			System.out.println(month_end + 1);
			System.out.println("before exec sql");
			String appsName = "%" +apps+"%";
			String query ="% - Reset Password%";
			if(apps.equalsIgnoreCase("MMS")) {
				query = "% - RESET PASSWORD%";
			}
			CallableStatement stmt = conn.prepareCall("{call multiResetPassAPPMonthly(?,?,?,?,?,?)}");
			stmt.setInt(1, month_begin + 1);
			stmt.setInt(2, year_begin);
			stmt.setInt(3, month_end + 1);
			stmt.setInt(4, year_end);
			stmt.setString(5, appsName);
			stmt.setString(6, query);
			stmt.execute();
			rs = stmt.getResultSet();

			try {
				while (rs.next()) {
					int year = rs.getInt(1);
					int month = rs.getInt(2);
					int countPass = rs.getInt(3);
					CountList temp = new CountList(countPass, month, year,apps);
					resultCount.add(temp);

				}
			} finally {
				stmt.close();
			}

		} finally {
			conn.close();
		}
		return resultCount;

	}
	
	public ArrayList<CountList> singleResetPassAPPMonthly(Date beginDate, Date endDate, String apps)
			throws SQLException, ParseException {

		ResultSet rs = null;
		Connection conn = SQLConn.getConnectionSql();
		ArrayList<CountList> resultCount = new ArrayList<CountList>();
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(beginDate);
			int month_begin = beginDate.getMonth();
			int month_end = endDate.getMonth();
			int year_begin = cal.get(Calendar.YEAR);
			cal.setTime(endDate);
			int year_end = cal.get(Calendar.YEAR);
			System.out.println(month_begin + 1);
			System.out.println(month_end + 1);
			System.out.println("before exec sql");
			String appsName = "%" +apps+"%";
			String query ="% - Reset Password%";
			if(apps.equalsIgnoreCase("MMS")) {
				query = "% - RESET PASSWORD%";
			}
			CallableStatement stmt = conn.prepareCall("{call singleResetPassAPPMonthly(?,?,?,?,?,?)}");
			stmt.setInt(1, month_begin + 1);
			stmt.setInt(2, year_begin);
			stmt.setInt(3, month_end + 1);
			stmt.setInt(4, year_end);
			stmt.setString(5, appsName);
			stmt.setString(6, query);
			stmt.execute();
			rs = stmt.getResultSet();

			try {
				while (rs.next()) {
					int year = rs.getInt(1);
					int month = rs.getInt(2);
					int countPass = rs.getInt(3);
					CountList temp = new CountList(countPass, month, year,apps);
					resultCount.add(temp);

				}
			} finally {
				stmt.close();
			}

		} finally {
			conn.close();
		}
		return resultCount;

	}

}
