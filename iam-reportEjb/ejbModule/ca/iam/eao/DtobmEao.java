package ca.iam.eao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

@Stateless
@LocalBean
@Named

public class DtobmEao {

	public ArrayList<CountList> countData(Date begin_date, Date end_date, String type)
			throws SQLException, ParseException {

		ResultSet rs = null;
		Connection conn = Settings.getConnection();
		ArrayList<CountList> resultCount = new ArrayList<CountList>();
		System.out.println(type);

		try {

			java.sql.Date begin = new java.sql.Date(begin_date.getTime());
			java.sql.Date end = new java.sql.Date(end_date.getTime());
			if (type.equalsIgnoreCase("onboard")) {
				CallableStatement stmt = conn.prepareCall("{call countOnboardDTOBM(?,?)}");
				stmt.setDate(1, (java.sql.Date) begin);
				stmt.setDate(2, (java.sql.Date) end);

				stmt.execute();
				rs = stmt.getResultSet();

				try {
					while (rs.next()) {
						int count = rs.getInt(1);
						Date date = rs.getDate(2);
						String date_str = Helper.dateToString(date);
						CountList temp = new CountList(date_str, count);
						resultCount.add(temp);
					}
				} finally {
					stmt.close();
				}
			} else if (type.equalsIgnoreCase("update")) {
				CallableStatement stmt = conn.prepareCall("{call countUpdateDTOBM(?,?)}");
				stmt.setDate(1, (java.sql.Date) begin);
				stmt.setDate(2, (java.sql.Date) end);

				stmt.execute();
				rs = stmt.getResultSet();

				try {
					while (rs.next()) {
						int count = rs.getInt(1);
						Date date = rs.getDate(2);
						String date_str = Helper.dateToString(date);
						CountList temp = new CountList(date_str, count);
						resultCount.add(temp);
					}
				} finally {
					stmt.close();
				}
			} else if (type.equalsIgnoreCase("disable")) {
				CallableStatement stmt = conn.prepareCall("{call countDisableDTOBM(?,?)}");
				stmt.setDate(1, (java.sql.Date) begin);
				stmt.setDate(2, (java.sql.Date) end);

				stmt.execute();
				rs = stmt.getResultSet();

				try {
					while (rs.next()) {
						int count = rs.getInt(1);
						Date date = rs.getDate(2);
						String date_str = Helper.dateToString(date);
						CountList temp = new CountList(date_str, count);
						resultCount.add(temp);
					}
				} finally {
					stmt.close();
				}
			}

		} finally {
			conn.close();
		}

		return resultCount;
	}

	public ArrayList<UserUpdates> getUserOnboard(Date begin_date, Date end_date) throws SQLException, ParseException {

		ResultSet rs = null;
		Connection conn = Settings.getConnection();
		UserUpdates user_updates = new UserUpdates();
		ArrayList<UserUpdates> userList = new ArrayList<UserUpdates>();

		try {

			java.sql.Date begin = new java.sql.Date(begin_date.getTime());
			java.sql.Date end = new java.sql.Date(end_date.getTime());

			CallableStatement stmt = conn.prepareCall("{call getDtobmOnboard(?,?)}");
			System.out.println(stmt);
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

	public ArrayList<UserUpdates> getUserUpdate(Date begin_date, Date end_date) throws SQLException, ParseException {

		ResultSet rs = null;
		Connection conn = Settings.getConnection();
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
		Connection conn = Settings.getConnection();
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
	public SummaryModel getSummaryReport(Date begin_date, Date end_date) throws SQLException, ParseException {

		ResultSet rs = null;
		Connection conn = Settings.getConnection();
		SummaryModel sumModel = new SummaryModel();

		try {

			java.sql.Date begin = new java.sql.Date(begin_date.getTime());
			java.sql.Date end = new java.sql.Date(end_date.getTime());

			CallableStatement stmt = conn.prepareCall("{call summaryDTOBM(?,?)}");
			stmt.setDate(1, (java.sql.Date) begin);
			stmt.setDate(2, (java.sql.Date) end);

			stmt.execute();
			rs = stmt.getResultSet();

			try {
				while (rs.next()) {
				int countOnboard = rs.getInt("countOnboard");
				int countUpdate = rs.getInt("countUpdate");
				int countDisabled = rs.getInt("countDisabled");
				int phone = rs.getInt("phone");
				int email = rs.getInt("email");
				int name = rs.getInt("name");
				sumModel = new SummaryModel(countOnboard, countUpdate, countDisabled, phone, email, name,0,0);
				}
			} finally {
				stmt.close();
			}
		} finally {
			conn.close();
		}

		return sumModel;
	}
	

}
