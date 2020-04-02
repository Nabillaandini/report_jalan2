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

import ca.iam.core.BasicSessionBacking;
import ca.iam.eao.DtkbmEao;
import ca.iam.eao.DtobmEao;
import ca.iam.eao.UserEao;
import ca.iam.entity.CountList;
import ca.iam.entity.SummaryModel;
import ca.iam.entity.UserUpdates;
import ca.iam.rules.UserRules;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.log.SysoCounter;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfWriter;

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

	private Date beginDate;
	private Date endDate;
	private String headName;
	SummaryModel sumModel = new SummaryModel();

	String application;

	public void toFile() {
		String res = "\nTotal Update : " + this.sumModel.getCountUpdate() + "\nTotal Onboard : "
				+ this.sumModel.getCountOnboard() + "\nTotal Disabled : " + this.sumModel.getCountDisabled();
		SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
		String date = DATE_FORMAT.format(this.beginDate);
		String end = DATE_FORMAT.format(this.endDate);
		String path="D:/summary_" +date+ "_"+ end + ".pdf";
		System.out.println(res);
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
			document.addTitle("IAM Summary " + this.application + " Report:" + date + " - " + end);
			Paragraph paragraph1 = new Paragraph();
			paragraph1.add("IAM Summary " + this.application + " Report: " + date + " - " + end);
			paragraph1.setAlignment(Element.ALIGN_CENTER);
			paragraph1.setFont(font);
			document.add(paragraph1);

			Paragraph paragraph = new Paragraph();
			paragraph.add(res);
			paragraph.setAlignment(Element.ALIGN_LEFT);
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
			setHeadName("Report Summary " + this.application);

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
		if (this.beginDate == null || this.endDate == null) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please fill in the required fields", "Error"));
		} else {
			try {
				if (this.application.equalsIgnoreCase("sap")) {
					this.sumModel = userEao.getSummaryReport(this.beginDate, this.endDate);
//				int [] countProv = userEao.getSummaryProv(this.beginDate, this.endDate);
//				this.sumModel.setProv(countProv[0]);
//				this.sumModel.setDeprov(countProv[1]);
				} else if (this.application.equalsIgnoreCase("dtobm")) {
					this.sumModel = dtobmEao.getSummaryReport(this.beginDate, this.endDate);
				} else if (this.application.equalsIgnoreCase("dtkbm")) {
					this.sumModel = dtkbmEao.getSummaryReport(this.beginDate, this.endDate);
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
