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
import ca.iam.util.Helper;
import ca.iam.util.SQLConn;
import ca.iam.util.Settings;
import ca.iam.util.ReportConn;

@Stateless
@LocalBean
@Named

public class DtobmEao {

	public ArrayList<CountList> countProvDTOBM(Date begin_date, Date end_date) throws SQLException, ParseException {

		ResultSet rs = null;
		Connection conn = SQLConn.getConnectionSql();
		ArrayList<CountList> resultCount = new ArrayList<CountList>();
		try {

			java.sql.Date begin = new java.sql.Date(begin_date.getTime());
			java.sql.Date end = new java.sql.Date(end_date.getTime());

			CallableStatement stmt = conn.prepareCall("{call countProvDTOBM(?,?)}");
			stmt.setDate(1, (java.sql.Date) begin);
			stmt.setDate(2, (java.sql.Date) end);

			stmt.execute();
			rs = stmt.getResultSet();

			try {
				while (rs.next()) {
					int count = rs.getInt(2);
					Date date = rs.getDate(1);
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

	public ArrayList<Provision> getProvisionData(Date begin_date, Date end_date) throws SQLException, ParseException {

		ResultSet rs = null;
		Connection conn = SQLConn.getConnectionSql();
		ArrayList<Provision> userList = new ArrayList<Provision>();
		Provision prov = new Provision();

		try {

			java.sql.Date begin = new java.sql.Date(begin_date.getTime());
			java.sql.Date end = new java.sql.Date(end_date.getTime());

			CallableStatement stmt = conn.prepareCall("{call getProvDTOBM(?,?)}");
			stmt.setDate(1, (java.sql.Date) begin);
			stmt.setDate(2, (java.sql.Date) end);

			stmt.execute();
			rs = stmt.getResultSet();

			try {
				while (rs.next()) {
					Date date = rs.getDate(1);
					String date_str = Helper.dateToString(date);
					String type = rs.getString(2).substring(39);
					if (!(type.contains("failed") || type.contains(":"))) {
						prov = new Provision(date_str, type);
						userList.add(prov);
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

	
	public ArrayList<UserUpdates> getUserUpdate(Date begin_date, Date end_date) throws SQLException, ParseException {

		ResultSet rs = null;
		Connection conn = ReportConn.getConnection();
		UserUpdates user_updates = new UserUpdates();
		ArrayList<UserUpdates> userList = new ArrayList<UserUpdates>();

		try {

			java.sql.Date begin = new java.sql.Date(begin_date.getTime());
			java.sql.Date end = new java.sql.Date(end_date.getTime());

			CallableStatement stmt = conn.prepareCall("{call getDtobmUpdates(?,?)}");
			stmt.setDate(1, (java.sql.Date) begin);
			stmt.setDate(2, (java.sql.Date) end);

			stmt.execute();
			rs = stmt.getResultSet();

			try {
				while (rs.next()) {
					String full_name = rs.getString("full_name");
					String user_id = rs.getString("user_id");
					String login_id = rs.getString("login_id");
					String first_name = rs.getString("first_name");
					String middle_name = rs.getString("middle_name");
					String last_name = rs.getString("last_name");
					Date activation_date = null;
					if (rs.getDate("activation_date") != null)
						activation_date = rs.getDate("activation_date");
					String string_05 = rs.getString("string_05");
					Date expiration_date = null;
					if (rs.getDate("expiration_date") != null)
						expiration_date = rs.getDate("expiration_date");
					String employee_status = rs.getString("employee_status");
					String manager = rs.getString("manager");
					String manager_employee_number = rs.getString("manager_employee_number");
					String integer_04 = rs.getString("integer_04");
					String alternate_email = rs.getString("alternate_email");
					String mobile_phone = rs.getString("mobile_phone");
					String string_00 = rs.getString("string_00");
					String strng_01 = rs.getString("string_01");
					String department = rs.getString("department");
					String string_02 = rs.getString("string_02");
					String case_exact_string04 = rs.getString("case_exact_string04");
					String cost_center = rs.getString("cost_center");
					String case_exact_string03 = rs.getString("case_exact_string03");
					String string_03 = rs.getString("string_03");
					String case_exact_string02 = rs.getString("case_exact_string02");
					String string_04 = rs.getString("string_04");
					String case_exact_string01 = rs.getString("case_exact_string01");
					String string_08 = rs.getString("string_08");
					String case_exact_string00 = rs.getString("case_exact_string00");
					String string_09 = rs.getString("string_09");
					String string_06 = rs.getString("string_06");
					String string_07 = rs.getString("string_07");
					String employee_type = rs.getString("employee_type");
					Boolean enable = rs.getBoolean("enable");
					Date last_update = null;
					if (rs.getDate("last_update") != null)
						last_update = rs.getDate("last_update");
					int is_onboard = rs.getInt("is_onboard");
					int is_update = rs.getInt("is_update");
					String updated_attr = rs.getString("updated_attr");
					int upd_success = rs.getInt("upd_success");
					user_updates = new UserUpdates(full_name, user_id, login_id, first_name, middle_name, last_name,
							activation_date, string_05, expiration_date, employee_status, manager,
							manager_employee_number, integer_04, alternate_email, mobile_phone, string_00, strng_01,
							department, string_02, case_exact_string04, cost_center, case_exact_string03, string_03,
							case_exact_string02, string_04, case_exact_string01, string_08, case_exact_string00,
							string_09, string_06, string_07, employee_type, enable, last_update, is_onboard, is_update,
							updated_attr, upd_success);
					userList.add(user_updates);
				}
			} finally {
				stmt.close();
			}
		} finally {
			conn.close();
		}

		return userList;
	}

	public ArrayList<UserUpdates> getUserDisabled(Date begin_date, Date end_date) throws SQLException, ParseException {

		ResultSet rs = null;
		Connection conn = ReportConn.getConnection();
		UserUpdates user_updates = new UserUpdates();
		ArrayList<UserUpdates> userList = new ArrayList<UserUpdates>();

		try {

			java.sql.Date begin = new java.sql.Date(begin_date.getTime());
			java.sql.Date end = new java.sql.Date(end_date.getTime());

			CallableStatement stmt = conn.prepareCall("{call getDtobmDisabled(?,?)}");
			stmt.setDate(1, (java.sql.Date) begin);
			stmt.setDate(2, (java.sql.Date) end);

			stmt.execute();
			rs = stmt.getResultSet();

			try {
				while (rs.next()) {
					String full_name = rs.getString("full_name");
					String user_id = rs.getString("user_id");
					String login_id = rs.getString("login_id");
					String first_name = rs.getString("first_name");
					String middle_name = rs.getString("middle_name");
					String last_name = rs.getString("last_name");
					Date activation_date = null;
					if (rs.getDate("activation_date") != null)
						activation_date = rs.getDate("activation_date");
					String string_05 = rs.getString("string_05");
					Date expiration_date = null;
					if (rs.getDate("expiration_date") != null)
						expiration_date = rs.getDate("expiration_date");
					String employee_status = rs.getString("employee_status");
					String manager = rs.getString("manager");
					String manager_employee_number = rs.getString("manager_employee_number");
					String integer_04 = rs.getString("integer_04");
					String alternate_email = rs.getString("alternate_email");
					String mobile_phone = rs.getString("mobile_phone");
					String string_00 = rs.getString("string_00");
					String strng_01 = rs.getString("string_01");
					String department = rs.getString("department");
					String string_02 = rs.getString("string_02");
					String case_exact_string04 = rs.getString("case_exact_string04");
					String cost_center = rs.getString("cost_center");
					String case_exact_string03 = rs.getString("case_exact_string03");
					String string_03 = rs.getString("string_03");
					String case_exact_string02 = rs.getString("case_exact_string02");
					String string_04 = rs.getString("string_04");
					String case_exact_string01 = rs.getString("case_exact_string01");
					String string_08 = rs.getString("string_08");
					String case_exact_string00 = rs.getString("case_exact_string00");
					String string_09 = rs.getString("string_09");
					String string_06 = rs.getString("string_06");
					String string_07 = rs.getString("string_07");
					String employee_type = rs.getString("employee_type");
					Boolean enable = rs.getBoolean("enable");
					Date last_update = null;
					if (rs.getDate("last_update") != null)
						last_update = rs.getDate("last_update");
					int is_onboard = rs.getInt("is_onboard");
					int is_update = rs.getInt("is_update");
					String updated_attr = rs.getString("updated_attr");
					int upd_success = rs.getInt("upd_success");
					user_updates = new UserUpdates(full_name, user_id, login_id, first_name, middle_name, last_name,
							activation_date, string_05, expiration_date, employee_status, manager,
							manager_employee_number, integer_04, alternate_email, mobile_phone, string_00, strng_01,
							department, string_02, case_exact_string04, cost_center, case_exact_string03, string_03,
							case_exact_string02, string_04, case_exact_string01, string_08, case_exact_string00,
							string_09, string_06, string_07, employee_type, enable, last_update, is_onboard, is_update,
							updated_attr, upd_success);
					userList.add(user_updates);
				}
			} finally {
				stmt.close();
			}
		} finally {
			conn.close();
		}

		return userList;
	}

	public ArrayList<Provision> getDeprovData(Date begin_date, Date end_date) throws SQLException, ParseException {

		ResultSet rs = null;
		Connection conn = SQLConn.getConnectionSql();
		ArrayList<Provision> userList = new ArrayList<Provision>();
		Provision prov = new Provision();

		try {

			java.sql.Date begin = new java.sql.Date(begin_date.getTime());
			java.sql.Date end = new java.sql.Date(end_date.getTime());

			CallableStatement stmt = conn.prepareCall("{call getDeprovData(?,?)}");
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
					String type = rs.getString(2);
					String[] arr = type.split(",");
					type = arr[1].substring(6);
					if (!(type.contains("failed") || type.contains(":"))) {
						if (type.charAt(0) == 'O') {
							prov = new Provision(date_str, type, "DTOBM");
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

	public ArrayList<Provision> getDeprovMonthly(Date begin_date, Date end_date) throws SQLException, ParseException {

		ResultSet rs = null;
		Connection conn = SQLConn.getConnectionSql();
		ArrayList<Provision> userList = new ArrayList<Provision>();
		Provision user = new Provision();
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(begin_date);
			int month_begin = begin_date.getMonth();
			int month_end = end_date.getMonth();
			int year_begin = cal.get(Calendar.YEAR);
			cal.setTime(end_date);
			int year_end = cal.get(Calendar.YEAR);
			System.out.println(month_begin + 1);
			System.out.println("before exec sql");
			CallableStatement stmt = conn.prepareCall("{call getDeprovMonthly(?,?,?,?)}");
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
					String desc = rs.getString(3);
					String[] arr = desc.split(",");
					desc = arr[1].substring(6);
					System.out.println("user dtobm " + desc);
					if (!(desc.contains("failed") || desc.contains(":"))) {
						if (desc.charAt(0) == 'O') {
							user = new Provision(desc, month, year, "DTOBM");
							userList.add(user);
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

	public ArrayList<CountList> countDeprovision(Date begin_date, Date end_date) throws SQLException, ParseException {

		ResultSet rs = null;
		Connection conn = SQLConn.getConnectionSql();
		ArrayList<CountList> resultCount = new ArrayList<CountList>();

		try {

			java.sql.Date begin = new java.sql.Date(begin_date.getTime());
			java.sql.Date end = new java.sql.Date(end_date.getTime());
			System.out.println("before exec sql");
			CallableStatement stmt = conn.prepareCall("{call countDeprovNonSAP(?,?,?)}");
			stmt.setDate(1, (java.sql.Date) begin);
			stmt.setDate(2, (java.sql.Date) end);
			stmt.setString(3, "%User O%");

			stmt.execute();
			rs = stmt.getResultSet();

			try {
				while (rs.next()) {
					int count = rs.getInt(1);
					Date date = rs.getDate(2);
					if (date == null) {
						continue;
					}
					String date_str = Helper.dateToString(date);
					CountList temp = new CountList(date_str, count, "DTOBM");
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

			System.out.println("before exec sql");
			CallableStatement stmt = conn.prepareCall("{call deprovMonthDTOBM(?,?,?,?)}");
			stmt.setInt(1, month_begin + 1);
			stmt.setInt(2, year_begin);
			stmt.setInt(3, month_end + 1);
			stmt.setInt(4, year_end);
			System.out.println(month_begin + 1);
			stmt.execute();
			rs = stmt.getResultSet();

			try {
				while (rs.next()) {
					int year = rs.getInt(1);
					int month = rs.getInt(2);
					int count = rs.getInt(3);
					System.out.println(month);
					System.out.println(year);
					String date_str = new DateFormatSymbols().getMonths()[month - 1] + " " + year;
					CountList temp = new CountList(date_str, count, "DTOBM");
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
					int countDTOBM = rs.getInt(4);
					String a_str = Helper.dateToStringWeek(a);
					String b_str = Helper.dateToStringWeek(b);
					CountList temp = new CountList(a_str, b_str, countDTOBM, "DTOBM");
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

	public ArrayList<CountList> countProvMonthly(Date begin_date, Date end_date) throws SQLException, ParseException {

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
			System.out.println(month_begin + 1);
			System.out.println("before exec sql");
			CallableStatement stmt = conn.prepareCall("{call countProvMonthlyDTOBM(?,?,?,?)}");
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
					int count = rs.getInt(3);
					System.out.println(month);
					System.out.println(year);
					String date_str = new DateFormatSymbols().getMonths()[month - 1] + " " + year;
					CountList temp = new CountList(date_str, count, "DTOBM");
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

	public ArrayList<Provision> getDeprovWeekly(Date begin_date, Date end_date) throws SQLException, ParseException {

		ResultSet rs = null;
		Connection conn = SQLConn.getConnectionSql();
		ArrayList<Provision> userList = new ArrayList<Provision>();
		Provision prov = new Provision();

		try {

			java.sql.Date begin = new java.sql.Date(begin_date.getTime());
			java.sql.Date end = new java.sql.Date(end_date.getTime());

			CallableStatement stmt = conn.prepareCall("{call getDeprovWeekly(?,?)}");
			stmt.setDate(1, (java.sql.Date) begin);
			stmt.setDate(2, (java.sql.Date) end);

			stmt.execute();
			rs = stmt.getResultSet();

			try {
				while (rs.next()) {
					Date a = rs.getDate(1);
					Date b = rs.getDate(2);
					String a_str = Helper.dateToStringWeek(a);
					String b_str = Helper.dateToStringWeek(b);
					String type = rs.getString(3);
					String[] arr = type.split(",");
					type = arr[1].substring(6);
					if (!(type.contains("failed") || type.contains(":"))) {
						if ((type.charAt(0) == 'O')) {
							prov = new Provision(a_str, b_str, type, "DTOBM");
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

	public ArrayList<CountList> countOnboardDailyNonSAP(Date begin_date, Date end_date, String type)
			throws SQLException, ParseException {

		ResultSet rs = null;
		Connection conn = SQLConn.getConnectionSql();
		ArrayList<CountList> resultCount = new ArrayList<CountList>();

		try {

			java.sql.Date begin = new java.sql.Date(begin_date.getTime());
			java.sql.Date end = new java.sql.Date(end_date.getTime());
			String query = "";
			if (type.equalsIgnoreCase("DTOBM")) {
				query = "%User O%";
			} else {
				query = "%User K%";
			}
			CallableStatement stmt = conn.prepareCall("{call countOnboardDailyNonSAP(?,?,?)}");
			stmt.setDate(1, (java.sql.Date) begin);
			stmt.setDate(2, (java.sql.Date) end);
			stmt.setString(3, query);

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

		return resultCount;
	}

	public ArrayList<CountList> countOnboardWeeklyNonSAP(Date begin_date, Date end_date, String type)
			throws SQLException, ParseException {

		ResultSet rs = null;
		Connection conn = SQLConn.getConnectionSql();
		ArrayList<CountList> resultCount = new ArrayList<CountList>();

		try {

			java.sql.Date begin = new java.sql.Date(begin_date.getTime());
			java.sql.Date end = new java.sql.Date(end_date.getTime());
			String query = "";
			if (type.equalsIgnoreCase("DTOBM")) {
				query = "%User O%";
			} else {
				query = "%User K%";
			}
			CallableStatement stmt = conn.prepareCall("{call countOnboardWeeklyNonSAP(?,?,?)}");
			stmt.setDate(1, (java.sql.Date) begin);
			stmt.setDate(2, (java.sql.Date) end);
			stmt.setString(3, query);

			stmt.execute();
			rs = stmt.getResultSet();

			try {
				while (rs.next()) {
					Date a = rs.getDate(1);
					Date b = rs.getDate(2);
					int count = rs.getInt(3);
					String a_str = Helper.dateToStringWeek(a);
					String b_str = Helper.dateToStringWeek(b);
					CountList temp = new CountList(a_str, b_str, count);
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

	public ArrayList<CountList> countOnboardMonthlyNonSAP(Date begin_date, Date end_date, String type)
			throws SQLException, ParseException {

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
			String query = "";
			if (type.equalsIgnoreCase("DTOBM")) {
				query = "%User O%";
			} else {
				query = "%User K%";
			}
			CallableStatement stmt = conn.prepareCall("{call countOnboardMonthlyNonSAP(?,?,?,?)}");
			stmt.setInt(1, month_begin + 1);
			stmt.setInt(2, year_begin);
			stmt.setInt(3, month_end + 1);
			stmt.setInt(4, year_end);
			stmt.setString(5, query);
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

	public ArrayList<UserModel> getUserOnboardMonthly(Date begin_date, Date end_date, String nonsap)
			throws SQLException, ParseException {

		ResultSet rs = null;
		Connection conn = SQLConn.getConnectionSql();
		ArrayList<UserModel> userListDTOBM = new ArrayList<UserModel>();
		ArrayList<UserModel> userListDTKBM = new ArrayList<UserModel>();

		try {

			Calendar cal = Calendar.getInstance();
			cal.setTime(begin_date);
			int month_begin = begin_date.getMonth();
			int month_end = end_date.getMonth();
			int year_begin = cal.get(Calendar.YEAR);
			cal.setTime(end_date);
			int year_end = cal.get(Calendar.YEAR);
			System.out.println(month_begin + 1);
			System.out.println(month_end + 1);
			System.out.println("before exec sql");

			CallableStatement stmt = conn.prepareCall("{call getUserOnboardMonthly(?,?,?,?)}");
			stmt.setInt(1, month_begin + 1);
			stmt.setInt(2, year_begin);
			stmt.setInt(3, month_end + 1);
			stmt.setInt(4, year_end);
			stmt.execute();
			rs = stmt.getResultSet();

			stmt.execute();
			rs = stmt.getResultSet();

			try {
				while (rs.next()) {
					int year = rs.getInt(1);
					int month = rs.getInt(2);
					String type = rs.getString(3);
					String[] arr = type.split(",");
					type = arr[1].substring(6);
					if (!(type.contains("failed") || type.contains(":"))) {

						if (nonsap.equalsIgnoreCase("DTOBM") && type.charAt(0) == 'O') {
							UserModel user = new UserModel(type, month, year);
							userListDTOBM.add(user);
						} else if (nonsap.equalsIgnoreCase("DTKBM") && type.charAt(0) == 'K') {
							UserModel user = new UserModel(type, month, year);
							userListDTKBM.add(user);
						}
					}

				}
			} finally {
				stmt.close();
			}
		} finally {
			conn.close();
		}
		if (nonsap.equalsIgnoreCase("DTOBM")) {
			return userListDTOBM;
		} else {
			return userListDTKBM;
		}
	}

	public ArrayList<UserModel> getUserOnboardWeekly(Date begin_date, Date end_date, String nonsap)
			throws SQLException, ParseException {

		ResultSet rs = null;
		Connection conn = SQLConn.getConnectionSql();
		ArrayList<UserModel> userListDTOBM = new ArrayList<UserModel>();
		ArrayList<UserModel> userListDTKBM = new ArrayList<UserModel>();

		try {

			java.sql.Date begin = new java.sql.Date(begin_date.getTime());
			java.sql.Date end = new java.sql.Date(end_date.getTime());

			CallableStatement stmt = conn.prepareCall("{call getUserOnboardWeekly(?,?)}");
			System.out.println(stmt);
			stmt.setDate(1, (java.sql.Date) begin);
			stmt.setDate(2, (java.sql.Date) end);

			stmt.execute();
			rs = stmt.getResultSet();

			try {
				while (rs.next()) {
					Date a = rs.getDate(1);
					Date b = rs.getDate(2);
					String type = rs.getString(3);

					String a_str = Helper.dateToStringWeek(a);
					String b_str = Helper.dateToStringWeek(b);
					String[] arr = type.split(",");
					type = arr[1].substring(6);
					if (!(type.contains("failed") || type.contains(":"))) {
						if (nonsap.equalsIgnoreCase("DTOBM") && type.charAt(0) == 'O') {
							UserModel user = new UserModel(a_str,b_str, type);
							userListDTOBM.add(user);
						} else if (nonsap.equalsIgnoreCase("DTKBM") && type.charAt(0) == 'K') {
							UserModel user = new UserModel(a_str,b_str, type);
							userListDTKBM.add(user);
						}
					}

				}
			} finally {
				stmt.close();
			}
		} finally {
			conn.close();
		}

		if (nonsap.equalsIgnoreCase("DTOBM")) {
			return userListDTOBM;
		} else {
			return userListDTKBM;
		}
	}
	
	public ArrayList<UserModel> getUserOnboard(Date begin_date, Date end_date,String nonsap) throws SQLException, ParseException {

		ResultSet rs = null;
		Connection conn = SQLConn.getConnectionSql();
		ArrayList<UserModel> userListDTOBM = new ArrayList<UserModel>();
		ArrayList<UserModel> userListDTKBM = new ArrayList<UserModel>();


		try {

			java.sql.Date begin = new java.sql.Date(begin_date.getTime());
			java.sql.Date end = new java.sql.Date(end_date.getTime());

			CallableStatement stmt = conn.prepareCall("{call getUserOnboard(?,?)}");
			System.out.println(stmt);
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
					String type = rs.getString(2);
					String[] arr = type.split(",");
					type = arr[1].substring(6);
					if (!(type.contains("failed") || type.contains(":"))) {
						
						if (nonsap.equalsIgnoreCase("DTOBM") && type.charAt(0) == 'O') {
							UserModel user = new UserModel(date_str, type);
							userListDTOBM.add(user);
						} else if (nonsap.equalsIgnoreCase("DTKBM") && type.charAt(0) == 'K') {
							UserModel user = new UserModel(date_str, type);
							userListDTKBM.add(user);
						}
					}

				}
			} finally {
				stmt.close();
			}
		} finally {
			conn.close();
		}

		if (nonsap.equalsIgnoreCase("DTOBM")) {
			return userListDTOBM;
		} else {
			return userListDTKBM;
		}
	}
	
	

}
