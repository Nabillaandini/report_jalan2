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
	
	public List<UserUpdates> detailList = new ArrayList<UserUpdates>();
	public List<CountList> countList = new ArrayList<CountList>();
	public String application;

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
		String path="D:/onboard_" +date+ "_"+ end + ".pdf";
		try {
			File file = new File(path);
			FileOutputStream fileout = new FileOutputStream(file);
			Document document = new Document();
			PdfWriter.getInstance(document, fileout);
			document.addAuthor("Me");

			document.open();

			Image image;
			try {
				image = Image.getInstance("D:/report_iam/iam-reportWeb/WebContent/img/mandiri-logo.png");
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
			document.addTitle("IAM Onboard " + this.application + " Report: " + date);
			Paragraph paragraph1 = new Paragraph();
			paragraph1.add("IAM Onboard " + this.application + " Report: " + date + " - " + end + "\n");
			paragraph1.add("\n");
			paragraph1.setAlignment(Element.ALIGN_CENTER);
			paragraph1.setFont(font);
			document.add(paragraph1);
			Paragraph paragraph = new Paragraph();
			paragraph.setAlignment(Element.ALIGN_LEFT);
			float[] columnWidths = { 2.5f, 2.5f, 3.5f };
			// create PDF table with the given widths
			PdfPTable table = new PdfPTable(columnWidths);
			// set table width a percentage of the page width
			table.setWidthPercentage(90f);
			insertCell(table, "Tanggal Onboard", Element.ALIGN_CENTER, 1);
			insertCell(table, "UID", Element.ALIGN_CENTER, 1);
			insertCell(table, "Full Name", Element.ALIGN_CENTER, 1);
			table.setHeaderRows(1);
			for (int i = 0; i < detailList.size(); i++) {
				insertCell(table, DATE_FORMAT.format(detailList.get(i).getLastUpdate()), Element.ALIGN_CENTER, 1);
				insertCell(table, detailList.get(i).getUserId(), Element.ALIGN_CENTER, 1);
				insertCell(table, detailList.get(i).getFullName(), Element.ALIGN_CENTER, 1);
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

		System.out.println("Content added");

		System.out.println("Pdf created");
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

	public List<UserUpdates> getDetailList() {
		return detailList;
	}

	public void setDetailList(List<UserUpdates> detailList) {
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
		System.out.println("value of apps: " + leftmenuBacking.getAppsValue());
		this.application = leftmenuBacking.getAppsValue();
		if (this.beginDate == null || this.endDate == null) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please fill in the required fields", "Error"));
		} else {
			try {
				if (this.application.equalsIgnoreCase("sap")) {
					this.countList = userEao.countData(this.beginDate, this.endDate, "onboard");
				} else if (this.application.equalsIgnoreCase("dtobm")) {
					this.countList = dtobmEao.countData(this.beginDate, this.endDate, "onboard");
				} else if (this.application.equalsIgnoreCase("dtkbm")) {
					this.countList = dtkbmEao.countData(this.beginDate, this.endDate, "onboard");
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
		this.application = leftmenuBacking.getAppsValue();
		if (this.beginDate == null || this.endDate == null) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please fill in the required fields", "Error"));
		} else {
			try {
				if (this.application.equalsIgnoreCase("sap")) {
					this.detailList = userEao.getUserOnboard(this.beginDate, this.endDate);
				} else if (this.application.equalsIgnoreCase("dtobm")) {
					this.detailList = dtobmEao.getUserOnboard(this.beginDate, this.endDate);
				} else if (this.application.equalsIgnoreCase("dtobm")) {
					this.detailList = dtkbmEao.getUserOnboard(this.beginDate, this.endDate);
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

}
