package ca.iam.eao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.ejb.Stateless;

import ca.iam.util.Settings;

@Stateless
public class LoginEao {
	
	String result = null;
	CallableStatement cs = null;
	ResultSet rs = null;
	Connection conn = null;
	String query = null;

	public String getUser(String user,String pass) throws SQLException{
		try{
			result = "0";
			
			conn = Settings.getConnection();
			query = "{call getUserDbmc(?,?)}";
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

	public void setLogin(String user,String pass) throws SQLException{
		try{		
			conn = Settings.getConnection();
			query = "{call setUserDbmc(?,?)}";
			cs = conn.prepareCall(query);
			cs.setString(1,user);
			cs.setString(2,pass);
			cs.execute();
		}
		finally{
			conn.close();
		}
	}
}
