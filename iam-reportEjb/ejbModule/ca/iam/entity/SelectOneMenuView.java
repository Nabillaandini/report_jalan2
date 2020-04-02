package ca.iam.entity;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.model.SelectItem;
import javax.inject.Named;

import ca.iam.backing.DisabledBacking;
import ca.iam.backing.OnboardBacking;
import ca.iam.backing.UpdateBacking;

@Named
@RequestScoped
public class SelectOneMenuView {
	private String application;
	private List<SelectItem> apps;
	
	@PostConstruct
	public void init() {
		apps = new ArrayList<SelectItem>();
		apps.add(new SelectItem("SAP","sap"));
		apps.add(new SelectItem("DTOBM","dtobm"));
		apps.add(new SelectItem("DTKBM","dtkbm"));
	}
	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public List<SelectItem> getApps() {
		return apps;
	}

	public void setApps(List<SelectItem> apps) {
		this.apps = apps;
	}

	

}
