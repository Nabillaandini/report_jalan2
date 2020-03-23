package ca.iam.rules;

import java.sql.SQLException;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import ca.iam.eao.LoginEao;
import ca.iam.eao.UserEao;

@Stateless
public class LoginRules {
	@EJB
	private LoginEao loginEao;
	
	@EJB
	private UserEao userEao;
	
	
	public String getUser(String user, String pass) throws SQLException{
		String result = "0";
//		result = loginEao.getUser(user, pass);
		userEao.testDb();
		if (user.equalsIgnoreCase("admin") & (pass.equalsIgnoreCase("admin")))
			result = "1";
		return result;
	}
	
	public void setLogin(String user, String pass) throws SQLException{
		loginEao.setLogin(user, pass);
	}
}
