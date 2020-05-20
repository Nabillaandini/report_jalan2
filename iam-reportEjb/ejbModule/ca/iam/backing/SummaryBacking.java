package ca.iam.backing;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
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
import javax.inject.Named;

import org.jfree.data.time.Month;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

import ca.iam.core.BasicSessionBacking;
import ca.iam.eao.DtkbmEao;
import ca.iam.eao.DtobmEao;
import ca.iam.eao.UserEao;
import ca.iam.entity.SummaryModel;
import ca.iam.rules.UserRules;
import ca.iam.util.Helper;

import com.google.gson.Gson;

@ManagedBean
@SessionScoped
@Stateful
@Named

public class SummaryBacking extends BasicSessionBacking {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8732810632316651351L;

	@EJB
	public UserRules userRules;

	@EJB
	public UserEao userEao;

	@EJB
	private LeftmenuBacking leftmenuBacking;

	@EJB
	public DtobmEao dtobmEao;

	@EJB
	public DtkbmEao dtkbmEao;
	private int maxBar;
	private Date beginDate;
	private Date endDate;
	private String headName;
	SummaryModel sumModel = new SummaryModel();
	ArrayList <SummaryModel> detailSumModel = new ArrayList <SummaryModel>();
	
	String period1;
	Month periodA=null;
	String application;
	private BarChartModel barOnboard = new BarChartModel();
	private BarChartModel barResetDomain = new BarChartModel();
	private BarChartModel barOffboard = new BarChartModel();
	
	public String detailListJson = "";
	
	public String getDetailListJson() {
		return detailSumModel != null? new Gson().toJson(detailSumModel) : "";
	}
	
	public void setDetailListJson(String detailList) {
		this.detailListJson = detailList;
	}	
	
	public Month getPeriodA() {
		return periodA;
	}

	public void setPeriodA(Month periodA) {
		this.periodA = periodA;
	}

	public String getPeriod1() {
		return period1;
	}

	public void setPeriod1(String period1) {
		this.period1 = period1;
	}

	public ArrayList<SummaryModel> getDetailSumModel() {
		return detailSumModel;
	}

	public void setDetailSumModel(ArrayList<SummaryModel> detailSumModel) {
		this.detailSumModel = detailSumModel;
	}

	public BarChartModel getBarOnboard() {
		return barOnboard;
	}

	public void setBarOnboard(BarChartModel barOnboard) {
		this.barOnboard = barOnboard;
	}

	public BarChartModel getBarResetDomain() {
		return barResetDomain;
	}

	public void setBarResetDomain(BarChartModel barResetDomain) {
		this.barResetDomain = barResetDomain;
	}

	public BarChartModel getBarOffboard() {
		return barOffboard;
	}

	public void setBarOffboard(BarChartModel barOffboard) {
		this.barOffboard = barOffboard;
	}

	
	public String getHeadName() {
		return headName;
	}

	public void setHeadName(String headName) {
		this.headName = headName;
	}

	public void load(ComponentSystemEvent event) {
		FacesContext context = FacesContext.getCurrentInstance();
		if (!context.isPostback()) {
			if (leftmenuBacking.getAppsValue() == null) {
				leftmenuBacking.setAppsValue("SAP");
			}
			this.application = leftmenuBacking.getAppsValue();
			setHeadName("Report Summary ");

			this.beginDate = null;
			this.endDate = null;
			this.detailSumModel =null;
		}
	}

	public UserRules getUserRules() {
		return userRules;
	}

	public void setUserRules(UserRules userRules) {
		this.userRules = userRules;
	}

	public UserEao getUserEao() {
		return userEao;
	}

	public void setUserEao(UserEao userEao) {
		this.userEao = userEao;
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

	public SummaryModel getSumModel() {
		return sumModel;
	}

	public void setSumModel(SummaryModel sumModel) {
		this.sumModel = sumModel;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void getSummaryReport() {
		this.detailSumModel =null;
		System.out.println(this.period1);
		
		if (this.beginDate == null || this.endDate == null) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please fill in the required fields", "Error"));
		} else {
			try {
				
					this.detailSumModel= userEao.getSummaryReport(this.beginDate, this.endDate);
				
					createBarModelOnboard();
					createBarModelOffboard();
					
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
	
	
	private BarChartModel initBarModelOnboard() {
		BarChartModel model = new BarChartModel();
		 ChartSeries sap = new ChartSeries();
	        sap.setLabel("SAP");

	        ChartSeries dtobm = new ChartSeries();
	        dtobm.setLabel("DTOBM");
	        
	        ChartSeries dtkbm = new ChartSeries();
	        dtkbm.setLabel("DTKBM");
	        
        maxBar = 0;
        for(int i =0;i<detailSumModel.size();i++) {
        	int count = detailSumModel.get(i).getSumOnboardSAP();
        	sap.set(detailSumModel.get(i).getDate(), count );
        	maxBar = maxBar < count? count : maxBar;
        	
        	count = detailSumModel.get(i).getSumOnboardDTOBM();
        	dtobm.set(detailSumModel.get(i).getDate(), count );
        	maxBar = maxBar < count? count : maxBar;
        	
        	count = detailSumModel.get(i).getSumOnboardDTKBM() ;
        	dtkbm.set(detailSumModel.get(i).getDate(), count);
        	maxBar = maxBar < count? count : maxBar;
        	
        }
        
        model.addSeries(sap);
        model.addSeries(dtobm);
        model.addSeries(dtkbm);
 
        return model;
    }
 
 
    private void createBarModelOnboard() {
    	barOnboard = initBarModelOnboard();
 
    	barOnboard.setTitle("Onboard Trusted Source");
    	barOnboard.setLegendPosition("ne");
 
        Axis xAxis = barOnboard.getAxis(AxisType.X);
        xAxis.setLabel("Period");
 
        Axis yAxis = barOnboard.getAxis(AxisType.Y);
        yAxis.setLabel("Total");
        yAxis.setMin(0);
        yAxis.setMax(Helper.roundUpToNearestMultipleOfSix(maxBar));
    }
    
    private BarChartModel initBarModelOffboard() {
		BarChartModel model = new BarChartModel();
		 ChartSeries sap = new ChartSeries();
	        sap.setLabel("SAP");

	        ChartSeries dtobm = new ChartSeries();
	        dtobm.setLabel("DTOBM");
	        
	        ChartSeries dtkbm = new ChartSeries();
	        dtkbm.setLabel("DTKBM");
	        
        maxBar = 0;
        for(int i =0;i<detailSumModel.size();i++) {
        	int count = detailSumModel.get(i).getDeprovSAP();
        	sap.set(detailSumModel.get(i).getDate(), count );
        	maxBar = maxBar < count? count : maxBar;
        	
        	count = detailSumModel.get(i).getDeprovDTOBM();
        	dtobm.set(detailSumModel.get(i).getDate(), count );
        	maxBar = maxBar < count? count : maxBar;
        	
        	count = detailSumModel.get(i).getDeprovDTKBM();
        	dtkbm.set(detailSumModel.get(i).getDate(), count);
        	maxBar = maxBar < count? count : maxBar;
        	
        }
        
        model.addSeries(sap);
        model.addSeries(dtobm);
        model.addSeries(dtkbm);
 
        return model;
    }
 
 
    private void createBarModelOffboard() {
    	barOffboard = initBarModelOffboard();
 
    	barOffboard.setTitle("Offboard Trusted Source");
    	barOffboard.setLegendPosition("ne");
 
        Axis xAxis = barOnboard.getAxis(AxisType.X);
        xAxis.setLabel("Period");
 
        Axis yAxis = barOnboard.getAxis(AxisType.Y);
        yAxis.setLabel("Total");
        yAxis.setMin(0);
        yAxis.setMax(Helper.roundUpToNearestMultipleOfSix(maxBar));
    }
}
