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

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.time.Month;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

import com.google.gson.Gson;
import com.itextpdf.awt.DefaultFontMapper;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.html.WebColors;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter; 
import com.itextpdf.layout.element.Table; 
import com.itextpdf.layout.property.TextAlignment;  


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

	private boolean flagSearch = false;

	private List<SelectItem> appsList;
	private String appsName;

	private String visibilityMonth = "block";
	private String visibilityWeek = "none";

	private List<SelectItem> periodList;
	private String period;
	
	private BarChartModel barModel = new BarChartModel();
	
	private int userActive = 0;
	
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
			
//			try {
//				System.out.println("masuk try ke 2");
//				userActive = sumEao.getUserActive(this.appsName);
//				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Searching is finished"));
//
//			} catch (SQLException e) {
//
//				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Database connection error"));
//
//				e.printStackTrace();
//			} catch (ParseException e) {
//				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Reading input failed"));
//				e.printStackTrace();
//			}
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
		String path = "C:/report_result/deprov_" + date + "_" + end + ".pdf";
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
			document.addTitle("IAM User Deprovisioning Report : " + date);
			Paragraph paragraph1 = new Paragraph();
			paragraph1.add("IAM User Deprovisioning Report : ");
			paragraph1.add("\n");
			paragraph1.add("Tanggal :" + date + " - " + end + "\n");
			paragraph1.add("Total User Deprov : " + detailList.size() + "\n");
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
			insertCell(table, "User Deprovisioning Approval Date", Element.ALIGN_CENTER, 1);
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
	public void sumtoFile() {
		SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
		String path="";
		String date="";
		String end="";
		if(this.period.equalsIgnoreCase("monthly")) {
			System.out.println("masuk monthly");
			date = DATE_FORMAT.format(this.month1);
			String[] arr = date.split("-");
			String month_a = arr[1];
			String year_a = arr[2];
			end = DATE_FORMAT.format(this.month2);
			arr= end.split("-");
			String month_b = arr[1];
			String year_b = arr[2];
			 path="C:/report_result/summarydeprov_" +month_a+year_a+ "_"+ month_b+year_b + ".pdf";
			
		}
		
		else if(this.period.equalsIgnoreCase("daily")) {
			System.out.println("masuk masuk daily");
		date = DATE_FORMAT.format(this.beginDate);
		 end = DATE_FORMAT.format(this.endDate);
		path = "C:/report_result/sumdeprov_" + date + "_" + end + ".pdf";
		}
		
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
			document.addTitle("IAM Offboard Report : " + date);
			Paragraph paragraph1 = new Paragraph();
			paragraph1.add("IAM Offboard Report : ");
			paragraph1.add("\n");
			paragraph1.add("periode :" + date + " - " + end + "\n");
			paragraph1.add("\n");
			paragraph1.setAlignment(Element.ALIGN_CENTER);
			paragraph1.setFont(font);
			document.add(paragraph1);
			Paragraph paragraph = new Paragraph();
			paragraph.setAlignment(Element.ALIGN_LEFT);
			float[] columnWidths = { 2.5f, 2.5f , 2.5f};
			// create PDF table with the given widths
			PdfPTable table = new PdfPTable(columnWidths);
			// set table width a percentage of the page width
			table.setWidthPercentage(90f);
			table.getDefaultCell().setBackgroundColor(new BaseColor(189, 255, 243));
			 PdfPCell cell1 = new PdfPCell(new Paragraph(
                     "Cell 1 - GREEN Background"));

        /* Set Background colour */
        cell1.setBackgroundColor(BaseColor.GREEN);
			insertCell(table, "Period", Element.ALIGN_CENTER, 1);
			insertCell(table, "Total", Element.ALIGN_CENTER, 1);
			insertCell(table, "Aplikasi", Element.ALIGN_CENTER, 1);
			table.setHeaderRows(1);
		
			for (int i = 0; i < countList.size(); i++) {
				System.out.println("masuk countlist");
				table.getDefaultCell().setBackgroundColor(new BaseColor(255, 255, 255));
				insertCell(table, countList.get(i).getDate(), Element.ALIGN_CENTER, 1);
				insertCell(table, Integer.toString(countList.get(i).getCount()), Element.ALIGN_CENTER, 1);
				insertCell(table, countList.get(i).getAppsName(), Element.ALIGN_CENTER, 1);
			}
			System.out.println("after loop");
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

	public JFreeChart generateBarChart() {
		DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
		for(int i=0;i<this.countList.size();i++) {
			dataSet.setValue(this.countList.get(i).getCount(),"Period",this.countList.get(i).getAppsName() + " " +this.countList.get(i).getDate());
		}

		JFreeChart chart = ChartFactory.createBarChart(
				"Offboard User", "Period", "Total",
				dataSet, PlotOrientation.VERTICAL, false, true, false);

		return chart;
	}
	public void exportChart() throws FileNotFoundException, DocumentException {
		System.out.println("masuk export chart");
		writeChartToPDF(generateBarChart(), "C://barchart.pdf");
		
	}
	public void writeChartToPDF(JFreeChart chart, String fileName) throws FileNotFoundException, DocumentException {
		String pdfFilePath = fileName;
        OutputStream fos = new FileOutputStream(new File(pdfFilePath));
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, fos);

        document.open();
        
        PdfContentByte pdfContentByte = writer.getDirectContent();
        int width = 400; //width of BarChart
        int height = 500; //height of BarChart
        PdfTemplate pdfTemplate = pdfContentByte.createTemplate(width, height);
        
        //create graphics
        Graphics2D graphics2d = pdfTemplate.createGraphics(width, height,
                     new DefaultFontMapper());
        
        //create rectangle
        java.awt.geom.Rectangle2D rectangle2d = new java.awt.geom.Rectangle2D.Double(
                     0, 0, width, height);

        chart.draw(graphics2d, rectangle2d);

        graphics2d.dispose();
        pdfContentByte.addTemplate(pdfTemplate, 40, 500); //0, 0 will draw BAR chart on bottom left of page

        document.close();
        System.out.println("PDF created in >> " + pdfFilePath);
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
        for(int i =0;i<countList.size();i++) {
        	if(countList.get(i).getAppsName().equalsIgnoreCase(appsName)) {
        	apps.set(countList.get(i).getDate(), countList.get(i).getCount());
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
        yAxis.setMax(200);
    }
}
