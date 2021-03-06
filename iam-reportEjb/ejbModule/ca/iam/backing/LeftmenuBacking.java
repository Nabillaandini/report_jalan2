package ca.iam.backing;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuElement;
import org.primefaces.model.menu.MenuItem;
import org.primefaces.model.menu.MenuModel;

import com.sun.faces.taglib.html_basic.SelectOneMenuTag;

import org.primefaces.component.panelmenu.PanelMenu;
import org.primefaces.component.selectonemenu.SelectOneMenu;
import org.primefaces.component.selectonemenu.SelectOneMenuRenderer;

import ca.iam.rules.LeftmenuRules;
import ca.iam.rules.LoginRules;
import ca.iam.util.Settings;

@ManagedBean
@SessionScoped
@Stateless
@Named
public class LeftmenuBacking implements Serializable{
	private static final long serialVersionUID = -3005541115232171012L;
    	
	@EJB
//	@Inject
	private LoginRules loginRules;
	
	 
	
	@EJB
	private LeftmenuRules leftmenuRules;
		
    private MenuModel model;
    private MenuModel panelModel;
    private String headName;
    private String appsValue;
    private List<SelectItem> appsList;
    private String isLogin;
    
//    private SelectOneMenuView selectOneMenu;
    
    
	public MenuModel getModel() {
		return model;
	}

	public String getIsLogin() {
		return isLogin;
	}

	public void setIsLogin(String isLogin) {
		this.isLogin = isLogin;
	}

	public void setModel(MenuModel model) {
		this.model = model;
	}

	public String getHeadName() {
		return headName;
	}

	public void setHeadName(String headName) {
		this.headName = headName;
	}

	public String getAppsValue() {
		return appsValue;
	}

	public void setAppsValue(String appsValue) {
		System.out.println("app value : " + getAppsValue());
		this.appsValue = appsValue;
	}

	public List<SelectItem> getAppsList() {
		return appsList;
	}

	public void setAppsList(List<SelectItem> appsList) {
		this.appsList = appsList;
	}

	public void load(ComponentSystemEvent event){
//		selectOneMenu.init();
        model = new DefaultMenuModel();
        DefaultMenuItem item;
        DefaultSubMenu subMenu;
        SelectOneMenu subMenuType = new SelectOneMenu();
        SelectOneMenuRenderer sub;
               
		appsList = new ArrayList<SelectItem>();
		appsList.add(new SelectItem("SAP","sap"));
		appsList.add(new SelectItem("DTOBM","dtobm"));
		appsList.add(new SelectItem("DTKBM","dtkbm"));
  
        
        subMenu = new DefaultSubMenu("Summary");
          
		
		  item = new DefaultMenuItem(Settings.getMessageString("text.page.menu.summary"));
		  item.setIcon("ui-icon-clipboard");
		  item.setCommand("#{leftmenuBacking.linkMenuPanel('summary')}");
		  subMenu.addElement(item);
		  
		
        item = new DefaultMenuItem(Settings.getMessageString("text.page.menu.logout"));
        item.setIcon("ui-icon-close");
        item.setCommand("#{leftmenuBacking.linkMenuPanel('logout')}");
        subMenu.addElement(item);
        
       

        model.addElement(subMenu);
        
    }
	
	public MenuModel getPanelModel() {
		return panelModel;
	}

	public void setPanelModel(MenuModel panelModel) {
		this.panelModel = panelModel;
	}

	public String linkMenuPanel(String str) throws SQLException{
		String result = "";
		result = leftmenuRules.setLinkMenuPanel(str);
		return result;
	}
	
	public void submitApp() {
		FacesContext context = FacesContext.getCurrentInstance();
	
		if(this.appsValue != null) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage( getAppsValue() + "is selected"));
		}
		System.out.println("submitApp : " + getAppsValue());
		
		
	}

}
