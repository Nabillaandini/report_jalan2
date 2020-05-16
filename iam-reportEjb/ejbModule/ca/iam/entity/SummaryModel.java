package ca.iam.entity;

import java.text.DateFormatSymbols;

import org.jfree.data.time.Month;

public class SummaryModel {
	private int countOnboard;
	private int countUpdate;
	private int sap;
	private int dtobm;
	private int dtkbm;
	private int countDeprov;
	private int resetDomain;
	private int resetApps;
	private int userRequest;
	private String date;
	private int mms;
	private int wbg;
	private int raos;
	private int ebudgeting;
	private int fico;
	private int mm;
	private int srm;
	private int skd;
	private int deprovSAP;
	private int deprovDTKBM;
	private int deprovDTOBM;
	private int wcp;
	private int aap;
	
	private int raospw;
	private int mmspw; 
	private int dtobmpw;
	private int dtkbmpw;
	private int sappw;
	private int webgpw;
	private int skdpw;
	private int aappw;
	private int ebudpw;
	private int ehcmspw;
	private int ficopw;
	private int srmpw;
	private int mmpw;
	private int wcppw;
	
	
	public SummaryModel(int countOnboard,int countUpdate,int sap, int dtobm, int dtkbm,int mms, int raos, int wbg,int countDeprov, int resetDomain, int resetApps, int ebudgeting,
			int fico, int mm, int srm, int skd, int deprovSAP, int deprovDTKBM, int deprovDTOBM, int wcp,int aap,int month, int year) {
		this.countOnboard=countOnboard;
		this.countUpdate=countUpdate;
		this.countDeprov = countDeprov;
		this.resetDomain = resetDomain;
		this.resetApps = resetApps;
		this.sap = sap;
		this.dtobm = dtobm;
		this.dtkbm = dtkbm;
		this.mms = mms;
		this.raos = raos;
		this.wbg = wbg;
		this.date = getMonth(month)+" "+year;	
		this.ebudgeting = ebudgeting;
		this.fico = fico;
		this.mm = mm;
		this.srm = srm;
		this.skd = skd;
		this.deprovSAP = deprovSAP;
		this.deprovDTOBM = deprovDTOBM;
		this.deprovDTKBM = deprovDTKBM;
		this.wcp  = wcp;
		this.aap = aap;
	}
	
	
	
	
	public int getAap() {
		return aap;
	}




	public void setAap(int aap) {
		this.aap = aap;
	}




	public int getWcp() {
		return wcp;
	}



	public void setWcp(int wcp) {
		this.wcp = wcp;
	}



	public int getAappw() {
		return aappw;
	}



	public void setAappw(int aappw) {
		this.aappw = aappw;
	}



	public int getEbudpw() {
		return ebudpw;
	}



	public void setEbudpw(int ebudpw) {
		this.ebudpw = ebudpw;
	}



	public int getEhcmspw() {
		return ehcmspw;
	}



	public void setEhcmspw(int ehcmspw) {
		this.ehcmspw = ehcmspw;
	}



	public int getFicopw() {
		return ficopw;
	}



	public void setFicopw(int ficopw) {
		this.ficopw = ficopw;
	}



	public int getSrmpw() {
		return srmpw;
	}



	public void setSrmpw(int srmpw) {
		this.srmpw = srmpw;
	}



	public int getMmpw() {
		return mmpw;
	}



	public void setMmpw(int mmpw) {
		this.mmpw = mmpw;
	}



	public int getWcppw() {
		return wcppw;
	}



	public void setWcppw(int wcppw) {
		this.wcppw = wcppw;
	}



	public int getRaospw() {
		return raospw;
	}



	public void setRaospw(int raospw) {
		this.raospw = raospw;
	}



	public int getMmspw() {
		return mmspw;
	}



	public void setMmspw(int mmspw) {
		this.mmspw = mmspw;
	}



	public int getDtobmpw() {
		return dtobmpw;
	}



	public void setDtobmpw(int dtobmpw) {
		this.dtobmpw = dtobmpw;
	}



	public int getDtkbmpw() {
		return dtkbmpw;
	}



	public void setDtkbmpw(int dtkbmpw) {
		this.dtkbmpw = dtkbmpw;
	}



	public int getSappw() {
		return sappw;
	}



	public void setSappw(int sappw) {
		this.sappw = sappw;
	}



	public int getWebgpw() {
		return webgpw;
	}



	public void setWebgpw(int webgpw) {
		this.webgpw = webgpw;
	}



	public int getSkdpw() {
		return skdpw;
	}



	public void setSkdpw(int skdpw) {
		this.skdpw = skdpw;
	}



	public int getEbudgeting() {
		return ebudgeting;
	}



	public void setEbudgeting(int ebudgeting) {
		this.ebudgeting = ebudgeting;
	}



	public int getFico() {
		return fico;
	}



	public void setFico(int fico) {
		this.fico = fico;
	}



	public int getMm() {
		return mm;
	}



	public void setMm(int mm) {
		this.mm = mm;
	}



	public int getSrm() {
		return srm;
	}



	public void setSrm(int srm) {
		this.srm = srm;
	}



	public int getSkd() {
		return skd;
	}



	public void setSkd(int skd) {
		this.skd = skd;
	}



	public int getDeprovSAP() {
		return deprovSAP;
	}



	public void setDeprovSAP(int deprovSAP) {
		this.deprovSAP = deprovSAP;
	}



	public int getDeprovDTKBM() {
		return deprovDTKBM;
	}



	public void setDeprovDTKBM(int deprovDTKBM) {
		this.deprovDTKBM = deprovDTKBM;
	}



	public int getDeprovDTOBM() {
		return deprovDTOBM;
	}



	public void setDeprovDTOBM(int deprovDTOBM) {
		this.deprovDTOBM = deprovDTOBM;
	}



	public int getMms() {
		return mms;
	}


	public void setMms(int mms) {
		this.mms = mms;
	}


	public int getWbg() {
		return wbg;
	}


	public void setWbg(int wbg) {
		this.wbg = wbg;
	}


	public int getRaos() {
		return raos;
	}


	public void setRaos(int raos) {
		this.raos = raos;
	}


	public String getMonth(int month) {
	    return new DateFormatSymbols().getMonths()[month-1];
	}
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public SummaryModel() {
		
	}

	public int getCountOnboard() {
		return countOnboard;
	}

	public void setCountOnboard(int countOnboard) {
		this.countOnboard = countOnboard;
	}

	public int getCountUpdate() {
		return countUpdate;
	}

	public void setCountUpdate(int countUpdate) {
		this.countUpdate = countUpdate;
	}

	public int getSap() {
		return sap;
	}
	public void setSap(int sap) {
		this.sap = sap;
	}
	public int getDtobm() {
		return dtobm;
	}
	public void setDtobm(int dtobm) {
		this.dtobm = dtobm;
	}
	public int getDtkbm() {
		return dtkbm;
	}
	public void setDtkbm(int dtkbm) {
		this.dtkbm = dtkbm;
	}
	public int getCountDeprov() {
		return countDeprov;
	}
	public void setCountDeprov(int countDeprov) {
		this.countDeprov = countDeprov;
	}
	public int getResetDomain() {
		return resetDomain;
	}
	public void setResetDomain(int resetDomain) {
		this.resetDomain = resetDomain;
	}
	public int getResetApps() {
		return resetApps;
	}
	public void setResetApps(int resetApps) {
		this.resetApps = resetApps;
	}
	public int getUserRequest() {
		return userRequest;
	}
	public void setUserRequest(int userRequest) {
		this.userRequest = userRequest;
	}


}
