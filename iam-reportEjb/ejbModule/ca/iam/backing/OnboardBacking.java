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

	private Date beginDate;
	private Date endDate;

	public List<UserUpdates> detailList = new ArrayList<UserUpdates>();
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

		}
	}

	public void sumSearch() {
		if (this.beginDate == null || this.endDate == null) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Please fill in the required fields", "Error"));
		} else {
			try {
				this.countList = userEao.countData(this.beginDate, this.endDate,"onboard");

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
				this.detailList = userEao.getUserOnboard(this.beginDate, this.endDate);

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
