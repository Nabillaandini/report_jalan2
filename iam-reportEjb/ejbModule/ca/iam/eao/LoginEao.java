package ca.iam.eao;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.ejb.Stateless;

import ca.iam.util.ReportConn;
import ca.iam.util.Settings;

@Stateless
public class LoginEao {
	
	String result = null;
	CallableStatement cs = null;
	ResultSet rs = null;
	Connection conn = null;
	String query = null;

	public String getUser(String user,String pass) throws SQLException, NoSuchAlgorithmException{
		pass = genPass(pass);
		try{
			result = "0";
			
			conn = ReportConn.getConnection();
			query = "{call getUserLogin(?,?)}";
			cs = conn.prepareCall(query);
			cs.setString(1,user);
			cs.setString(2,pass);
			cs.execute();
			rs = (ResultSet)cs.getResultSet();
			
			try {
				while(rs.next()){
					result = rs.getString(1);
				}
			}
			finally {
				cs.close();
			}
		}
		finally{
			conn.close();
		}
		return result;
	}

//	public void setLogin(String user,String pass) throws SQLException{
//		try{		
//			conn = Settings.getConnection();
//			query = "{call setUserLogout(?,?)}";
//			cs = conn.prepareCall(query);
//			cs.setString(1,user);
//			cs.setString(2,pass);
//			cs.execute();
//		}
//		finally{
//			conn.close();
//		}
//	}

	public String genPass(String pass) throws NoSuchAlgorithmException{
		String result = "";
		String password = pass;

        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] hashInBytes = md.digest(password.getBytes(StandardCharsets.UTF_8));

        StringBuilder sb = new StringBuilder();
        for (byte b : hashInBytes) {
            sb.append(String.format("%02x", b));
        }
        System.out.println("MD5: " + sb.toString());
		return result;
	}
}
