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
import org.primefaces.model.menu.MenuModel;

import com.sun.faces.taglib.html_basic.SelectOneMenuTag;

import org.primefaces.component.selectonemenu.SelectOneMenu;
import org.primefaces.component.selectonemenu.SelectOneMenuRenderer;

import ca.iam.entity.SelectOneMenuView;
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
    private String headName;
    private String appsValue;
    private List<SelectItem> appsList;
    
//    private SelectOneMenuView selectOneMenu;
    
	public MenuModel getModel() {
		return model;
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
  
        
        subMenu = new DefaultSubMenu(Settings.getMessageString("text.page.menu.report"));
          
        item = new DefaultMenuItem(Settings.getMessageString("text.page.menu.summary"));
        item.setIcon("ui-icon-clipboard");
        item.setCommand("#{leftmenuBacking.linkMenuPanel('summary')}");
        subMenu.addElement(item);
        
        item = new DefaultMenuItem(Settings.getMessageString("text.page.menu.sumonboard"));
        item.setIcon("ui-icon-note");
        item.setCommand("#{leftmenuBacking.linkMenuPanel('sumonboard')}");
        subMenu.addElement(item);
        
        item = new DefaultMenuItem(Settings.getMessageString("text.page.menu.detailonboard"));
        item.setIcon("ui-icon-note");
        item.setCommand("#{leftmenuBacking.linkMenuPanel('detailonboard')}");
        subMenu.addElement(item);
        
        item = new DefaultMenuItem(Settings.getMessageString("text.page.menu.sumupdate"));
        item.setIcon("ui-icon-note");
        item.setCommand("#{leftmenuBacking.linkMenuPanel('sumupdate')}");
        subMenu.addElement(item);

        item = new DefaultMenuItem(Settings.getMessageString("text.page.menu.detailupdate"));
        item.setIcon("ui-icon-note");
        item.setCommand("#{leftmenuBacking.linkMenuPanel('detailupdate')}");
        subMenu.addElement(item);
        
        item = new DefaultMenuItem(Settings.getMessageString("text.page.menu.sumdisabled"));
        item.setIcon("ui-icon-note");
        item.setCommand("#{leftmenuBacking.linkMenuPanel('sumdisabled')}");
        subMenu.addElement(item);
        
        item = new DefaultMenuItem(Settings.getMessageString("text.page.menu.detaildisabled"));
        item.setIcon("ui-icon-note");
        item.setCommand("#{leftmenuBacking.linkMenuPanel('detaildisabled')}");
        subMenu.addElement(item);
        
        item = new DefaultMenuItem(Settings.getMessageString("text.page.menu.sumprov"));
        item.setIcon("ui-icon-person");
        item.setCommand("#{leftmenuBacking.linkMenuPanel('sumprov')}");
        subMenu.addElement(item);
        
        item = new DefaultMenuItem(Settings.getMessageString("text.page.menu.detailprov"));
        item.setIcon("ui-icon-person");
        item.setCommand("#{leftmenuBacking.linkMenuPanel('detailprov')}");
        subMenu.addElement(item);
        
        item = new DefaultMenuItem(Settings.getMessageString("text.page.menu.detaildeprov"));
        item.setIcon("ui-icon-person");
        item.setCommand("#{leftmenuBacking.linkMenuPanel('detaildeprov')}");
        subMenu.addElement(item);
        
        item = new DefaultMenuItem(Settings.getMessageString("text.page.menu.logout"));
        item.setIcon("ui-icon-close");
        item.setCommand("#{leftmenuBacking.linkMenuPanel('logout')}");
        subMenu.addElement(item);
       

        model.addElement(subMenu);
        
    }
	
	public String linkMenuPanel(String str) throws SQLException{
		String result = "";
		result = leftmenuRules.setLinkMenuPanel(str);
		return result;
	}
	
	public void submitApp() {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage( getAppsValue() + "is selected"));
		System.out.println("submitApp : " + getAppsValue());
		
		
	}

}
