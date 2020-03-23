package ca.iam.core;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.servlet.http.HttpServletRequest;

public abstract class BasicBacking implements Serializable {
	private static final long serialVersionUID = -9171053487047277232L;

	public boolean isUserLoggedIn() {
        return getSessionMap().containsKey("currentUser");
    }

    public FacesContext getFacesContext() {
        return FacesContext.getCurrentInstance();
    }

    public Map<String, Object> getSessionMap() {
        return FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
    }

    public Map<String, Object> getRequestMap() {
        return FacesContext.getCurrentInstance().getExternalContext().getRequestMap();
    }

    public Flash getFlash() {
        return getFacesContext().getExternalContext().getFlash();
    }

    public HttpServletRequest getHttpServletRequest() {
    	return ((HttpServletRequest)getFacesContext().getExternalContext().getRequest());
    }
    
	public ResourceBundle getMessageBundle() {
		String bundleName = getFacesContext().getApplication().getMessageBundle();
		Locale locale = getFacesContext().getViewRoot().getLocale();
		
		return ResourceBundle.getBundle(bundleName, locale);
	}

	public String getResourceString(String key) {
		try {
			return getMessageBundle().getString(key);
		}
		catch (MissingResourceException e) {
			return "!" + key + "!";
		}
	}
	
	public String getResourceString(String key, Object... params) {
		try {
			return MessageFormat.format(getMessageBundle().getString(key), params);
		}
		catch (MissingResourceException e) {
			return "!" + key + "!";
		}
	}
	
}