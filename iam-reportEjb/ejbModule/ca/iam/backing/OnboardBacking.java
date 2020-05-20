package ca.iam.backing;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
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
import ca.iam.eao.UserEao;
import ca.iam.entity.CountList;
import ca.iam.entity.UserModel;
import ca.iam.rules.UserRules;
import ca.iam.util.Helper;

@ManagedBean
@SessionScoped
@Stateful
@Named
public class OnboardBacking extends BasicSessionBacking {

	/**
	 * 
	 */
	private static final long serialVersionUID = 789259241105977794L;

	@EJB
	public UserRules userRules;

	@EJB
	public UserEao userEao;

	@EJB
	public DtobmEao dtobmEao;

	@EJB
	public DtkbmEao dtkbmEao;

	@EJB
	private LeftmenuBacking leftmenuBacking;
	
	private Date beginDate;
	private Date endDate;
	private String headSum;
	private String headDetail;
	
	public List<UserModel> detailList = new ArrayList<UserModel>();
	public List<CountList> countList = new ArrayList<CountList>();
	public String application;
	
	private String visibilityMonth = "block";
	private String visibilityWeek = "none";
	private int maxBar;

	private List<SelectItem> periodList;
	private String period;
	
	private BarChartModel barModel = new BarChartModel();
	
	private Date month1;
	private Date month2;
	
	private List<SelectItem> appsList;
	private String appsName;
	
	private int detailSize=0;
	

	
	public int getDetailSize() {
		return detailSize;
	}

	public void setDetailSize(int detailSize) {
		this.detailSize = detailSize;
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
public String detailListJson = "";
	
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


	public String getApplication() {
		System.out.println("get app: " + getApplication());
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
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

	public List<UserModel> getDetailList() {
		return detailList;
	}
	


	public void setDetailList(List<UserModel> detailList) {
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
		periodList = new ArrayList<SelectItem>();
		periodList.add(new SelectItem("monthly", "monthly"));
		periodList.add(new SelectItem("weekly", "weekly"));
		periodList.add(new SelectItem("daily", "daily"));
		if (!context.isPostback()) {
				if(leftmenuBacking.getAppsValue()==null) {
					leftmenuBacking.setAppsValue("SAP");
				}
				this.application = leftmenuBacking.getAppsValue();
				setHeadSum("Summary Onboard Trusted Source");
				setHeadDetail("Detail Onboard Trusted Source");
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
		this.maxBar= 0;
		if ((this.beginDate == null || this.endDate == null) && (this.month1 == null || this.month2 == null)) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please fill in the required fields", "Error"));
		}else {
			try {
				if(this.period.equalsIgnoreCase("daily") && this.appsName.equalsIgnoreCase("SAP")) {
					this.countList = userEao.countOnboardDaily(beginDate, endDate);
				}else if(this.period.equalsIgnoreCase("monthly") &&  this.appsName.equalsIgnoreCase("SAP")) {
					this.countList = userEao.countOnboardMonthly(month1, month2);
					createBarModels();
				}else if(this.period.equalsIgnoreCase("weekly") && this.appsName.equalsIgnoreCase("SAP")){
					this.countList = userEao.countOnboardWeekly(beginDate, endDate);
				}else if(this.period.equalsIgnoreCase("DTOBM") || this.period.equalsIgnoreCase("DTKBM") && this.period.equalsIgnoreCase("daily")) {
					this.countList = dtobmEao.countOnboardDailyNonSAP(beginDate, endDate, appsName);
				}else if(this.period.equalsIgnoreCase("DTOBM") || this.period.equalsIgnoreCase("DTKBM") && this.period.equalsIgnoreCase("weekly")) {
					this.countList = dtobmEao.countOnboardWeeklyNonSAP(beginDate, endDate, appsName);
				}else if(this.period.equalsIgnoreCase("DTOBM") || this.period.equalsIgnoreCase("DTKBM") && this.period.equalsIgnoreCase("monthly")) {
					this.countList = dtobmEao.countOnboardMonthlyNonSAP(beginDate, endDate, appsName);
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
		this.detailList = null;
		
		if ((this.beginDate == null || this.endDate == null) && (this.month1 == null || this.month2 == null)) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please fill in the required fields", "Error"));
		}else {
			try {
				if(this.period.equalsIgnoreCase("daily") &&  this.appsName.equalsIgnoreCase("SAP")) {
					this.detailList = userEao.getUserOnboard(this.beginDate, this.endDate);
					
				}else if(this.period.equalsIgnoreCase("monthly")&&  this.appsName.equalsIgnoreCase("SAP")) {
					this.detailList = userEao.getUserOnboardMonthly(month1, month2);
				}else if(this.period.equalsIgnoreCase("weekly")&&  this.appsName.equalsIgnoreCase("SAP")){
					this.detailList = userEao.getUserOnboardWeekly(beginDate, endDate);
				}else if(this.period.equalsIgnoreCase("daily") &&  this.period.equalsIgnoreCase("DTOBM") || this.period.equalsIgnoreCase("DTKBM")) {
					this.detailList = dtobmEao.getUserOnboard(this.beginDate, this.endDate,this.appsName);
					
				}else if(this.period.equalsIgnoreCase("monthly")&&  this.period.equalsIgnoreCase("DTOBM") || this.period.equalsIgnoreCase("DTKBM")) {
					this.detailList = dtobmEao.getUserOnboardMonthly(month1, month2,this.appsName);
				}else if(this.period.equalsIgnoreCase("weekly")&&  this.period.equalsIgnoreCase("DTOBM") || this.period.equalsIgnoreCase("DTKBM")){
					this.detailList = dtobmEao.getUserOnboardWeekly(beginDate, endDate,this.appsName);
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
        ChartSeries onboard = new ChartSeries();
        onboard.setLabel("Onboard");
        maxBar = 0;
        for(int i =0;i<countList.size();i++) {
        	int count = countList.get(i).getCount();
			maxBar = maxBar < count? count : maxBar;
        	onboard.set(countList.get(i).getDate(), countList.get(i).getCount());
        	
        }
        
        model.addSeries(onboard);
 
        return model;
    }
 
    private void createBarModels() {
        createBarModel();
    }
 
    private void createBarModel() {
        barModel = initBarModel();
 
        barModel.setTitle("Onboard Trusted Source " + this.appsName);
        barModel.setLegendPosition("ne");
 
        Axis xAxis = barModel.getAxis(AxisType.X);
        xAxis.setLabel("Period");
 
        Axis yAxis = barModel.getAxis(AxisType.Y);
        yAxis.setLabel("Total");
        yAxis.setMin(0);
        yAxis.setMax(Helper.roundUpToNearestMultipleOfSix(maxBar));
    }
    
    
}
