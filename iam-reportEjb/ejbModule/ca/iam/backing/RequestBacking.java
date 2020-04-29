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
import ca.iam.entity.UserRequest;
import ca.iam.entity.UserUpdates;
import ca.iam.rules.UserRules;

@ManagedBean
@SessionScoped
@Stateful
@Named
public class RequestBacking extends BasicSessionBacking {

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
	
	public List<UserRequest> detailList = new ArrayList<UserRequest>();
	public List<UserRequest> countList = new ArrayList<UserRequest>();
	public String application;
	 private List<SelectItem> appsList;
	 private String appsName;
	 
	 
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
		System.out.println("get app: " + getApplication());
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}
	
	public void sumToFile() {
		toFile("C:/report_result/sumReq_"+this.appsName,"User Application Request Summary ", "Total", countList);
	}
	public void detailToFile(){
		toFile("C:/report_result/userRequest_"+this.appsName,"User Application Request Detail ", "Username", detailList);
	}


	public void toFile(String fileName,String title, String col3, List<UserRequest> list) {
		SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
		String date = DATE_FORMAT.format(this.beginDate);
		String end = DATE_FORMAT.format(this.endDate);
		String path=fileName+date+ "_"+ end + ".pdf";
	
		try {
			File file = new File(path);
			FileOutputStream fileout = new FileOutputStream(file);
			Document document = new Document();
			PdfWriter.getInstance(document, fileout);
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
			Paragraph paragraph1 = new Paragraph();
			paragraph1.add(this.appsName + " "+title+"\n");
			paragraph1.add("Tanggal : "+ date + "- " + end +"\n");
			paragraph1.add("Total : "  + list.size()+"\n");
			paragraph1.add("\n");
			paragraph1.setAlignment(Element.ALIGN_CENTER);
			paragraph1.setFont(font);
			paragraph1.add(Chunk.NEWLINE);
			document.add(paragraph1);
			Paragraph paragraph = new Paragraph();
			paragraph.setAlignment(Element.ALIGN_LEFT);
			float[] columnWidths = { 2.5f, 2.5f};
			// create PDF table with the given widths
			PdfPTable table = new PdfPTable(columnWidths);
			// set table width a percentage of the page width
			table.setWidthPercentage(90f);
			insertCell(table, "Date", Element.ALIGN_CENTER, 1);
			insertCell(table, col3, Element.ALIGN_CENTER, 1);
			table.setHeaderRows(1);
			for (int i = 0; i < list.size(); i++) {
				insertCell(table, list.get(i).getDate(), Element.ALIGN_CENTER, 1);
				insertCell(table, list.get(i).getTemp(), Element.ALIGN_CENTER, 1);
				
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

	

	public List<UserRequest> getDetailList() {
		return detailList;
	}

	public void setDetailList(List<UserRequest> detailList) {
		this.detailList = detailList;
	}

	public List<UserRequest> getCountList() {
		return countList;
	}

	public void setCountList(List<UserRequest> countList) {
		this.countList = countList;
	}

	public void load(ComponentSystemEvent event) {
		FacesContext context = FacesContext.getCurrentInstance();
		appsList = new ArrayList<SelectItem>();
		appsList.add(new SelectItem("eForm","eForm"));
		appsList.add(new SelectItem("AAP","AAP"));
		appsList.add(new SelectItem("RAOS","RAOS"));
		appsList.add(new SelectItem("LOS-IPS","LOS-IPS"));
		appsList.add(new SelectItem("DTKBM","dtkbm"));
		if (!context.isPostback()) {
				if(leftmenuBacking.getAppsValue()==null) {
					leftmenuBacking.setAppsValue("SAP");
				}
				this.application = leftmenuBacking.getAppsValue();
				setHeadSum("Summary Onboard " + this.application);
				setHeadDetail("Detail Onboard " + this.application);
		}
	}

	public void sumSearch() {
		System.out.println("ini appsName " + this.appsName);
		if (this.beginDate == null || this.endDate == null) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Please fill in the required fields", "Error"));
		} else {
			try {
				this.countList = userEao.countUserRequest(beginDate, endDate,this.appsName);

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
		if (this.beginDate == null || this.endDate == null) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Please fill in the required fields", "Error"));
		}else {
			try {
				this.detailList = userEao.getUserRequest(this.beginDate, this.endDate,this.appsName);

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
	

}
