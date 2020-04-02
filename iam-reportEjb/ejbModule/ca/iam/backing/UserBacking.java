package ca.iam.backing;

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
import ca.iam.eao.UserEao;
import ca.iam.entity.CountList;
import ca.iam.entity.UserUpdates;
import ca.iam.rules.UserRules;

@ManagedBean
@SessionScoped
@Stateful
@Named
public class UserBacking extends BasicSessionBacking {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2421369158594542707L;

	@EJB
	public UserRules userRules;

	@EJB
	public UserEao userEao;

	private String userId;
	private Date beginDate;
	private Date endDate;
	private List<UserUpdates> userList = new ArrayList<UserUpdates>();
	private String headName;
	public List <CountList> countList = new ArrayList<CountList>();
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	
	public List<CountList> getCountList() {
		return countList;
	}

	public void setCountList(List<CountList> countList) {
		this.countList = countList;
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

	public UserRules getUserRules() {
		return userRules;
	}

	public void setUserRules(UserRules userRules) {
		this.userRules = userRules;
	}

	public List<UserUpdates> getUserList() {
		return userList;
	}

	public void setUserList(List<UserUpdates> userList) {
		this.userList = userList;
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
			setHeadName("Report Summary");

		}
	}

	
	public void getReportSummary() {
		
	}
	
	public void getDetailReport() throws ParseException{
		
		try {
			this.userList = userEao.getUpdateById(userId, beginDate, endDate);
			 FacesContext.getCurrentInstance().addMessage(null,
		                new FacesMessage("Searching is finished"));

		} catch (SQLException e) {
			 FacesContext.getCurrentInstance().addMessage(null,
		                new FacesMessage("Database Connection Error"));
			e.printStackTrace();
		}
		
//		this.userList.add(new UserUpdates("AMELIA ANDREMICA", "1188393433"));
//		this.userList.add(new UserUpdates("Abdalla Dewi", "09123809128"));
//		System.out.println("Executing searching user from db of " + beginDate);
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
