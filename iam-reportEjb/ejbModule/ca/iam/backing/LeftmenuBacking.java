package ca.iam.backing;

import java.io.Serializable;
import java.sql.SQLException;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ComponentSystemEvent;

import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;

import ca.iam.rules.LeftmenuRules;
import ca.iam.rules.LoginRules;
import ca.iam.util.Settings;

@ManagedBean
@SessionScoped
public class LeftmenuBacking implements Serializable{
	private static final long serialVersionUID = -3005541115232171012L;
    	
	@EJB 
	private LoginRules loginRules;
	
	@EJB
	private LeftmenuRules leftmenuRules;
		
    private MenuModel model;
    private String headName;
    
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

	public void load(ComponentSystemEvent event){
        model = new DefaultMenuModel();
        DefaultMenuItem item;
        DefaultSubMenu subMenu;
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

}
