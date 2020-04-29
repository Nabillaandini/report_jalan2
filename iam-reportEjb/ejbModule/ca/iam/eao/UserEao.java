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

import com.itextpdf.text.log.SysoCounter;

import ca.iam.entity.CountList;
import ca.iam.entity.Provision;
import ca.iam.entity.ResetModel;
import ca.iam.entity.SummaryModel;
import ca.iam.entity.SummarySQL;
import ca.iam.entity.UserModel;
import ca.iam.entity.UserRequest;
import ca.iam.entity.UserUpdates;
import ca.iam.util.Helper;
import ca.iam.util.SQLConn;
import ca.iam.util.ReportConn;
import ca.iam.util.Settings;

@Stateless
@LocalBean
@Named
public class UserEao {
	String result = null;
	CallableStatement cs = null;
	ResultSet rs = null;
	Connection conn = null;
	String query = null;


	public ArrayList<UserModel> getUserOnboard(Date begin_date, Date end_date) throws SQLException, ParseException {

		ResultSet rs = null;
		Connection conn = SQLConn.getConnectionSql();
		ArrayList<UserModel> userList = new ArrayList<UserModel>();

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
					if(date==null) {
						continue;
					}
					String date_str = Helper.dateToString(date);
					String type = rs.getString(2).substring(23);
					if(!(type.contains("failed")||type.contains(":"))) {
						UserModel user = new UserModel(date_str,type);
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

	public ArrayList<CountList> countData(Date begin_date, Date end_date, String type)
			throws SQLException, ParseException {

		ResultSet rs = null;

		Connection conn = SQLConn.getConnectionSql();
		ArrayList<CountList> resultCount = new ArrayList<CountList>();
		System.out.println(type);

		try {

			java.sql.Date begin = new java.sql.Date(begin_date.getTime());
			java.sql.Date end = new java.sql.Date(end_date.getTime());
			if (type.equalsIgnoreCase("onboard")) {
				CallableStatement stmt = conn.prepareCall("{call countOnboard(?,?)}");
				stmt.setDate(1, (java.sql.Date) begin);
				stmt.setDate(2, (java.sql.Date) end);

				stmt.execute();
				rs = stmt.getResultSet();

				try {
					while (rs.next()) {
						int count = rs.getInt(1);
						Date date = rs.getDate(2);
						if(date==null) {
							continue;
						}
						String date_str = Helper.dateToString(date);
						if(count !=0) {
						CountList temp = new CountList(date_str, count);
						resultCount.add(temp);
						}
					}
				} finally {
					stmt.close();
				}
			} else if (type.equalsIgnoreCase("update")) {
				CallableStatement stmt = conn.prepareCall("{call countUpdate(?,?)}");
				stmt.setDate(1, (java.sql.Date) begin);
				stmt.setDate(2, (java.sql.Date) end);

				stmt.execute();
				rs = stmt.getResultSet();

				try {
					while (rs.next()) {
						int count = rs.getInt(1);
						Date date = rs.getDate(2);
						if(date==null) {
							continue;
						}
						String date_str = Helper.dateToString(date);
						if(count !=0) {
						CountList temp = new CountList(date_str, count);
						resultCount.add(temp);
						}
					}
				} finally {
					stmt.close();
				}
			} else if (type.equalsIgnoreCase("disable")) {
				CallableStatement stmt = conn.prepareCall("{call countDisable(?,?)}");
				stmt.setDate(1, (java.sql.Date) begin);
				stmt.setDate(2, (java.sql.Date) end);

				stmt.execute();
				rs = stmt.getResultSet();

				try {
					while (rs.next()) {
						int count = rs.getInt(1);
						Date date = rs.getDate(2);
						String date_str = Helper.dateToString(date);
						if(count !=0) {
						CountList temp = new CountList(date_str, count);
						resultCount.add(temp);
						}
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

	public ArrayList<UserModel> getUserUpdate(Date begin_date, Date end_date) throws SQLException, ParseException {

		ResultSet rs = null;
		Connection conn = SQLConn.getConnectionSql();
		ArrayList<UserModel> userList = new ArrayList<UserModel>();

		try {

			java.sql.Date begin = new java.sql.Date(begin_date.getTime());
			java.sql.Date end = new java.sql.Date(end_date.getTime());

			CallableStatement stmt = conn.prepareCall("{call getUserUpdates(?,?)}");
			stmt.setDate(1, (java.sql.Date) begin);
			stmt.setDate(2, (java.sql.Date) end);

			stmt.execute();
			rs = stmt.getResultSet();

			try {
				while (rs.next()) {
					Date date = rs.getDate(1);
					String date_str = Helper.dateToString(date);
					String type = rs.getString(2).substring(23);
					if(!(type.contains("failed")||type.contains(":"))) {
						UserModel user = new UserModel(date_str,type);
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
	
	public ArrayList<UserUpdates> getUserDisabled(Date begin_date, Date end_date) throws SQLException, ParseException {

		ResultSet rs = null;
		Connection conn = ReportConn.getConnection();
		UserUpdates user_updates = new UserUpdates();
		ArrayList<UserUpdates> userList = new ArrayList<UserUpdates>();

		try {

			java.sql.Date begin = new java.sql.Date(begin_date.getTime());
			java.sql.Date end = new java.sql.Date(end_date.getTime());

			CallableStatement stmt = conn.prepareCall("{call getUserDisabled(?,?)}");
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
	
	public ArrayList<SummaryModel> getSummaryReport(Date begin_date, Date end_date) throws SQLException, ParseException {

		ResultSet rs = null;
		Connection conn = SQLConn.getConnectionSql();
		ArrayList<SummaryModel> sumModel = new ArrayList<SummaryModel>();
		String date_str = "";

		try {

			java.sql.Date begin = new java.sql.Date(begin_date.getTime());
			java.sql.Date end = new java.sql.Date(end_date.getTime());

			CallableStatement stmt = conn.prepareCall("{call getSummaryReport(?,?)}");
			stmt.setDate(1, (java.sql.Date) begin);
			stmt.setDate(2, (java.sql.Date) end);

			stmt.execute();
			rs = stmt.getResultSet();

			try {
				while (rs.next()) {
					
				int countOnboard = rs.getInt(1);
				int countUpdate = rs.getInt(2);
				int countDisabled = rs.getInt(3);
				Date date = rs.getDate(4);
				if(date != null) {
					date_str = Helper.dateToString(date);
				}
				if(countOnboard>0 || countUpdate>0 || countDisabled>0) {
				
				sumModel.add(new SummaryModel(countOnboard, countUpdate, countDisabled, date_str));
				}
				}
			} finally {
				stmt.close();
			}
		} finally {
			conn.close();
		}

		return sumModel;
	}
	
	public ArrayList<CountList> countProvision(Date begin_date, Date end_date)
			throws SQLException, ParseException {

		ResultSet rs = null;
		Connection conn = SQLConn.getConnectionSql();
		ArrayList<CountList> resultCount = new ArrayList<CountList>();

		try {

			java.sql.Date begin = new java.sql.Date(begin_date.getTime());
			java.sql.Date end = new java.sql.Date(end_date.getTime());
			System.out.println("before exec sql");
				CallableStatement stmt = conn.prepareCall("{call countProvision(?,?)}");
				stmt.setDate(1, (java.sql.Date) begin);
				stmt.setDate(2, (java.sql.Date) end);

				stmt.execute();
				rs = stmt.getResultSet();

				try {
					while (rs.next()) {
						int count = rs.getInt(2);
						Date date = rs.getDate(1);
						if(date==null) {
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
	public ArrayList<Provision> getProvisionData(Date begin_date, Date end_date)
			throws SQLException, ParseException {

		ResultSet rs = null;
		Connection conn = SQLConn.getConnectionSql();
		ArrayList<Provision> userList = new ArrayList<Provision>();
		Provision prov = new Provision();

		try {

			java.sql.Date begin = new java.sql.Date(begin_date.getTime());
			java.sql.Date end = new java.sql.Date(end_date.getTime());
			
				CallableStatement stmt = conn.prepareCall("{call getProvisionData(?,?)}");
				stmt.setDate(1, (java.sql.Date) begin);
				stmt.setDate(2, (java.sql.Date) end);

				stmt.execute();
				rs = stmt.getResultSet();

				try {
					while (rs.next()) {
						Date date = rs.getDate(1);
						if(date==null) {
							continue;
						}
						String date_str = Helper.dateToString(date);
						String type = rs.getString(2).substring(39);
						if(!(type.contains("failed")||type.contains(":"))) {
							prov = new Provision(date_str,type);
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
	
	public ArrayList<CountList> countDeprovision(Date begin_date, Date end_date)
			throws SQLException, ParseException {

		ResultSet rs = null;
		Connection conn = SQLConn.getConnectionSql();
		ArrayList<CountList> resultCount = new ArrayList<CountList>();

		try {

			java.sql.Date begin = new java.sql.Date(begin_date.getTime());
			java.sql.Date end = new java.sql.Date(end_date.getTime());
			System.out.println("before exec sql");
				CallableStatement stmt = conn.prepareCall("{call countDeprovision(?,?)}");
				stmt.setDate(1, (java.sql.Date) begin);
				stmt.setDate(2, (java.sql.Date) end);

				stmt.execute();
				rs = stmt.getResultSet();

				try {
					while (rs.next()) {
						int count = rs.getInt(1);
						Date date = rs.getDate(2);
						if(date==null) {
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
	public ArrayList<Provision> getDeprovData(Date begin_date, Date end_date)
			throws SQLException, ParseException {

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
						if(date==null) {
							continue;
						}
						String date_str = Helper.dateToString(date);
						String type = rs.getString(2).substring(35);
						if(!(type.contains("failed")||type.contains(":"))) {
							prov = new Provision(date_str,type);
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
	
	public ArrayList<SummarySQL> getSummaryProv(Date begin_date, Date end_date)
			throws SQLException, ParseException {

		ResultSet rs = null;
		Connection conn = SQLConn.getConnectionSql();
		ArrayList<SummarySQL> countList = new ArrayList<SummarySQL>();

		try {

			java.sql.Date begin = new java.sql.Date(begin_date.getTime());
			java.sql.Date end = new java.sql.Date(end_date.getTime());
			System.out.println("before exec sql");
				CallableStatement stmt = conn.prepareCall("{call getSummary(?,?)}");
				
				stmt.setDate(1, (java.sql.Date) begin);
				stmt.setDate(2, (java.sql.Date) end);

				stmt.execute();
				rs = stmt.getResultSet();
				System.out.println("after exec sql");
				try {
					while (rs.next()) {
						int prov = rs.getInt(1);
						int deprov = rs.getInt(2);
						Date date = rs.getDate(3);
						if(date==null) {
							continue;
						}
						String date_str = Helper.dateToString(date);
						if(prov != 0 || deprov!=0) {
						countList.add(new SummarySQL(prov,deprov,date_str));
						}
						
						
					}
				} finally {
					stmt.close();
				}
		

		} finally {
			conn.close();
		}
		return countList;
	}
	
	public ArrayList<ResetModel> countPassChanges(Date begin_date, Date end_date)
			throws SQLException, ParseException {

		ResultSet rs = null;
		Connection conn = SQLConn.getConnectionSql();
		ArrayList<ResetModel> resultCount = new ArrayList<ResetModel>();
		ResetModel temp = null;

		try {

			java.sql.Date begin = new java.sql.Date(begin_date.getTime());
			java.sql.Date end = new java.sql.Date(end_date.getTime());

				CallableStatement stmt = conn.prepareCall("{call countPassChanges(?,?)}");
				stmt.setDate(1, (java.sql.Date) begin);
				stmt.setDate(2, (java.sql.Date) end);

				stmt.execute();
				rs = stmt.getResultSet();

				try {
					while (rs.next()) {
						if(rs.getDate(1)==null) {
							continue;
						}
						Date date = rs.getDate(1);
						String date_str = Helper.dateToString(date);
						int domain = rs.getInt(2);
						int apps = rs.getInt(3);
						if(domain >0 || apps >0) {
						temp = new ResetModel(date_str, domain,apps);
						resultCount.add(temp);
						}
					}
				} finally {
					stmt.close();
				}
		

		} finally {
			conn.close();
		}

		return resultCount;
	}
	public ArrayList<Provision> getPassChanges(Date begin_date, Date end_date)
			throws SQLException, ParseException {

		ResultSet rs = null;
		Connection conn = SQLConn.getConnectionSql();
		ArrayList<Provision> userList = new ArrayList<Provision>();
		Provision prov = new Provision();

		try {

			java.sql.Date begin = new java.sql.Date(begin_date.getTime());
			java.sql.Date end = new java.sql.Date(end_date.getTime());
			
				CallableStatement stmt = conn.prepareCall("{call getPassChanges(?,?)}");
				stmt.setDate(1, (java.sql.Date) begin);
				stmt.setDate(2, (java.sql.Date) end);

				stmt.execute();
				rs = stmt.getResultSet();

				try {
					while (rs.next()) {
						Date date = rs.getDate(1);
						if(date==null) {
							continue;
						}
						String date_str = Helper.dateToString(date);
						String type = rs.getString(2).substring(43);
						if(!(type.contains("failed")||type.contains(":"))) {
							prov = new Provision(date_str,type);
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
	public ArrayList<Provision> getResetChanges(Date begin_date, Date end_date)
			throws SQLException, ParseException {

		ResultSet rs = null;
		Connection conn = SQLConn.getConnectionSql();
		ArrayList<Provision> userList = new ArrayList<Provision>();
		Provision prov = new Provision();

		try {

			java.sql.Date begin = new java.sql.Date(begin_date.getTime());
			java.sql.Date end = new java.sql.Date(end_date.getTime());
			
				CallableStatement stmt = conn.prepareCall("{call getResetChanges(?,?)}");
				stmt.setDate(1, (java.sql.Date) begin);
				stmt.setDate(2, (java.sql.Date) end);

				stmt.execute();
				rs = stmt.getResultSet();

				try {
					while (rs.next()) {
						Date date = rs.getDate(1);
						if(date==null) {
							continue;
						}
						String date_str = Helper.dateToString(date);
						String type = rs.getString(2).substring(50);
						if(!(type.contains("failed")||type.contains(":"))) {
							prov = new Provision(date_str,type);
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
	
	public ArrayList<UserRequest> countUserRequest(Date begin_date, Date end_date, String type)
			throws SQLException, ParseException {

		ResultSet rs = null;
		Connection conn = SQLConn.getConnectionSql();
		ArrayList<UserRequest> resultCount = new ArrayList<UserRequest>();

		try {

			java.sql.Date begin = new java.sql.Date(begin_date.getTime());
			java.sql.Date end = new java.sql.Date(end_date.getTime());

				CallableStatement stmt = conn.prepareCall("{call countUserRequest(?,?,?)}");
				stmt.setDate(1, (java.sql.Date) begin);
				stmt.setDate(2, (java.sql.Date) end);
				stmt.setString(3, "%"+type+"%");
				

				stmt.execute();
				rs = stmt.getResultSet();

				try {
					while (rs.next()) {
						int count = rs.getInt(3);
						String name = rs.getString(2);
						Date date = rs.getDate(1);
						if(date==null) {
							continue;
						}
						String date_str = Helper.dateToString(date);
						UserRequest temp = new UserRequest(date_str, name,String.format("%d",count));
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
	
	public ArrayList<UserRequest> getUserRequest(Date begin_date, Date end_date, String type)
			throws SQLException, ParseException {

		ResultSet rs = null;
		Connection conn = SQLConn.getConnectionSql();
		ArrayList<UserRequest> userList = new ArrayList<UserRequest>();
		UserRequest user = null;
		System.out.println("before try catch");
		try {

			java.sql.Date begin = new java.sql.Date(begin_date.getTime());
			java.sql.Date end = new java.sql.Date(end_date.getTime());
			System.out.println("before exec");
				CallableStatement stmt = conn.prepareCall("{call getUserRequest(?,?)}");
				stmt.setDate(1, (java.sql.Date) begin);
				stmt.setDate(2, (java.sql.Date) end);

				stmt.execute();
				rs = stmt.getResultSet();

				try {
					while (rs.next()) {
						Date date = rs.getDate(1);
						if(date==null) {
							continue;
						}
						String date_str = Helper.dateToString(date);
						String appsName = rs.getString(2);
						String name = rs.getString(3);
						String [] arr = name.split(",");
						name = arr[1].substring(6);
						if(!(name.contains("failed")||name.contains(":"))) {
							if(appsName.contains(type)) {
							user = new UserRequest(date_str, appsName, name);
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
	
}
