package ca.iam.backing;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.model.SelectItem;
import javax.inject.Named;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

import com.google.gson.Gson;

import ca.iam.core.BasicSessionBacking;
import ca.iam.eao.ChangeEao;
import ca.iam.eao.UserEao;
import ca.iam.entity.CountList;
import ca.iam.entity.Provision;
import ca.iam.rules.UserRules;
import ca.iam.util.Helper;

@ManagedBean
@SessionScoped
@Stateful
@Named

public class ChangeBacking extends BasicSessionBacking {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1149841774695559308L;

	@EJB
	public UserRules userRules;

	@EJB
	public UserEao userEao;

	@EJB
	public ChangeEao changeEao;

	private Date beginDate;
	private Date endDate;

	private Date month1;
	private Date month2;

	public List<Provision> detailList = new ArrayList<Provision>();
//	public List<ResetModel> countList = new ArrayList<ResetModel>();
	public List<CountList> countList = new ArrayList<CountList>();
	public List<Provision> detailListApps = new ArrayList<Provision>();
	private List<SelectItem> appsList;
	private String appsName;

	private List<SelectItem> periodList;
	private String period;

	private List<SelectItem> typeList;
	private String type;
	private int maxBar;
	private String visibilityMonth = "block";
	private String visibilityWeek = "none";
	private BarChartModel barModel = new BarChartModel();
	
private int detailSize=0;
	

	
	public int getDetailSize() {
		return detailSize;
	}

	public void setDetailSize(int detailSize) {
		this.detailSize = detailSize;
	}

public String detailListAppsJson = "";

	
	public String getDetailListAppsJson() {
		return detailListApps!= null  && !detailListApps.isEmpty() ? new Gson().toJson(detailListApps) : "";
	}
	
	public void setDetailListAppsJson(String detailList) {
		this.detailListJson = detailList;
	}	
public String detailListJson = "";
	
	public String getDetailListJson() {
		return detailList != null && !detailList.isEmpty() ? new Gson().toJson(detailList) : "";
	}
	
	public void setDetailListJson(String detailList) {
		this.detailListJson = detailList;
	}	
	public String countListJson = "";
	
	public String getCountListJson() {
		return countList != null && !countList.isEmpty()? new Gson().toJson(countList) : "";
	}
	
	public void setCountListJson(String countList) {
		this.countListJson = countList;
	}	
	
	public BarChartModel getBarModel() {
		return barModel;
	}

	public void setBarModel(BarChartModel barModel) {
		this.barModel = barModel;
	}

	public List<SelectItem> getTypeList() {
		return typeList;
	}

	public void setTypeList(List<SelectItem> typeList) {
		this.typeList = typeList;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getVisibilityMonth() {
		return visibilityMonth;
	}

	public void setVisibilityMonth(String visibilityMonth) {
		this.visibilityMonth = visibilityMonth;
	}

	public String getVisibilityWeek() {
		return visibilityWeek;
	}

	public void setVisibilityWeek(String visibilityWeek) {
		this.visibilityWeek = visibilityWeek;
	}

	public Date getMonth1() {
		return month1;
	}

	public void setMonth1(Date month1) {
		this.month1 = month1;
	}

	public Date getMonth2() {
		return month2;
	}

	public void setMonth2(Date month2) {
		this.month2 = month2;
	}

	public List<SelectItem> getPeriodList() {
		return periodList;
	}

	public void setPeriodList(List<SelectItem> periodList) {
		this.periodList = periodList;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public List<SelectItem> getAppsList() {
		return appsList;
	}

	public void setAppsList(List<SelectItem> appsList) {
		this.appsList = appsList;
	}

	public String getAppsName() {
		return appsName;
	}

	public void setAppsName(String appsName) {
		this.appsName = appsName;
	}

	public List<Provision> getDetailListApps() {
		return detailListApps;
	}

	public void setDetailListApps(List<Provision> detailListApps) {
		this.detailListApps = detailListApps;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public List<Provision> getDetailList() {
		return detailList;
	}

	public void setDetailList(List<Provision> detailList) {
		this.detailList = detailList;
	}

	public List<CountList> getCountList() {
		return countList;
	}

	public void setCountList(List<CountList> countList) {
		this.countList = countList;
	}

	public void load(ComponentSystemEvent event) {
		FacesContext context = FacesContext.getCurrentInstance();
		appsList = new ArrayList<SelectItem>();
		appsList = new ArrayList<SelectItem>();
		appsList.add(new SelectItem("SAP eBudgeting", "SAP eBudgeting"));
		appsList.add(new SelectItem("SAP eHCMS", "SAP eHCMS"));
		appsList.add(new SelectItem("SAP FICO", "SAP FICO"));
		appsList.add(new SelectItem("SAP MM", "SAP MM"));
		appsList.add(new SelectItem("SAP SRM", "SAP SRM"));
		appsList.add(new SelectItem("DTOBM", "DTOBM"));
		appsList.add(new SelectItem("DTKBM", "DTKBM"));
		appsList.add(new SelectItem("SKD KPEI", "SKD KPEI"));
		appsList.add(new SelectItem("AAP", "AAP"));
		appsList.add(new SelectItem("MMS", "MMS"));
		appsList.add(new SelectItem("RAOS", "RAOS"));
		appsList.add(new SelectItem("Web Bill Gateway", "Web Bill Gateway"));
		appsList.add(new SelectItem("Web Corporate Payable", "Web Corporate Payable"));

		periodList = new ArrayList<SelectItem>();
		periodList.add(new SelectItem("monthly", "monthly"));
		periodList.add(new SelectItem("weekly", "weekly"));
		periodList.add(new SelectItem("daily", "daily"));

		typeList = new ArrayList<SelectItem>();
		typeList.add(new SelectItem("single reset", "single reset"));
		typeList.add(new SelectItem("multiple reset", "multiple reset"));

		if (!context.isPostback()) {
			this.beginDate = null;
			this.endDate = null;
			this.detailList = null;
			this.countList = null;
			this.visibilityMonth = "block";
			this.visibilityWeek = "none";
			this.month1 = null;
			this.month2 = null;
			this.barModel = new BarChartModel();
			this.period = "monthly";
			setVisibilityMonth("block");
			setVisibilityWeek("none");
			
		}
	}

	public void sumSearch() {
		this.countList = null;
		this.barModel = new BarChartModel();
		if ((this.beginDate == null || this.endDate == null) && (this.month1 == null || this.month2 == null)) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please fill in the required fields", "Error"));
		} else {
			try {
				if (this.period.equalsIgnoreCase("monthly") && type.equalsIgnoreCase("single reset")) {

					this.countList = changeEao.singleResetADMonthly(month1, month2);
					createBarModels();
				} else if (this.period.equalsIgnoreCase("monthly") && type.equalsIgnoreCase("multiple reset")) {
					this.countList = changeEao.multiResetADMonthly(month1, month2);
					createBarModels();
				}else if (this.period.equalsIgnoreCase("daily") && type.equalsIgnoreCase("single reset")) {
					this.countList = changeEao.singleResetPassADDaily(beginDate,endDate);
				}else if (this.period.equalsIgnoreCase("daily") && type.equalsIgnoreCase("multiple reset")) {
					this.countList = changeEao.multiResetPassADDaily(beginDate,endDate);
				}else if (this.period.equalsIgnoreCase("weekly") && type.equalsIgnoreCase("single reset")) {
					this.countList = changeEao.singleResetPassADWeekly(beginDate,endDate);
				}else if (this.period.equalsIgnoreCase("weekly") && type.equalsIgnoreCase("multiple reset")) {
					this.countList = changeEao.multiResetPassADWeekly(beginDate,endDate);
				}
				
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Searching is finished"));

			} catch (SQLException e) {

				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Database connection error"));

				e.printStackTrace();
			} catch (ParseException e) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Reading input failed"));
				e.printStackTrace();
			}
		}
	}

	public void sumSearchApps() {
		this.countList = null;
		this.barModel = new BarChartModel();
		if ((this.beginDate == null || this.endDate == null) && (this.month1 == null || this.month2 == null)) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please fill in the required fields", "Error"));
		} else {
			try {
				if(this.period.equalsIgnoreCase("monthly") && this.type.equalsIgnoreCase("single reset")) {
					this.countList = changeEao.singleResetPassAPPMonthly(month1, month2, this.appsName);
					createBarModelApps(this.appsName);
				}else if(this.period.equalsIgnoreCase("monthly") && this.type.equalsIgnoreCase("multiple reset")) {
					this.countList = changeEao.multiResetPassAPPMonthly(month1, month2, appsName);
					createBarModelApps(this.appsName);
				}else if(this.period.equalsIgnoreCase("weekly") && this.type.equalsIgnoreCase("single reset")) {
					this.countList = changeEao.singleResetPassAPPWeekly(beginDate, endDate, appsName);
				}else if (this.period.equalsIgnoreCase("weekly") && this.type.equalsIgnoreCase("multiple reset")) {
					this.countList = changeEao.multiResetPassAPPWeekly(beginDate, endDate, appsName);
				}else if (this.period.equalsIgnoreCase("daily") && this.type.equalsIgnoreCase("single reset")) {
					this.countList = changeEao.singleResetPassAppsDaily(beginDate, endDate, appsName);
				}else if (this.period.equalsIgnoreCase("daily") && this.type.equalsIgnoreCase("multiple reset")) {
					this.countList = changeEao.multiResetPassAppsDaily(beginDate, endDate, appsName);
				}
				
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Searching is finished"));

			} catch (SQLException e) {

				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Database connection error"));

				e.printStackTrace();
			} catch (ParseException e) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Reading input failed"));
				e.printStackTrace();
			}
		}
	}
	public void detailSearch() {
		if ((this.beginDate == null || this.endDate == null) && (this.month1 == null || this.month2 == null)) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please fill in the required fields", "Error"));
		} else {
			try {
				if(this.period.equalsIgnoreCase("weekly") && this.type.equalsIgnoreCase("single reset")) {
					this.detailList = changeEao.getsingleResetPassADWeekly(beginDate, endDate);
				}else if(this.period.equalsIgnoreCase("weekly") && this.type.equalsIgnoreCase("multiple reset")) {
					this.detailList = changeEao.getmultiResetPassADWeekly(beginDate, endDate);
				}else if(this.period.equalsIgnoreCase("monthly") && this.type.equalsIgnoreCase("single reset")) {
					this.detailList = changeEao.getsingleResetPassADMonthly(month1,month2);
					
				}else if(this.period.equalsIgnoreCase("monthly") && this.type.equalsIgnoreCase("multiple reset")) {
					this.detailList = changeEao.getmultiResetPassADMonthly(month1,month2);
				}else if(this.period.equalsIgnoreCase("daily") && this.type.equalsIgnoreCase("single reset")) {
					this.detailList = changeEao.getsingleResetPassADDaily(beginDate, endDate);
				}else if (this.period.equalsIgnoreCase("daily") && this.type.equalsIgnoreCase("multiple reset")) {
					this.detailList = changeEao.getmultiResetPassADDaily(beginDate, endDate);
				}
				this.detailSize = 0;
				this.detailSize = detailList.size();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Searching is finished"));

			} catch (SQLException e) {

				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Database connection error"));

				e.printStackTrace();
			} catch (ParseException e) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Reading input failed"));
				e.printStackTrace();
			}
		}
	}

	public void detailSearchApps() {
		if ((this.beginDate == null || this.endDate == null) && (this.month1 == null || this.month2 == null)) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please fill in the required fields", "Error"));
		}else {
			try {
				if(this.period.equalsIgnoreCase("monthly") && this.type.equalsIgnoreCase("single reset")) {
					this.detailListApps  = changeEao.getsingleResetPassAPPMonthly(month1, month2, appsName);
				}else if(this.period.equalsIgnoreCase("monthly") && this.type.equalsIgnoreCase("multiple reset")) {
					this.detailListApps  = changeEao.getmultiResetPassAPPMonthly(month1, month2, appsName);
				}else if(this.period.equalsIgnoreCase("weekly") && this.type.equalsIgnoreCase("single reset")) {
					this.detailListApps  = changeEao.getsingleResetPassAPPWeekly(beginDate, endDate, appsName);
				}else if(this.period.equalsIgnoreCase("weekly") && this.type.equalsIgnoreCase("multiple reset")) {
					this.detailListApps  = changeEao.getmultiResetPassAPPWeekly(beginDate, endDate, appsName);
				}else if(this.period.equalsIgnoreCase("daily") && this.type.equalsIgnoreCase("single reset")) {
					this.detailListApps  = changeEao.getsingleResetPassAppsDaily(beginDate, endDate, appsName);
				}else if(this.period.equalsIgnoreCase("daily") && this.type.equalsIgnoreCase("multiple reset")) {
					this.detailListApps  = changeEao.getmultiResetPassAppsDaily(beginDate, endDate, appsName);
				}
				this.detailSize = 0;
				this.detailSize = detailListApps.size();
				
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Searching is finished"));

			} catch (SQLException e) {

				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Database connection error"));

				e.printStackTrace();
			} catch (ParseException e) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Reading input failed"));
				e.printStackTrace();
			}
		}
	}

	
	public void updateSelected() {

		switch (period) {
		case "weekly":
			setVisibilityWeek("block");
			setVisibilityMonth("none");
			break;
		case "monthly":
			setVisibilityWeek("none");
			setVisibilityMonth("block");
			break;
		case "daily":
			setVisibilityWeek("block");
			setVisibilityMonth("none");
			break;
		}
	}
	
	private BarChartModel initBarModel() {
		BarChartModel model = new BarChartModel();
        ChartSeries onboard = new ChartSeries();
        onboard.setLabel("Reset Password Domain");
        maxBar = 0;
        for(int i =0;i<countList.size();i++) {
        	onboard.set(countList.get(i).getDate(), countList.get(i).getCount());
        	int count = countList.get(i).getCount();
			maxBar = maxBar < count? count : maxBar;
        	
        }
        
        model.addSeries(onboard);
 
        return model;
    }
 
    private void createBarModels() {
        createBarModel();
    }
 
    private void createBarModel() {
        barModel = initBarModel();
 
        barModel.setTitle("Domain Password Reset ("+this.type+")");
        barModel.setLegendPosition("ne");
 
        Axis xAxis = barModel.getAxis(AxisType.X);
        xAxis.setLabel("Period");
 
        Axis yAxis = barModel.getAxis(AxisType.Y);
        yAxis.setLabel("Total");
        yAxis.setMin(0);
        yAxis.setMax(Helper.roundUpToNearestMultipleOfSix(maxBar));
    }
    

    private void createBarModelApps(String apps) {
        barModel = initBarModel(apps);
 
        barModel.setTitle(apps+" Password Reset ("+this.type+")");
        barModel.setLegendPosition("ne");
 
        Axis xAxis = barModel.getAxis(AxisType.X);
        xAxis.setLabel("Period");
 
        Axis yAxis = barModel.getAxis(AxisType.Y);
        yAxis.setLabel("Total");
        yAxis.setMin(0);
    	yAxis.setMax(Helper.roundUpToNearestMultipleOfSix(maxBar));
    }
    
	private BarChartModel initBarModel(String apps) {
		BarChartModel model = new BarChartModel();
        ChartSeries onboard = new ChartSeries();
        onboard.setLabel("Reset Password " + apps);
        maxBar= 0;
        for(int i =0;i<countList.size();i++) {
        	onboard.set(countList.get(i).getDate(), countList.get(i).getCount());
        	int count = countList.get(i).getCount();
			maxBar = maxBar < count? count : maxBar;
        }
        
        model.addSeries(onboard);
 
        return model;
    }
 
}
