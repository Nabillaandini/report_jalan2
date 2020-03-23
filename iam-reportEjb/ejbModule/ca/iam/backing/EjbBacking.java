package ca.iam.backing;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Named;

import ca.iam.eao.EjbEao;
import ca.iam.entity.UserUpdates;

@Named
@Stateless
@LocalBean
public class EjbBacking {
	@EJB
	private EjbEao ejbEao;
	private String txt;
	public EjbBacking() {
	}
	public String getTxt() {
	return txt;
	}
	public void setTxt(String txt) {
	this.txt = txt;
	}
	public void load(ComponentSystemEvent event) throws Exception{
	txt = "Calendar : ";
	txt = ejbEao.testDb();
	}
	public void getUpdatesById(ComponentSystemEvent event) throws Exception{
		txt = "get User Updates by Id : ";
		txt = ejbEao.testDb();
		String user_id = "1187408513";
		ArrayList<UserUpdates> userList = new ArrayList();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date begin_date = formatter.parse("2020-03-18");
		Date end_date = formatter.parse("2020-03-19");
		userList = ejbEao.getUpdateById(user_id, begin_date, end_date);
		String userID = "";
		String updated = "";
		for(int i=0;i<userList.size();i++){
//			userID = userList.get(i).user_id;
//			updated = userList.get(i).updated_attr;
		}
	}
}