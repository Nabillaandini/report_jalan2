package ca.iam.util;

import java.io.IOException;
import java.io.Serializable;







import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
//import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import ca.iam.backing.LoginBacking;


public class AutoLogout implements Serializable {
	private static final long serialVersionUID = -3599843786151220979L;
	;
	
	public void load(ComponentSystemEvent event) throws IOException{
		FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest(); 		
//        System.out.println("session id : " + request.getSession().getId() +
//        		"; user : " + request.getSession().getAttribute("username"));
        
        if (request.getSession().getAttribute("username") == null){
            request.getSession().invalidate();
			context.addMessage(null, new FacesMessage(Settings.getMessageString("text.page.auto.out")));
	        FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml");
		}
	}
}
