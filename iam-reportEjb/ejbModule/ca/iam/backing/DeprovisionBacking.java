package ca.iam.backing;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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
import ca.iam.eao.DtkbmEao;
import ca.iam.eao.DtobmEao;
import ca.iam.eao.SummaryEao;
import ca.iam.eao.UserEao;
import ca.iam.entity.CountList;
import ca.iam.entity.Provision;
import ca.iam.entity.UserModel;
import ca.iam.entity.UserUpdates;
import ca.iam.rules.UserRules;
import ca.iam.util.Helper;

@ManagedBean
@SessionScoped
@Stateful
@Named

public class DeprovisionBacking extends BasicSessionBacking {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1149841774695559308L;

	@EJB
	public UserRules userRules;

	@EJB
	public UserEao userEao;

	@EJB
	public DtobmEao dtobmEao;

	@EJB
	public DtkbmEao dtkbmEao;
	
	@EJB
	public SummaryEao sumEao;

	@EJB
	private LeftmenuBacking leftmenuBacking;

	private Date beginDate;
	private Date endDate;
	private String application;

	private Date month1;
	private Date month2;

	public List<Provision> detailList = new ArrayList<Provision>();
	public List<CountList> countList = new ArrayList<CountList>();

	private String headSum;
	private String headDetail;

	private List<SelectItem> appsList;
	private String appsName;

	private String visibilityMonth = "block";
	private String visibilityWeek = "none";

	private List<SelectItem> periodList;
	private String period;
	
	private BarChartModel barModel = new BarChartModel();
	
	private int userActive = 0;
	
	private int maxBar;
	
	public String detailListJson = "";
	
	private int detailSize=0;
	

	
	public int getDetailSize() {
		return detailSize;
	}

	public void setDetailSize(int detailSize) {
		this.detailSize = detailSize;
	}

	
	public String getDetailListJson() {
		return detailList != null && !detailList.isEmpty() ? new Gson().toJson(detailList) : "";
	}
	
	public void setDetailListJson(String detailList) {
		this.detailListJson = detailList;
	}	
	public String countListJson = "";
	
	public String getcountListJson() {
		return countList != null && !countList.isEmpty()? new Gson().toJson(countList) : "";
	}
	
	public void setcountListJson(String countList) {
		this.countListJson = countList;
	}	
	
	
	public int getUserActive() {
		return userActive;
	}

	public void setUserActive(int userActive) {
		this.userActive = userActive;
	}

	public BarChartModel getBarModel() {
		return barModel;
	}

	public void setBarModel(BarChartModel barModel) {
		this.barModel = barModel;
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

	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public String getHeadSum() {
		return headSum;
	}

	public void setHeadSum(String headSum) {
		this.headSum = headSum;
	}

	public String getHeadDetail() {
		return headDetail;
	}

	public void setHeadDetail(String headDetail) {
		this.headDetail = headDetail;
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
		appsList.add(new SelectItem("SAP", "SAP"));
		appsList.add(new SelectItem("DTOBM", "DTOBM"));
		appsList.add(new SelectItem("DTKBM", "DTKBM"));
//		appsList.add(new SelectItem("ALL", "ALL"));
		periodList = new ArrayList<SelectItem>();
		periodList.add(new SelectItem("monthly", "monthly"));
		periodList.add(new SelectItem("weekly", "weekly"));
		periodList.add(new SelectItem("daily", "daily"));
		if (!context.isPostback()) {
			if (leftmenuBacking.getAppsValue() == null) {
				leftmenuBacking.setAppsValue("SAP");
			}
			this.application = leftmenuBacking.getAppsValue();
			setHeadSum("Summary Offboard User ");
			setHeadDetail("Detail Offboard User ");

			this.beginDate = null;
			this.endDate = null;
			this.detailList = null;
			this.countList = null;
			this.visibilityMonth = "block";
			this.visibilityWeek = "none";
			this.month1 = null;
			this.month2 = null;
			this.barModel = new BarChartModel();
			this.userActive = 0;
			this.countListJson = null;
			this.detailListJson = null;
			getcountListJson();
			this.period = "monthly";
			setVisibilityMonth("block");
			setVisibilityWeek("none");
		}
	}

	public void sumSearch() {
		System.out.println(period + " " + appsName);
		this.countList = null;
		this.barModel = new BarChartModel();
		if ((this.beginDate == null || this.endDate == null) && (this.month1 == null || this.month2 == null)) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please fill in the required fields", "Error"));
		} else {
			try {
				if (this.appsName.equalsIgnoreCase("SAP") && this.period.equalsIgnoreCase("daily") ) {
					this.countList = userEao.countDeprovision(this.beginDate, this.endDate);
				} else if (this.appsName.equalsIgnoreCase("DTOBM")&& this.period.equalsIgnoreCase("daily")) {
					this.countList = dtobmEao.countDeprovision(beginDate, endDate);
				} else if (this.appsName.equalsIgnoreCase("DTKBM")&& this.period.equalsIgnoreCase("daily")) {
					this.countList = dtkbmEao.countDeprovision(beginDate, endDate);
				}  
				else if (this.period.equalsIgnoreCase("monthly") && this.appsName.equalsIgnoreCase("SAP")) {
					System.out.println("masuk monthly SAP");
					this.countList = userEao.countDeprovMonthly(month1, month2);
					createBarModelApp(this.appsName);
				} else if (this.period.equalsIgnoreCase("monthly") && this.appsName.equalsIgnoreCase("DTOBM")) {
					System.out.println("masuk monthly DTOBM");
					this.countList = dtobmEao.countDeprovMonthly(month1, month2);
					createBarModelApp(this.appsName);
				} else if (this.period.equalsIgnoreCase("monthly") && this.appsName.equalsIgnoreCase("DTKBM")) {
					System.out.println("masuk monthly DTKBM");
					this.countList = dtkbmEao.countDeprovMonthly(month1, month2);
					createBarModelApp(this.appsName);
				}else if(this.period.equalsIgnoreCase("monthly") && this.appsName.equalsIgnoreCase("all")) {
					this.countList = sumEao.countDeprovMonthly(month1, month2);
					createBarModels();
				}else if (this.period.equalsIgnoreCase("weekly") && this.appsName.equalsIgnoreCase("all")) {
					this.countList = sumEao.countDeprovWeek(beginDate, endDate);
				}else if(this.period.equalsIgnoreCase("weekly") && this.appsName.equalsIgnoreCase("SAP")) {
					System.out.println("masuk weekly SAP");
					this.countList = userEao.countDeprovWeek(beginDate, endDate);
				}else if(this.period.equalsIgnoreCase("weekly") && this.appsName.equalsIgnoreCase("DTOBM")) {
					System.out.println("masuk weekly DTOBM");
					this.countList = dtobmEao.countDeprovWeek(beginDate, endDate);
				}else if(this.period.equalsIgnoreCase("weekly") && this.appsName.equalsIgnoreCase("DTKBM")) {
					this.countList = dtkbmEao.countDeprovWeek(beginDate, endDate);
				}
				getcountListJson();
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
		this.countList = null;
		this.detailList = null;
		if ((this.beginDate == null || this.endDate == null) && (this.month1 == null || this.month2 == null)) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please fill in the required fields", "Error"));
		} else {
			try {
				if (this.appsName.equalsIgnoreCase("SAP") && this.period.equalsIgnoreCase("daily")) {
					this.detailList = userEao.getDeprovData(this.beginDate, this.endDate);
				} else if (this.appsName.equalsIgnoreCase("DTOBM")  && this.period.equalsIgnoreCase("daily")) {
					System.out.println("masuk dtobm");
					this.detailList = dtobmEao.getDeprovData(beginDate, endDate);
				} else if (this.appsName.equalsIgnoreCase("DTKBM") && this.period.equalsIgnoreCase("daily")) {
					this.detailList = dtkbmEao.getDeprovData(beginDate, endDate);
				} else if (this.period.equalsIgnoreCase("monthly") && this.appsName.equalsIgnoreCase("SAP")) {
					this.detailList = userEao.getDeprovMonthly(month1, month2);
				} else if (this.period.equalsIgnoreCase("monthly") && this.appsName.equalsIgnoreCase("DTOBM")) {
					this.detailList = dtobmEao.getDeprovMonthly(month1, month2);
				} else if (this.period.equalsIgnoreCase("monthly") && this.appsName.equalsIgnoreCase("DTKBM")) {
					this.detailList = dtkbmEao.getDeprovMonthly(month1, month2);
				}else if(this.appsName.equalsIgnoreCase("SAP") && this.period.equalsIgnoreCase("weekly")) {
					this.detailList = userEao.getDeprovWeekly(beginDate, endDate);
				}else if(this.appsName.equalsIgnoreCase("DTOBM") && this.period.equalsIgnoreCase("weekly")) {
					this.detailList = dtobmEao.getDeprovWeekly(beginDate, endDate);
				}else if(this.appsName.equalsIgnoreCase("DTKBM") && this.period.equalsIgnoreCase("weekly")) {
					this.detailList = dtkbmEao.getDeprovWeekly(beginDate, endDate);
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
        ChartSeries sap = new ChartSeries();
        sap.setLabel("SAP");

        ChartSeries dtobm = new ChartSeries();
        dtobm.setLabel("DTOBM");
        
        ChartSeries dtkbm = new ChartSeries();
        dtkbm.setLabel("DTKBM");
        for(int i =0;i<countList.size();i++) {
        	if(countList.get(i).getAppsName().equalsIgnoreCase("SAP")) {
        	sap.set(countList.get(i).getDate(), countList.get(i).getCount());
        	}
        	else if(countList.get(i).getAppsName().equalsIgnoreCase("DTOBM")) {
        		dtobm.set(countList.get(i).getDate(), countList.get(i).getCount());
        	}else {
        		dtkbm.set(countList.get(i).getDate(), countList.get(i).getCount());
        		
        	}
        }
        
        model.addSeries(sap);
        model.addSeries(dtobm);
        model.addSeries(dtkbm);
 
        return model;
    }
 
    private void createBarModels() {
        createBarModel();
    }
 
    private void createBarModel() {
        barModel = initBarModel();
 
        barModel.setTitle("Monthly Offboard Chart");
        barModel.setLegendPosition("ne");
 
        Axis xAxis = barModel.getAxis(AxisType.X);
        xAxis.setLabel("Period");
 
        Axis yAxis = barModel.getAxis(AxisType.Y);
        yAxis.setLabel("Total");
        yAxis.setMin(0);
        yAxis.setMax(200);
    }
    
    private BarChartModel initBarModel(String appsName) {
		BarChartModel model = new BarChartModel();
        ChartSeries apps = new ChartSeries();
        apps.setLabel(appsName);
        maxBar = 0;
        for(int i =0;i<countList.size();i++) {
        	if(countList.get(i).getAppsName().equalsIgnoreCase(appsName)) {
        	apps.set(countList.get(i).getDate(), countList.get(i).getCount());
        	int count = countList.get(i).getCount();
			maxBar = maxBar < count? count : maxBar;
        	}
        }
        
        model.addSeries(apps);
        return model;
    }
    
   
 
    private void createBarModelApp(String appsName) {
        barModel = initBarModel(appsName);
 
        barModel.setTitle("Monthly Offboard Chart " + this.appsName);
        barModel.setLegendPosition("ne");
 
        Axis xAxis = barModel.getAxis(AxisType.X);
        xAxis.setLabel("Period");
 
        Axis yAxis = barModel.getAxis(AxisType.Y);
        yAxis.setLabel("Total");
        yAxis.setMin(0);
        yAxis.setMax(Helper.roundUpToNearestMultipleOfSix(maxBar));
    }
}
