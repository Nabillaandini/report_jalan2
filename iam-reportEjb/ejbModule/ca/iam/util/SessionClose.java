package ca.iam.util;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import ca.iam.backing.LoginBacking;

public class SessionClose implements Serializable{
	private static final long serialVersionUID = -6618786226701442787L;	
	
	@Inject
	private LoginBacking loginBacking;
	
	private int timer;	
	
	public int getTimer() {
		timer = (30* 60 * 1000) + (0 * 1000);
		return timer;
	}


	public void setTimer(int timer) {
		this.timer = timer;
	}


	public void onIdle() throws IOException {        
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        
        loginBacking.setCloseSession(true);
        request.getSession().invalidate();
        FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml");
        
    }

}
