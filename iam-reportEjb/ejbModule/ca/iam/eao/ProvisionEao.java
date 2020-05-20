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

public class ProvisionEao {
	
	public ArrayList<Provision> getProvMonthly(Date begin_date, Date end_date, String appsName)
			throws SQLException, ParseException {

		ResultSet rs = null;
		Connection conn = SQLConn.getConnectionSql();
		ArrayList<Provision> userList = new ArrayList<Provision>();
		Provision user = null;
		System.out.println("before try catch");
		try {

			Calendar cal = Calendar.getInstance();
			cal.setTime(begin_date);
			int month_begin = begin_date.getMonth();
			int month_end = end_date.getMonth();
			int year_begin = cal.get(Calendar.YEAR);
			cal.setTime(end_date);
			int year_end = cal.get(Calendar.YEAR);
			CallableStatement stmt = conn.prepareCall("{call getProvMonthly(?,?,?,?,?)}");
			stmt.setInt(1, month_begin + 1);
			stmt.setInt(2, year_begin);
			stmt.setInt(3, month_end + 1);
			stmt.setInt(4, year_end);
			stmt.setString(5, "%"+appsName+"%");

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
						user = new Provision( name,month,year,appsName);
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
	
	
	public ArrayList<Provision> getProvMonthlyApps(Date begin_date, Date end_date, String appsName)
			throws SQLException, ParseException {

		ResultSet rs = null;
		Connection conn = SQLConn.getConnectionSql();
		ArrayList<Provision> userList = new ArrayList<Provision>();
		Provision user = null;
		System.out.println("before try catch");
		try {

			Calendar cal = Calendar.getInstance();
			cal.setTime(begin_date);
			int month_begin = begin_date.getMonth();
			int month_end = end_date.getMonth();
			int year_begin = cal.get(Calendar.YEAR);
			cal.setTime(end_date);
			int year_end = cal.get(Calendar.YEAR);
			CallableStatement stmt = conn.prepareCall("{call getProvMonthlyApps(?,?,?,?,?)}");
			stmt.setInt(1, month_begin + 1);
			stmt.setInt(2, year_begin);
			stmt.setInt(3, month_end + 1);
			stmt.setInt(4, year_end);
			stmt.setString(5, "%"+appsName+"%");

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
						user = new Provision( name,month,year,appsName);
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
	
	
	public ArrayList<Provision> getProvWeeklyApps(Date begin_date, Date end_date,String apps) throws SQLException, ParseException {

		ResultSet rs = null;
		Connection conn = SQLConn.getConnectionSql();
		ArrayList<Provision> userList = new ArrayList<Provision>();

		try {

			java.sql.Date begin = new java.sql.Date(begin_date.getTime());
			java.sql.Date end = new java.sql.Date(end_date.getTime());
			System.out.println("before exec sql");
			CallableStatement stmt = conn.prepareCall("{call getProvWeeklyApps(?,?,?)}");
			stmt.setDate(1, (java.sql.Date) begin);
			stmt.setDate(2, (java.sql.Date) end);
			stmt.setString(3, "%"+apps+"%");

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
						Provision user = new Provision(a_str,b_str, name,apps);
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
	
	public ArrayList<Provision> getProvWeekly(Date begin_date, Date end_date,String apps) throws SQLException, ParseException {

		ResultSet rs = null;
		Connection conn = SQLConn.getConnectionSql();
		ArrayList<Provision> userList = new ArrayList<Provision>();

		try {

			java.sql.Date begin = new java.sql.Date(begin_date.getTime());
			java.sql.Date end = new java.sql.Date(end_date.getTime());
			System.out.println("before exec sql");
			CallableStatement stmt = conn.prepareCall("{call getProvWeekly(?,?,?)}");
			stmt.setDate(1, (java.sql.Date) begin);
			stmt.setDate(2, (java.sql.Date) end);
			stmt.setString(3, "%"+apps+"%");

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
						Provision user = new Provision(a_str,b_str, name,apps);
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
	public ArrayList<CountList> countProvWeekly(Date begin_date, Date end_date, String apps) throws SQLException, ParseException {

		ResultSet rs = null;
		Connection conn = SQLConn.getConnectionSql();
		ArrayList<CountList> resultCount = new ArrayList<CountList>();

		try {

			java.sql.Date begin = new java.sql.Date(begin_date.getTime());
			java.sql.Date end = new java.sql.Date(end_date.getTime());
			CallableStatement stmt = conn.prepareCall("{call countProvWeekly(?,?,?)}");
			stmt.setDate(1, (java.sql.Date) begin);
			stmt.setDate(2, (java.sql.Date) end);
			stmt.setString(3, "%"+apps+"%");

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
	
	public ArrayList<CountList> countProvWeeklyApps(Date begin_date, Date end_date, String apps) throws SQLException, ParseException {

		ResultSet rs = null;
		Connection conn = SQLConn.getConnectionSql();
		ArrayList<CountList> resultCount = new ArrayList<CountList>();

		try {

			java.sql.Date begin = new java.sql.Date(begin_date.getTime());
			java.sql.Date end = new java.sql.Date(end_date.getTime());
			CallableStatement stmt = conn.prepareCall("{call countProvWeeklyApps(?,?,?)}");
			stmt.setDate(1, (java.sql.Date) begin);
			stmt.setDate(2, (java.sql.Date) end);
			stmt.setString(3, "%"+apps+"%");

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
	public ArrayList<CountList> countProvision(Date begin_date, Date end_date, String apps) throws SQLException, ParseException {

		ResultSet rs = null;
		Connection conn = SQLConn.getConnectionSql();
		ArrayList<CountList> resultCount = new ArrayList<CountList>();

		try {

			java.sql.Date begin = new java.sql.Date(begin_date.getTime());
			java.sql.Date end = new java.sql.Date(end_date.getTime());
			CallableStatement stmt = conn.prepareCall("{call countProvision(?,?,?)}");
			stmt.setDate(1, (java.sql.Date) begin);
			stmt.setDate(2, (java.sql.Date) end);
			stmt.setString(3, "%"+apps+"%");

			stmt.execute();
			rs = stmt.getResultSet();

			try {
				while (rs.next()) {
					
					Date date = rs.getDate(1);
					int count = rs.getInt(2);
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
		System.out.println(resultCount.size());

		return resultCount;
	}
	
	public ArrayList<CountList> countProvisionApps(Date begin_date, Date end_date, String apps) throws SQLException, ParseException {

		ResultSet rs = null;
		Connection conn = SQLConn.getConnectionSql();
		ArrayList<CountList> resultCount = new ArrayList<CountList>();

		try {

			java.sql.Date begin = new java.sql.Date(begin_date.getTime());
			java.sql.Date end = new java.sql.Date(end_date.getTime());
			CallableStatement stmt = conn.prepareCall("{call countProvisionApps(?,?,?)}");
			stmt.setDate(1, (java.sql.Date) begin);
			stmt.setDate(2, (java.sql.Date) end);
			stmt.setString(3, "%"+apps+"%");

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
		System.out.println(resultCount.size());

		return resultCount;
	}
	public ArrayList<Provision> getProvisionData(Date begin_date, Date end_date, String apps)
			throws SQLException, ParseException {

		ResultSet rs = null;
		Connection conn = SQLConn.getConnectionSql();
		ArrayList<Provision> userList = new ArrayList<Provision>();
		Provision prov = new Provision();

		try {

			java.sql.Date begin = new java.sql.Date(begin_date.getTime());
			java.sql.Date end = new java.sql.Date(end_date.getTime());

			CallableStatement stmt = conn.prepareCall("{call getProvisionData(?,?,?)}");
			stmt.setDate(1, (java.sql.Date) begin);
			stmt.setDate(2, (java.sql.Date) end);
			stmt.setString(3, "%" + apps + "%");

			stmt.execute();
			rs = stmt.getResultSet();

			try {
				if (rs != null) {
					while (rs.next()) {
						Date date = rs.getDate(1);
						if (date == null) {
							continue;
						}
						String date_str = Helper.dateToString(date);
						String type = rs.getString(2);
						String[] arr = type.split(",");
						type = arr[1].substring(6);
						if (!(type.contains("failed") || type.contains(":"))) {
							prov = new Provision(date_str, type, apps,true);
							userList.add(prov);
						}
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
	
	public ArrayList<Provision> getProvisionDataApps(Date begin_date, Date end_date, String apps)
			throws SQLException, ParseException {

		ResultSet rs = null;
		Connection conn = SQLConn.getConnectionSql();
		ArrayList<Provision> userList = new ArrayList<Provision>();
		Provision prov = new Provision();

		try {

			java.sql.Date begin = new java.sql.Date(begin_date.getTime());
			java.sql.Date end = new java.sql.Date(end_date.getTime());

			CallableStatement stmt = conn.prepareCall("{call getProvisionDataApps(?,?,?)}");
			stmt.setDate(1, (java.sql.Date) begin);
			stmt.setDate(2, (java.sql.Date) end);
			stmt.setString(3, "%" + apps + "%");

			stmt.execute();
			rs = stmt.getResultSet();

			try {
				if (rs != null) {
					while (rs.next()) {
						Date date = rs.getDate(1);
						if (date == null) {
							continue;
						}
						String date_str = Helper.dateToString(date);
						String type = rs.getString(2);
						String[] arr = type.split(",");
						type = arr[1].substring(6);
						if (!(type.contains("failed") || type.contains(":"))) {
							prov = new Provision(date_str, type, apps,true);
							userList.add(prov);
						}
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


}
