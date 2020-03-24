package ca.iam.rules;

import java.io.Serializable;
import java.sql.SQLException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import ca.iam.backing.LoginBacking;
import ca.iam.backing.SellBacking;
import ca.iam.backing.UserBacking;
import ca.iam.eao.LogoutEao;

@Stateless
public class LeftmenuRules implements Serializable{
	private static final long serialVersionUID = 5348186282278838066L;
	@Inject
	private LoginBacking loginBacking;
	
	@SuppressWarnings("unused")
	@Inject
	private SellBacking sellBacking;
	
	@SuppressWarnings("unused")
	@Inject
	private UserBacking userBacking;
	
	@EJB
	private LogoutEao logoutEao;
			
	public String setLinkMenuPanel(String str) throws SQLException{
		String result = "";
		switch (str) {
		case "user" :
			result = "user?faces-redirect=true";
			break;
		case "detail" :
			result = "userDetail?faces-redirect=true";
			break;
		case "sumonboard" :
			result = "sumonboard?faces-redirect=true";
			break;
		case "detailonboard" :
			result = "detailonboard?faces-redirect=true";
			break;
		case "sumupdate" :
			result = "sumupdate?faces-redirect=true";
			break;
		case "detailupdate" :
			result = "detailupdate?faces-redirect=true";
			break;
		case "sumdisabled" :
			result = "sumdisabled?faces-redirect=true";
			break;
		case "detaildisabled" :
			result = "detaildisabled?faces-redirect=true";
			break;
		case "summary" :
			result = "summary?faces-redirect=true";
			break;
		default :
			HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
			String user = null;
			loginBacking.setCloseSession(false);
	    	
			
			user = request.getSession().getAttribute("username").toString();
			System.out.println("logout user : " + user);
//			logoutEao.setLogout(user);
							        
	        request.getSession().invalidate();
			result = "login?faces-redirect=true";
			break;
		}
		
		return result;
	}

}
