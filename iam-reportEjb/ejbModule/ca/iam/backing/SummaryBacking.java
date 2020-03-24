package ca.iam.backing;

import java.sql.SQLException;
import java.text.ParseException;
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
import ca.iam.eao.UserEao;
import ca.iam.entity.CountList;
import ca.iam.entity.SummaryModel;
import ca.iam.entity.UserUpdates;
import ca.iam.rules.UserRules;

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

	private Date beginDate;
	private Date endDate;
	private String headName;
	SummaryModel sumModel = new SummaryModel();


	public String getHeadName() {
		return headName;
	}

	public void setHeadName(String headName) {
		this.headName = headName;
	}

	public void load(ComponentSystemEvent event) {
		FacesContext context = FacesContext.getCurrentInstance();
		if (!context.isPostback()) {
			setHeadName("Report Summary");

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
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Please fill in the required fields", "Error"));
		} else {
			try {
				this.sumModel = userEao.getSummaryReport(this.beginDate, this.endDate);

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
