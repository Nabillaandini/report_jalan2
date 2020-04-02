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
import javax.inject.Named;

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
import ca.iam.eao.UserEao;
import ca.iam.entity.CountList;
import ca.iam.entity.Provision;
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

	private Date beginDate;
	private Date endDate;

	public List<Provision> detailList = new ArrayList<Provision>();
	public List<CountList> countList = new ArrayList<CountList>();

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
		if (!context.isPostback()) {

		}
	}

	public void sumSearch() {
		if (this.beginDate == null || this.endDate == null) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please fill in the required fields", "Error"));
		} else {
			try {
				this.countList = userEao.countDeprovision(this.beginDate, this.endDate);

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
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please fill in the required fields", "Error"));
		} else {
			try {
				this.detailList = userEao.getDeprovData(this.beginDate, this.endDate);

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
		String path="D:/deprov_" +date+ "_"+ end + ".pdf";
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

			Font font = new Font();
			font.setStyle(Font.BOLD);
			font.setStyle(Font.ITALIC);
			font.setSize(24);
			document.addTitle("IAM User Deprovisioning Report : " + date);
			Paragraph paragraph1 = new Paragraph();
			paragraph1.add("IAM User Deprovisioning Report " + date + " - " + end);
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

}
