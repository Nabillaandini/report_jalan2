package ca.iam.eao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.ejb.Stateless;

import ca.iam.util.Settings;

@Stateless
public class LogoutEao {
	
	String result = null;
	CallableStatement cs = null;
	ResultSet rs = null;
	Connection conn = null;
	String query = null;

	public void setLogout(String user) throws SQLException{
		try{		
			conn = Settings.getConnection();
			query = "{call setUserDbmcLogout(?)}";
			cs = conn.prepareCall(query);
			cs.setString(1,user);
			cs.execute();
		}
		finally{
			conn.close();
		}
	}
}
