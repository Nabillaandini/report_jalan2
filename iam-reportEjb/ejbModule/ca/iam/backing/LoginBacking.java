package ca.iam.backing;

import java.io.Serializable;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import ca.iam.eao.UserEao;
import ca.iam.entity.CountList;
import ca.iam.entity.UserUpdates;
import ca.iam.rules.LoginRules;
import ca.iam.util.Settings;

@ManagedBean
@SessionScoped
@Stateless
@Named
public class LoginBacking implements Serializable{
	private static final long serialVersionUID = 8700187060603805355L;
	
	@EJB
	private LoginRules loginRules;
	
	@EJB
	private UserEao userEao;
	
	@EJB
	private UserBacking userBacking;
	
	private String username;
	private String password;
	private boolean closeSession = false;
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isCloseSession() {
		return closeSession;
	}

	public void setCloseSession(boolean closeSession) {
		this.closeSession = closeSession;
	}

	public void load(ComponentSystemEvent event) throws ParseException, SQLException{
		
		username = "";
		password = "";
	
		
		FacesContext context = FacesContext.getCurrentInstance();
		if (!context.isPostback()){
			if (this.isCloseSession() == true){
				context.addMessage(null, new FacesMessage(Settings.getMessageString("text.page.auto.out")));
				this.setCloseSession(false);
			}
		}
	}
	
	public String submitLogin() throws SQLException{	
		FacesContext context = FacesContext.getCurrentInstance();
		this.setCloseSession(false);
		String result = "";


		result = loginRules.getUser(username, password);
		result = "1";
		if (result.equals("1")){
	        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	    	HttpSession session = request.getSession(); //sessionCreated() is executed
	    	session.setAttribute("username", username);	 
			result = "summary?faces-redirect=true";
		}else if (result.equals("0")){   
	        context.addMessage(null, new FacesMessage(Settings.getMessageString("text.page.login.error")));
	        result = null;
		}else {
	        context.addMessage(null, new FacesMessage(Settings.getMessageString("text.page.login.user.online")));
	        result = null;
		}
		return result;	
	}
}
