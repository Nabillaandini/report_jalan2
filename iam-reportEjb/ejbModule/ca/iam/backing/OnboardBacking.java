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

import org.jfree.util.LineBreakIterator;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

import com.google.gson.Gson;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.log.SysoCounter;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

import ca.iam.core.BasicSessionBacking;
import ca.iam.eao.DtkbmEao;
import ca.iam.eao.DtobmEao;
import ca.iam.eao.UserEao;
import ca.iam.entity.CountList;
import ca.iam.entity.SelectOneMenuView;
import ca.iam.entity.UserModel;
import ca.iam.entity.UserUpdates;
import ca.iam.rules.UserRules;

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

	private List<SelectItem> periodList;
	private String period;
	
	private BarChartModel barModel = new BarChartModel();
	
	private Date month1;
	private Date month2;

	
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
	
	


	public void toFile() {
		SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
		String date = DATE_FORMAT.format(this.beginDate);
		String end = DATE_FORMAT.format(this.endDate);
		String path="C:/report_result/onboard_" +date+ "_"+ end + ".pdf";
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
//			Chunk chunk = new Chunk("Summary report "+ date);
			Font font = new Font();
			font.setStyle(Font.BOLD);
			font.setStyle(Font.ITALIC);
			font.setSize(24);
//			chunk.setFont(font);
//			document.add(chunk);
			document.addTitle("IAM Onboard Report: " + date);
			Paragraph paragraph1 = new Paragraph();
			paragraph1.add("IAM ONBOARD REPORT");
			paragraph1.add("\n");
			paragraph1.add("Tanggal :" + date + " - " + end +"\n");
			paragraph1.add("Total User Onboard : " + detailList.size() +"\n");
			paragraph1.add("\n");
			paragraph1.setAlignment(Element.ALIGN_CENTER);
			paragraph1.setFont(font);
			document.add(paragraph1);
			Paragraph paragraph = new Paragraph();
			paragraph.setAlignment(Element.ALIGN_LEFT);
			float[] columnWidths = { 2.5f, 2.5f};
			// create PDF table with the given widths
			PdfPTable table = new PdfPTable(columnWidths);
			// set table width a percentage of the page width
			table.setWidthPercentage(90f);
			insertCell(table, "Date", Element.ALIGN_CENTER, 1);
			insertCell(table, "Username", Element.ALIGN_CENTER, 1);
			table.setHeaderRows(1);
			for (int i = 0; i < detailList.size(); i++) {
				insertCell(table, detailList.get(i).getDate(), Element.ALIGN_CENTER, 1);
				insertCell(table, detailList.get(i).getName(), Element.ALIGN_CENTER, 1);
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
		periodList = new ArrayList<SelectItem>();
		periodList.add(new SelectItem("monthly", "monthly"));
		periodList.add(new SelectItem("weekly", "weekly"));
		periodList.add(new SelectItem("daily", "daily"));
		if (!context.isPostback()) {
				if(leftmenuBacking.getAppsValue()==null) {
					leftmenuBacking.setAppsValue("SAP");
				}
				this.application = leftmenuBacking.getAppsValue();
				setHeadSum("Summary Onboard ");
				setHeadDetail("Detail Onboard ");
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
		this.barModel = new BarChartModel();
		if ((this.beginDate == null || this.endDate == null) && (this.month1 == null || this.month2 == null)) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please fill in the required fields", "Error"));
		}else {
			try {
				if(this.period.equalsIgnoreCase("daily")) {
					this.countList = userEao.countOnboardDaily(beginDate, endDate);
				}else if(this.period.equalsIgnoreCase("monthly")) {
					this.countList = userEao.countOnboardMonthly(month1, month2);
					createBarModels();
				}else {
					this.countList = userEao.countOnboardWeekly(beginDate, endDate);
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
				if(this.period.equalsIgnoreCase("daily")) {
					this.detailList = userEao.getUserOnboard(this.beginDate, this.endDate);
					
				}else if(this.period.equalsIgnoreCase("monthly")) {
					this.detailList = userEao.getUserOnboardMonthly(month1, month2);
				}else {
					this.detailList = userEao.getUserOnboardWeekly(beginDate, endDate);
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
        for(int i =0;i<countList.size();i++) {
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
 
        barModel.setTitle("Onboard Karyawan Permanent");
        barModel.setLegendPosition("ne");
 
        Axis xAxis = barModel.getAxis(AxisType.X);
        xAxis.setLabel("Period");
 
        Axis yAxis = barModel.getAxis(AxisType.Y);
        yAxis.setLabel("Total");
        yAxis.setMin(0);
        yAxis.setMax(200);
    }
    
    
}
