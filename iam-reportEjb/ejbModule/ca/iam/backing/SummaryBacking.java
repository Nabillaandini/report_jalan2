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

import ca.iam.core.BasicSessionBacking;
import ca.iam.eao.DtkbmEao;
import ca.iam.eao.DtobmEao;
import ca.iam.eao.UserEao;
import ca.iam.entity.CountList;
import ca.iam.entity.SummaryModel;
import ca.iam.entity.SummarySQL;
import ca.iam.entity.UserUpdates;
import ca.iam.rules.UserRules;

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
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;

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
	ArrayList <SummarySQL> detailListSQL = new ArrayList<SummarySQL>();
	ArrayList <SummaryModel> detailSumModel = new ArrayList <SummaryModel>();
	
	String period1;
	Month periodA=null;
	String application;
	
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

	public ArrayList<SummarySQL> getDetailListSQL() {
		return detailListSQL;
	}

	public void setDetailListSQL(ArrayList<SummarySQL> detailListSQL) {
		this.detailListSQL = detailListSQL;
	}
	public void toFile() {
		String res = "";
		SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
		String date = DATE_FORMAT.format(this.beginDate);
		String[] arr = date.split("-");
		String month_a = arr[1];
		String year_a = arr[2];
		String end = DATE_FORMAT.format(this.endDate);
		arr= end.split("-");
		String month_b = arr[1];
		String year_b = arr[2];
		String path="C:/report_result/summary_" +month_a+year_a+ "_"+ month_b+year_b + ".pdf";
	
		System.out.println(res);
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
			document.addTitle("IAM Summary Report" + date + " - " + end);
			Paragraph paragraph1 = new Paragraph();
			paragraph1.add("IAM Summary Report ");
			paragraph1.add("\n");
			paragraph1.add("Period : "+ month_a+"/"+year_a + " - " +month_b+"/"+year_b +"\n");
			Chunk linebreak = new Chunk(new DottedLineSeparator());
			paragraph1.add(linebreak);
			paragraph1.add("\n\n");
			paragraph1.setAlignment(Element.ALIGN_CENTER);
			paragraph1.setFont(font);
			document.add(paragraph1);

			Paragraph paragraph = new Paragraph();
			for(int i=0;i<detailSumModel.size();i++) {
				res = "Period : " + detailSumModel.get(i).getDate() + "\nJumlah Karyawan Onboard : " + detailSumModel.get(i).getCountOnboard() 
						
						+ "\nJumlah Karyawan Success Request Aplikasi SAP eHCMS: " + this.detailSumModel.get(i).getSap()
						+ "\nJumlah Karyawan Success Request Aplikasi SAP eBudgeting: " + this.detailSumModel.get(i).getEbudgeting()
						+ "\nJumlah Karyawan Success Request Aplikasi SAP FICO: " + this.detailSumModel.get(i).getFico()
						+ "\nJumlah Karyawan Success Request Aplikasi SAP MM: " + this.detailSumModel.get(i).getMm()
						+ "\nJumlah Karyawan Success Request Aplikasi SAP SRM: " + this.detailSumModel.get(i).getSrm()
						+ "\nJumlah Karyawan Success Request Aplikasi SKD KPEI: " + this.detailSumModel.get(i).getSkd()
						+"\nJumlah Karyawan Success Request Aplikasi DTOBM : "+ this.detailSumModel.get(i).getDtobm()
						+"\nJumlah Karyawan Success Request Aplikasi DTKBM : "+ this.detailSumModel.get(i).getDtkbm()
						+"\nJumlah Karyawan Success Request Aplikasi MMS : "+ this.detailSumModel.get(i).getMms()
						+"\nJumlah Karyawan Success Request Aplikasi RAOS : "+ this.detailSumModel.get(i).getRaos()
						+"\nJumlah Karyawan Success Request Aplikasi Web Bill Gateway : "+ this.detailSumModel.get(i).getWbg()
						+"\nJumlah Karyawan Success Request Aplikasi Web Corporate Payable : "+ this.detailSumModel.get(i).getWcp()
						+"\nJumlah Karyawan Offboard/Terminate SAP : " + detailSumModel.get(i).getDeprovSAP()
						+"\nJumlah Karyawan Offboard/Terminate DTOBM : " + detailSumModel.get(i).getDeprovDTOBM()
						+"\nJumlah Karyawan Offboard/Terminate DTKBM : " + detailSumModel.get(i).getDeprovDTKBM()
						+"\nJumlah Karyawan Reset Password AD : "+ this.detailSumModel.get(i).getResetDomain()
						+ "\nJumlah Karyawan Reset Password SAP eHCMS : " + this.detailSumModel.get(i).getEhcmspw()
						+ "\nJumlah Karyawan Reset Password  SAP eBudgeting : " + this.detailSumModel.get(i).getEbudpw()
						+ "\nJumlah Karyawan Reset Password  SAP FICO : " + this.detailSumModel.get(i).getFicopw()
						+ "\nJumlah Karyawan Reset Password  SAP MM : " + this.detailSumModel.get(i).getMmpw()
						+ "\nJumlah Karyawan Reset Password  SAP SRM: " + this.detailSumModel.get(i).getSrmpw()
						+"\nJumlah Karyawan Reset Password DTOBM : " +this.detailSumModel.get(i).getDtobmpw()
						+"\nJumlah Karyawan Reset Password DTKBM : " +this.detailSumModel.get(i).getDtkbmpw()
						+"\nJumlah Karyawan Reset Password MMS : " +this.detailSumModel.get(i).getMmspw()
						+"\nJumlah Karyawan Reset Password RAOS : " +this.detailSumModel.get(i).getRaospw()
						+"\nJumlah Karyawan Reset Password Web Bill Gateway : " +this.detailSumModel.get(i).getWebgpw()
						+"\nJumlah Karyawan Reset Password Web Corporate Payable : " +this.detailSumModel.get(i).getWcppw()
						+"\nJumlah Karyawan Reset Password SKD KPEI : " +this.detailSumModel.get(i).getSkdpw()
						+ "\n\n\n";
				paragraph.add(res);
			}
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
			setHeadName("Report Summary ");

			this.beginDate = null;
			this.endDate = null;
			this.detailListSQL = null;
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
	
	public void getSummarySQL() {
		if (this.beginDate == null || this.endDate == null) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Please fill in the required fields", "Error"));
		}else {
			try {
				this.detailListSQL = userEao.getSummaryProv(this.beginDate, this.endDate);

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
	public void provToFile() {
		String res = "";
		SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
		String date = DATE_FORMAT.format(this.beginDate);
		String end = DATE_FORMAT.format(this.endDate);
		String path="C:/report_result/summaryprov_" +date+ "_"+ end + ".pdf";
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
			document.addTitle("IAM Prov & Deprov Report: " + date + " - " + end);
			Paragraph paragraph1 = new Paragraph();
			paragraph1.add("IAM Prov & Deprov Report: "+ this.application);
			paragraph1.add("\n");
			paragraph1.add("Tanggal : "+ date + " - " + end);
			paragraph1.add("\n");
			paragraph1.setAlignment(Element.ALIGN_CENTER);
			paragraph1.setFont(font);
			document.add(paragraph1);

			Paragraph paragraph = new Paragraph();
			for(int i=0;i<detailListSQL.size();i++) {
				res = "Date : " + detailListSQL.get(i).getDate() + "\nTotal Provision : " + detailListSQL.get(i).getProv() + "\nTotal Deprovision : " + detailListSQL.get(i).getDeprov() + "\n\n";
				paragraph.add(res);
			}
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
//	public void sumtoFile() {
//		String res = "";
//		SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
//		String date = DATE_FORMAT.format(this.beginDate);
//		String[] arr = date.split("-");
//		String month_a = arr[1];
//		String year_a = arr[2];
//		String end = DATE_FORMAT.format(this.endDate);
//		arr= end.split("-");
//		String month_b = arr[1];
//		String year_b = arr[2];
//		String path="C:/report_result/summarytbl_" +month_a+year_a+ "_"+ month_b+year_b + ".pdf";
//	
//		
//		try {
//			File file = new File(path);
//			FileOutputStream fileout = new FileOutputStream(file);
//			Document document = new Document();
//			PdfWriter.getInstance(document, fileout);
//			document.addAuthor("Me");
//
//			document.open();
//
//			Image image;
//			try {
//				image = Image.getInstance("C:/report_result/img/mandiri-logo.png");
//				image.setAlignment(Image.MIDDLE);
//				image.scaleToFit(200, 100);
//
//				document.add(image);
//			} catch (MalformedURLException e) {
//				e.printStackTrace();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//
//			Font font = new Font();
//			font.setStyle(Font.BOLD);
//			font.setStyle(Font.ITALIC);
//			font.setSize(24);
//			document.addTitle("IAM Summary Report : " + date);
//			Paragraph paragraph1 = new Paragraph();
//			paragraph1.add("IAM Summary Report : ");
//			paragraph1.add("\n");
//			paragraph1.add("periode :" + date + " - " + end + "\n");
//			paragraph1.add("\n");
//			paragraph1.setAlignment(Element.ALIGN_CENTER);
//			paragraph1.setFont(font);
//			document.add(paragraph1);
//			Paragraph paragraph = new Paragraph();
//			paragraph.setAlignment(Element.ALIGN_LEFT);
//			for(int i=0;i<detailSumModel.size();i++) {
//				float[] columnWidths = { 2.5f, 2.5f};
//				// create PDF table with the given widths
//				PdfPTable table = new PdfPTable(columnWidths);
//				// set table width a percentage of the page width
//				table.setWidthPercentage(90f);
//				insertCell(table, "Jumlah Karyawan Onboard", Element.ALIGN_LEFT, 1);
//				
//			}
//			for (int i = 0; i < countList.size(); i++) {
//				System.out.println("masuk countlist");
//				insertCell(table, countList.get(i).getDate(), Element.ALIGN_CENTER, 1);
//				insertCell(table, Integer.toString(countList.get(i).getCount()), Element.ALIGN_CENTER, 1);
//				insertCell(table, countList.get(i).getAppsName(), Element.ALIGN_CENTER, 1);
//			}
//			System.out.println("after loop");
//			paragraph.add(table);
//
//			document.add(paragraph);
//
//			document.close();
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (DocumentException e) {
//			e.printStackTrace();
//		}
//		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("File has been downloaded " + path));
//
//	}
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
