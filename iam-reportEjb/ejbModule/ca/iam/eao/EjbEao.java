package ca.iam.eao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import javax.ejb.Stateless;

import ca.iam.entity.UserUpdates;
import ca.iam.util.Settings;
@Stateless
public class EjbEao {
	
	
	public String testDb() throws SQLException{
		String result = null;
		ResultSet rs = null;
		Connection conn = Settings.getConnection();
			try {
				String sql = "{call GetAllUser()}";
				CallableStatement cs = conn.prepareCall(sql);
				rs = cs.executeQuery(sql);
				try {
					while(rs.next()){
					result = rs.getString("user_id");
					}
				}
				finally {
				cs.close();
				}
			}
			finally {
				conn.close();
			}
		
		return result;
	}
	
	public String uploadFlatFile(String full_name, String user_id, String login_id, String first_name, String middle_name, String last_name, 
			Date activation_date, String string_05, Date expiration_date, String employee_status, String manager, String manager_employee_number, 
			String integer_04, String alternate_email, String mobile_phone, String string_00, String strng_01, String department, String string_02, 
			String case_exact_string04, String cost_center, String case_exact_string03, String string_03, String case_exact_string02, String string_04,
			String case_exact_string01, String string_08, String case_exact_string00, String string_09, String string_06, String string_07, 
			String employee_type, Boolean enable, Date last_update) throws SQLException{
		String result = null;
		ResultSet rs = null;
		Connection conn = Settings.getConnection();
			try {
				//String sql = "{call insertUser()}";
				CallableStatement stmt=conn.prepareCall("{call insertUser(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");  
				stmt.setString(1, full_name);  
				stmt.setString(2,user_id);  
				stmt.setString(3, login_id);
				stmt.setString(4, first_name);
				stmt.setString(5, middle_name);
				stmt.setString(6, last_name);
				stmt.setDate(7, (java.sql.Date) activation_date);
				stmt.setString(8, string_05);
				stmt.setDate(9, (java.sql.Date) expiration_date);
				stmt.setString(10, employee_status);
				stmt.setString(11, manager);
				stmt.setString(12, manager_employee_number);
				stmt.setString(13, integer_04);
				stmt.setString(14, alternate_email);
				stmt.setString(15, mobile_phone);
				stmt.setString(16, string_00);
				stmt.setString(17, strng_01);
				stmt.setString(18, department);
				stmt.setString(19, string_02);
				stmt.setString(20, case_exact_string04);
				stmt.setString(22, cost_center);
				stmt.setString(23, case_exact_string03);
				stmt.setString(24, string_03);
				stmt.setString(25, case_exact_string02);
				stmt.setString(26, string_04);
				stmt.setString(27, case_exact_string01);
				stmt.setString(28, string_08);
				stmt.setString(29, case_exact_string00);
				stmt.setString(30, string_09);
				stmt.setString(31, string_06);
				stmt.setString(32, string_07);
				stmt.setString(33, employee_type);
				stmt.setBoolean(34, enable);
				stmt.setDate(35,(java.sql.Date) last_update);
				
				stmt.execute();
				
			}
			finally {
				conn.close();
			}
		
		return result;
	}
	public ArrayList<UserUpdates> getUpdateById(String userId, Date begin_date, Date end_date) throws SQLException{
		String result = null;
		ResultSet rs = null;
		Connection conn = Settings.getConnection();
		UserUpdates user_updates = new UserUpdates();
		ArrayList<UserUpdates> userList = new ArrayList();
		
			try {
//				String sql = "{call GetAllUser()}";
//				
//				CallableStatement cs = conn.prepareCall(sql);
//				rs = cs.executeQuery(sql);
				java.sql.Date begin = new java.sql.Date(begin_date.getTime());
				java.sql.Date end = new java.sql.Date(end_date.getTime());
				CallableStatement stmt=conn.prepareCall("{call getUpdateById(?,?,?)}");  
				stmt.setString(1, userId);  
				stmt.setDate(2, (java.sql.Date) begin);
				stmt.setDate(3, (java.sql.Date) end);

				stmt.execute();
				
				
				try {
					while(rs.next()){
					String full_name=	rs.getString("full_name");  
					String user_id=	rs.getString("user_id");  
					String login_id=	rs.getString("login_id");
					String first_name=	rs.getString("first_name");
					String middle_name=	rs.getString("middle_name");
					String last_name=	rs.getString("last_name");
					Date activation_date=	rs.getDate("activation_date");
					String string_05=	rs.getString("string_05");
					Date expiration_date=	rs.getDate("expiration_date");
					String employee_status=	rs.getString("employee_status");
					String manager=	rs.getString("manager");
					String manager_employee_number=	rs.getString("manager_employee_number");
					String integer_04=	rs.getString("integer_04");
					String alternate_email=	rs.getString("alternate_email");
					String mobile_phone=	rs.getString("mobile_phone");
					String string_00=	rs.getString("string_00");
					String strng_01=	rs.getString("strng_01");
					String department=	rs.getString("department");
					String string_02=	rs.getString("string_02");
					String case_exact_string04=	rs.getString("case_exact_string04");
					String cost_center=	rs.getString("cost_center");
					String case_exact_string03=	rs.getString("case_exact_string03");
					String string_03=	rs.getString("string_03");
					String case_exact_string02=	rs.getString("case_exact_string02");
					String string_04=	rs.getString("string_04");
					String case_exact_string01=	rs.getString("case_exact_string01");
					String string_08=	rs.getString("string_08");
					String case_exact_string00=	rs.getString("case_exact_string00");
					String string_09=	rs.getString("string_09");
					String string_06=	rs.getString("string_06");
					String string_07=	rs.getString("string_07");
					String employee_type=	rs.getString("employee_type");
					Boolean enable=	rs.getBoolean("enable");
					Date last_update=	rs.getDate("last_update");
					int is_onboard=	rs.getInt("is_onboard");
					int is_update=	rs.getInt("is_update");
					String updated_attr=	rs.getString("updated_attr");
					int upd_success=	rs.getInt("upd_success");
					user_updates = new UserUpdates(full_name, user_id, login_id, first_name, middle_name, last_name, activation_date, string_05, expiration_date, employee_status, manager, manager_employee_number, integer_04, alternate_email, mobile_phone, string_00, strng_01, department, string_02, case_exact_string04, cost_center, case_exact_string03, string_03, case_exact_string02, string_04, case_exact_string01, string_08, case_exact_string00, string_09, string_06, string_07, employee_type,enable, last_update, is_onboard, is_update, updated_attr, upd_success);
					userList.add(user_updates);
					}
				}
				finally {
				stmt.close();
				}
			}
			finally {
				conn.close();
			}
		
		return userList;
	}
}