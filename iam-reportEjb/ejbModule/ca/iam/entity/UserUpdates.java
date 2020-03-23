package ca.iam.entity;

import java.util.Date;

public class UserUpdates {

	private String userId;
	private String fullName;
	private String loginId;
	private String firstName;
	private String middleName;
	private String lastName;
	private Date activationDate;
	private String string05;
	private Date expirationDate;
	private String employeeStatus;
	private String manager;
	private String managerEmployeeNumber;
	private String integer04;
	private String alternateEmail;
	private String mobilePhone;
	private String string00;
	private String strng01;
	private String department;
	private String string02;
	private String caseExactString04;
	private String costCenter;
	private String caseExactString03;
	private String string03;
	private String caseExactString02;
	private String string04;
	private String caseExactString01;
	private String string08;
	private String caseExactString00;
	private String string09;
	private String string06;
	private String string07;
	private String employeeType;
	private Boolean enable;
	private Date lastUpdate;
	private int isOnboard;
	private int isUpdate;
	private String updatedAttr;
	private int updSuccess;

	public UserUpdates() {
		fullName = "";
		userId = "";
		loginId = "";
		firstName = "";
		middleName = "";
		lastName = "";
		activationDate = null;
		string05 = "";
		expirationDate = null;
		employeeStatus = "";
		manager = "";
		managerEmployeeNumber = "";
		integer04 = "";
		alternateEmail = "";
		mobilePhone = "";
		string00 = "";
		strng01 = "";
		department = "";
		string02 = "";
		caseExactString04 = "";
		costCenter = "";
		caseExactString03 = "";
		string03 = "";
		caseExactString02 = "";
		string04 = "";
		caseExactString01 = "";
		string08 = "";
		caseExactString00 = "";
		string09 = "";
		string06 = "";
		string07 = "";
		employeeType = "";
		enable = true;
		lastUpdate = null;
	}

	public UserUpdates(String fullName, String userId){
		this.fullName = fullName;
		this.userId = userId;
	}
	public UserUpdates(String fullName, String userId, String loginId, String firstName, String middleName,
			String lastName, Date activationDate, String string05, Date expirationDate, String employeeStatus,
			String manager, String managerEmployeeNumber, String integer04, String alternateEmail, String mobilePhone,
			String string00, String strng01, String department, String string02, String caseExactString04,
			String costCenter, String caseExactString03, String string03, String caseExactString02, String string04,
			String caseExactString01, String string08, String caseExactString00, String string09, String string06,
			String string07, String employeeType, Boolean enable, Date lastUpdate, int isOnboard, int isUpdate,
			String updatedAttr, int updSuccess) {
		this.fullName = fullName;
		this.userId = userId;
		this.loginId = loginId;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.activationDate = activationDate;
		this.string05 = string05;
		this.expirationDate = expirationDate;
		this.employeeStatus = employeeStatus;
		this.manager = manager;
		this.managerEmployeeNumber = managerEmployeeNumber;
		this.integer04 = integer04;
		this.alternateEmail = alternateEmail;
		this.mobilePhone = mobilePhone;
		this.string00 = string00;
		this.strng01 = strng01;
		this.department = department;
		this.string02 = string02;
		this.caseExactString04 = caseExactString04;
		this.costCenter = costCenter;
		this.caseExactString03 = caseExactString04;
		this.string03 = string03;
		this.caseExactString02 = caseExactString02;
		this.string04 = string04;
		this.caseExactString01 = caseExactString01;
		this.string08 = string08;
		this.caseExactString00 = caseExactString00;
		this.string09 = string09;
		this.string06 = string06;
		this.string07 = string07;
		this.employeeType = employeeType;
		this.enable = enable;
		this.lastUpdate = lastUpdate;
		this.isOnboard = isOnboard;
		this.isUpdate = isUpdate;
		this.updatedAttr = updatedAttr;
		this.updSuccess = updSuccess;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getActivationDate() {
		return activationDate;
	}

	public void setActivationDate(Date activationDate) {
		this.activationDate = activationDate;
	}

	public String getString05() {
		return string05;
	}

	public void setString05(String string05) {
		this.string05 = string05;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public String getEmployeeStatus() {
		return employeeStatus;
	}

	public void setEmployeeStatus(String employeeStatus) {
		this.employeeStatus = employeeStatus;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public String getManagerEmployeeNumber() {
		return managerEmployeeNumber;
	}

	public void setManagerEmployeeNumber(String managerEmployeeNumber) {
		this.managerEmployeeNumber = managerEmployeeNumber;
	}

	public String getInteger04() {
		return integer04;
	}

	public void setInteger04(String integer04) {
		this.integer04 = integer04;
	}

	public String getAlternateEmail() {
		return alternateEmail;
	}

	public void setAlternateEmail(String alternateEmail) {
		this.alternateEmail = alternateEmail;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getString00() {
		return string00;
	}

	public void setString00(String string00) {
		this.string00 = string00;
	}

	public String getStrng01() {
		return strng01;
	}

	public void setStrng01(String strng01) {
		this.strng01 = strng01;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getString02() {
		return string02;
	}

	public void setString02(String string02) {
		this.string02 = string02;
	}

	public String getCaseExactString04() {
		return caseExactString04;
	}

	public void setCaseExactString04(String caseExactString04) {
		this.caseExactString04 = caseExactString04;
	}

	public String getCostCenter() {
		return costCenter;
	}

	public void setCostCenter(String costCenter) {
		this.costCenter = costCenter;
	}

	public String getCaseExactString03() {
		return caseExactString03;
	}

	public void setCaseExactString03(String caseExactString03) {
		this.caseExactString03 = caseExactString03;
	}

	public String getString03() {
		return string03;
	}

	public void setString03(String string03) {
		this.string03 = string03;
	}

	public String getCaseExactString02() {
		return caseExactString02;
	}

	public void setCaseExactString02(String caseExactString02) {
		this.caseExactString02 = caseExactString02;
	}

	public String getString04() {
		return string04;
	}

	public void setString04(String string04) {
		this.string04 = string04;
	}

	public String getCaseExactString01() {
		return caseExactString01;
	}

	public void setCaseExactString01(String caseExactString01) {
		this.caseExactString01 = caseExactString01;
	}

	public String getString08() {
		return string08;
	}

	public void setString08(String string08) {
		this.string08 = string08;
	}

	public String getCaseExactString00() {
		return caseExactString00;
	}

	public void setCaseExactString00(String caseExactString00) {
		this.caseExactString00 = caseExactString00;
	}

	public String getString09() {
		return string09;
	}

	public void setString09(String string09) {
		this.string09 = string09;
	}

	public String getString06() {
		return string06;
	}

	public void setString06(String string06) {
		this.string06 = string06;
	}

	public String getString07() {
		return string07;
	}

	public void setString07(String string07) {
		this.string07 = string07;
	}

	public String getEmployeeType() {
		return employeeType;
	}

	public void setEmployeeType(String employeeType) {
		this.employeeType = employeeType;
	}

	public Boolean getEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public int getIsOnboard() {
		return isOnboard;
	}

	public void setIsOnboard(int isOnboard) {
		this.isOnboard = isOnboard;
	}

	public int getIsUpdate() {
		return isUpdate;
	}

	public void setIsUpdate(int isUpdate) {
		this.isUpdate = isUpdate;
	}

	public String getUpdatedAttr() {
		return updatedAttr;
	}

	public void setUpdatedAttr(String updatedAttr) {
		this.updatedAttr = updatedAttr;
	}

	public int getUpdSuccess() {
		return updSuccess;
	}

	public void setUpdSuccess(int updSuccess) {
		this.updSuccess = updSuccess;
	}
}
