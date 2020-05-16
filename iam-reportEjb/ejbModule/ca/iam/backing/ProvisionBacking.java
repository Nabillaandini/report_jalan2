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
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import ca.iam.core.BasicSessionBacking;
import ca.iam.eao.DtkbmEao;
import ca.iam.eao.DtobmEao;
import ca.iam.eao.ProvisionEao;
import ca.iam.eao.UserEao;
import ca.iam.entity.CountList;
import ca.iam.entity.Provision;
import ca.iam.entity.UserModel;
import ca.iam.entity.UserUpdates;
import ca.iam.rules.UserRules;

@ManagedBean
@SessionScoped
@Stateful
@Named

public class ProvisionBacking extends BasicSessionBacking {

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
	public ProvisionEao provEao;

	@EJB
	private LeftmenuBacking leftmenuBacking;

	private Date beginDate;
	private Date endDate;
	public String application;

	public List<Provision> detailList = new ArrayList<Provision>();
	public List<CountList> countList = new ArrayList<CountList>();

	private String headSum;
	private String headDetail;

	private List<SelectItem> appsList;
	private String appsName;

	private List<SelectItem> periodList;
	private String period;

	private String option;
	private String visibilityMonth = "block";
	private String visibilityWeek = "none";

	private Date month1;
	private Date month2;

	private boolean flagUsereao = false;
	private boolean flagApps = false;

	private BarChartModel barModel = new BarChartModel();

public String detailListJson = "";
	
	public String getDetailListJson() {
		return detailList != null? new Gson().toJson(detailList) : "";
	}
	
	public void setDetailListJson(String detailList) {
		this.detailListJson = detailList;
	}	
	public String countListJson = "";
	
	public String getcountListJson() {
		System.out.println(countList !=null ? countList.get(0).getDate():"IS NULL ");
		return countList != null? new Gson().toJson(countList) : "";
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

	public boolean isFlagUsereao() {
		return flagUsereao;
	}

	public void setFlagUsereao(boolean flagUsereao) {
		this.flagUsereao = flagUsereao;
	}

	public boolean isFlagApps() {
		return flagApps;
	}

	public void setFlagApps(boolean flagApps) {
		this.flagApps = flagApps;
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

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
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

		if (!context.isPostback()) {
			if (leftmenuBacking.getAppsValue() == null) {
				leftmenuBacking.setAppsValue("SAP");
			}
			if (this.appsName == null) {
				setAppsName("SAP");
			}
			this.application = leftmenuBacking.getAppsValue();
			setHeadSum("Summary User Application Request ");
			setHeadDetail("Detail User Application Request ");

			this.beginDate = null;
			this.endDate = null;
			this.detailList = null;
			this.countList = null;
			this.visibilityMonth = "block";
			this.visibilityWeek = "none";
			this.month1 = null;
			this.month2 = null;
			this.barModel = new BarChartModel();

		}
	}

	public void sumSearch() {
		this.countList = null;
		if (this.appsName.equalsIgnoreCase("SKD KPEI") || this.appsName.equalsIgnoreCase("AAP")
				|| this.appsName.equalsIgnoreCase("SAP SRM") || this.appsName.equalsIgnoreCase("SAP FICO")
				|| this.appsName.equalsIgnoreCase("SAP eBudgeting") || this.appsName.equalsIgnoreCase("SAP MM")) {
			this.flagUsereao = true;
		} else {
			this.flagApps = true;
		}

		this.application = leftmenuBacking.getAppsValue();
		if ((this.beginDate == null || this.endDate == null) && (this.month1 == null || this.month2 == null)) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please fill in the required fields", "Error"));
		} else {
			try {
				if (this.period.equalsIgnoreCase("monthly") && this.flagUsereao == true) {
					System.out.println("masuk eao" + this.appsName);
					
					this.countList = userEao.countProvMonthly(this.month1, this.month2, this.appsName);
					createBarModelApp(this.appsName);
				} else if (this.period.equalsIgnoreCase("monthly") && this.appsName.equalsIgnoreCase("DTOBM")) {
					this.countList = dtobmEao.countProvMonthly(this.month1, this.month2);
					createBarModelApp(this.appsName);
				} else if (this.period.equalsIgnoreCase("monthly") && this.appsName.equalsIgnoreCase("DTKBM")) {
					this.countList = dtkbmEao.countProvMonthly(this.month1, this.month2);
					createBarModelApp(this.appsName);
				} else if (this.period.equalsIgnoreCase("monthly") && this.flagApps == true) {
					
					this.countList = userEao.countProvMonthlyApps(this.month1, this.month2, this.appsName);
					createBarModelApp(this.appsName);
				}else if(this.period.equalsIgnoreCase("weekly") && this.flagUsereao) {
					this.countList = provEao.countProvWeekly(beginDate, endDate, appsName);
				}else if (this.period.equalsIgnoreCase("weekly") && this.flagApps) {
					this.countList = provEao.countProvWeeklyApps(beginDate, endDate, appsName);
				}else if (this.period.equalsIgnoreCase("daily") && this.flagUsereao) {
					this.countList = provEao.countProvision(beginDate, endDate, appsName);
				}else if(this.period.equalsIgnoreCase("daily") && this.flagApps) {
					this.countList = provEao.countProvisionApps(beginDate, endDate, appsName);
				}
				this.flagUsereao = false;
				this.flagApps = false;
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
		if (this.appsName.equalsIgnoreCase("SKD KPEI") || this.appsName.equalsIgnoreCase("AAP")
				|| this.appsName.equalsIgnoreCase("SAP SRM") || this.appsName.equalsIgnoreCase("SAP FICO")
				|| this.appsName.equalsIgnoreCase("SAP eBudgeting") || this.appsName.equalsIgnoreCase("SAP MM")) {
			this.flagUsereao = true;
		} else {
			this.flagApps = true;
		}

		if ((this.beginDate == null || this.endDate == null) && (this.month1 == null || this.month2 == null)) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please fill in the required fields", "Error"));
		}  else {
			try {

				if (this.period.equalsIgnoreCase("monthly") && this.flagUsereao == true) {
					this.detailList = provEao.getProvMonthly(month1, month2, this.appsName);

				} else if (this.period.equalsIgnoreCase("monthly") && this.isFlagApps() == true) {
					this.detailList = provEao.getProvMonthlyApps(month1, month2, this.appsName);
				} else if (this.period.equalsIgnoreCase("weekly") && this.flagUsereao == true) {
					this.detailList = provEao.getProvWeekly(beginDate, endDate, appsName);

				} else if (this.period.equalsIgnoreCase("weekly") && this.flagApps == true) {
					this.detailList = provEao.getProvWeeklyApps(beginDate, endDate, appsName);
				} else if (this.period.equalsIgnoreCase("daily") && this.flagUsereao) {
					this.detailList = provEao.getProvisionData(beginDate, endDate, this.appsName);
				} else if (this.period.equalsIgnoreCase("daily") && this.flagApps) {
					this.detailList = provEao.getProvisionDataApps(beginDate, endDate, appsName);
				}
				this.flagUsereao = false;
				this.flagApps = false;
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

	public void toFile() {
		SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
		String date = DATE_FORMAT.format(this.beginDate);
		String end = DATE_FORMAT.format(this.endDate);
		String path = "C:/report_result/provision_" + this.appsName + "_" + date + "_" + end + ".pdf";
		try {
			File file = new File(path);
			FileOutputStream fileout = new FileOutputStream(file);
			Document document = new Document();
			PdfWriter.getInstance(document, fileout);
			document.addAuthor("Me");

			document.open();

			Image image;
			try {
				image = Image.getInstance("C:/report_result/img/mandiri-logo.png");
				image.setAlignment(Image.MIDDLE);
				image.scaleToFit(200, 100);

				document.add(image);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			Font font = new Font();
			font.setStyle(Font.BOLD);
			font.setStyle(Font.ITALIC);
			font.setSize(24);
			document.addTitle("IAM User Provisioning Report : " + date);
			Paragraph paragraph1 = new Paragraph();
			paragraph1.add("IAM User Provisioning Report : " + this.appsName);
			paragraph1.add("\n");
			paragraph1.add("Tanggal :" + date + " - " + end + "\n");
			paragraph1.add("Total : " + this.detailList.size() + "\n");
			paragraph1.add("\n");
			paragraph1.setAlignment(Element.ALIGN_CENTER);
			paragraph1.setFont(font);
			document.add(paragraph1);
			Paragraph paragraph = new Paragraph();
			paragraph.setAlignment(Element.ALIGN_LEFT);
			float[] columnWidths = { 2.5f, 2.5f };
			// create PDF table with the given widths
			PdfPTable table = new PdfPTable(columnWidths);
			// set table width a percentage of the page width
			table.setWidthPercentage(90f);
			insertCell(table, "User Provisioning Approval Date", Element.ALIGN_CENTER, 1);
			insertCell(table, "User ID", Element.ALIGN_CENTER, 1);
			table.setHeaderRows(1);
			for (int i = 0; i < detailList.size(); i++) {
				insertCell(table, detailList.get(i).getDate(), Element.ALIGN_CENTER, 1);
				insertCell(table, detailList.get(i).getType(), Element.ALIGN_CENTER, 1);
			}
			paragraph.add(table);

			document.add(paragraph);

			document.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("File has been downloaded " + path));

	}

	private void insertCell(PdfPTable table, String text, int align, int colspan) {

		// create a new cell with the specified Text and Font
		PdfPCell cell = new PdfPCell(new Phrase(text.trim()));
		// set the cell alignment
		cell.setHorizontalAlignment(align);
		// set the cell column span in case you want to merge two or more cells
		cell.setColspan(colspan);
		// in case there is no text and you wan to create an empty row
		if (text.trim().equalsIgnoreCase("")) {
			cell.setMinimumHeight(10f);
		}
		// add the call to the table
		table.addCell(cell);

	}

	public void Search() {

		this.application = leftmenuBacking.getAppsValue();
		System.out.println(period);
	}

	public void updateSelected() {
		System.out.println("----------------------");
		System.out.println("period : " + period);

		switch (period) {
		case "weekly":
			setVisibilityWeek("block");
			setVisibilityMonth("none");
			System.out.println("weekly : " + visibilityWeek);
			break;
		case "monthly":
			setVisibilityWeek("none");
			setVisibilityMonth("block");
			System.out.println("weekly : " + visibilityWeek);
			break;
		case "daily":
			setVisibilityWeek("block");
			setVisibilityMonth("none");
			System.out.println("weekly : " + visibilityWeek);
			break;
		}
	}

	private BarChartModel initBarModel(String appsName) {
		BarChartModel model = new BarChartModel();
		ChartSeries apps = new ChartSeries();
		apps.setLabel(appsName);
		for (int i = 0; i < countList.size(); i++) {
			if (countList.get(i).getAppsName().equalsIgnoreCase(appsName)) {
				apps.set(countList.get(i).getDate(), countList.get(i).getCount());
			}
		}

		model.addSeries(apps);
		return model;
	}

	private void createBarModelApp(String appsName) {
		this.barModel = initBarModel(appsName);

		barModel.setTitle("Karyawan Success Request Aplikasi");
		barModel.setLegendPosition("ne");

		Axis xAxis = barModel.getAxis(AxisType.X);
		xAxis.setLabel("Period");

		Axis yAxis = barModel.getAxis(AxisType.Y);
		yAxis.setLabel("Total");
		yAxis.setMin(0);
		yAxis.setMax(200);
	}

}
