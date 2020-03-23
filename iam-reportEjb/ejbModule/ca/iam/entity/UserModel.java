package ca.iam.entity;

import java.util.Date;

public class UserModel {
String full_name;
String user_id;
String login_id;
String first_name;
String middle_name;
String last_name;
Date activation_date;
String string_05;
Date expiration_date;
String employee_status;
String manager;
String manager_employee_number;
String integer_04;
String alternate_email;
String mobile_phone;
String string_00;
String strng_01;
String department;
String string_02;
String case_exact_string04;
String cost_center;
String case_exact_string03;
String string_03;
String case_exact_string02;
String string_04;
String case_exact_string01;
String string_08;
String case_exact_string00;
String string_09;
String string_06;
String string_07;
String employee_type;
Boolean enable;
Date last_update;

public UserModel(){
	 full_name= "";
	 user_id= "";
	 login_id= "";
	 first_name= "";
	 middle_name= "";
	 last_name= "";
	activation_date= null;
	 string_05= "";
	 expiration_date= null;
	 employee_status= "";
	 manager= "";
	 manager_employee_number= "";
	 integer_04= "";
	 alternate_email= "";
	 mobile_phone= "";
	 string_00= "";
	 strng_01= "";
	 department= "";
	 string_02= "";
	 case_exact_string04= "";
	 cost_center= "";
	 case_exact_string03= "";
	 string_03= "";
	 case_exact_string02= "";
	 string_04= "";
	 case_exact_string01= "";
	 string_08= "";
	 case_exact_string00= "";
	 string_09= "";
	 string_06= "";
	 string_07= "";
	 employee_type= "";
	enable= true;
	last_update= null;
}
public UserModel(String full_name, String user_id, String login_id, String first_name, String middle_name, String last_name, Date activation_date, String string_05, Date expiration_date, String employee_status, String manager, String manager_employee_number, String integer_04, String alternate_email, String mobile_phone, String string_00, String strng_01, String department, String string_02, String case_exact_string04, String cost_center, String case_exact_string03, String string_03, String case_exact_string02, String string_04, String case_exact_string01, String string_08, String case_exact_string00, String string_09, String string_06, String string_07, String employee_type, Boolean enable, Date last_update){
	this.full_name=full_name;
	this.user_id=user_id;
	this.login_id=login_id;
	this.first_name=first_name;
	this.middle_name=middle_name;
	this.last_name=last_name;
	this.activation_date=activation_date;
	this.string_05=string_05;
	this.expiration_date=expiration_date;
	this.employee_status=employee_status;
	this.manager=manager;
	this.manager_employee_number=manager_employee_number;
	this.integer_04=integer_04;
	this.alternate_email=alternate_email;
	this.mobile_phone=mobile_phone;
	this.string_00=string_00;
	this.strng_01=strng_01;
	this.department=department;
	this.string_02=string_02;
	this.case_exact_string04=case_exact_string04;
	this.cost_center=cost_center;
	this.case_exact_string03=case_exact_string04;
	this.string_03=string_03;
	this.case_exact_string02=case_exact_string02;
	this.string_04=string_04;
	this.case_exact_string01=case_exact_string01;
	this.string_08=string_08;
	this.case_exact_string00=case_exact_string00;
	this.string_09=string_09;
	this.string_06=string_06;
	this.string_07=string_07;
	this.employee_type=employee_type;
	this.enable=enable;
	this.last_update=last_update;
}
public String getFull_name() {
	return full_name;
}
public void setFull_name(String full_name) {
	this.full_name = full_name;
}
public String getUser_id() {
	return user_id;
}
public void setUser_id(String user_id) {
	this.user_id = user_id;
}
public String getLogin_id() {
	return login_id;
}
public void setLogin_id(String login_id) {
	this.login_id = login_id;
}
public String getFirst_name() {
	return first_name;
}
public void setFirst_name(String first_name) {
	this.first_name = first_name;
}
public String getMiddle_name() {
	return middle_name;
}
public void setMiddle_name(String middle_name) {
	this.middle_name = middle_name;
}
public String getLast_name() {
	return last_name;
}
public void setLast_name(String last_name) {
	this.last_name = last_name;
}
public Date getActivation_date() {
	return activation_date;
}
public void setActivation_date(Date activation_date) {
	this.activation_date = activation_date;
}
public String getString_05() {
	return string_05;
}
public void setString_05(String string_05) {
	this.string_05 = string_05;
}
public Date getExpiration_date() {
	return expiration_date;
}
public void setExpiration_date(Date expiration_date) {
	this.expiration_date = expiration_date;
}
public String getEmployee_status() {
	return employee_status;
}
public void setEmployee_status(String employee_status) {
	this.employee_status = employee_status;
}
public String getManager() {
	return manager;
}
public void setManager(String manager) {
	this.manager = manager;
}
public String getManager_employee_number() {
	return manager_employee_number;
}
public void setManager_employee_number(String manager_employee_number) {
	this.manager_employee_number = manager_employee_number;
}
public String getInteger_04() {
	return integer_04;
}
public void setInteger_04(String integer_04) {
	this.integer_04 = integer_04;
}
public String getAlternate_email() {
	return alternate_email;
}
public void setAlternate_email(String alternate_email) {
	this.alternate_email = alternate_email;
}
public String getMobile_phone() {
	return mobile_phone;
}
public void setMobile_phone(String mobile_phone) {
	this.mobile_phone = mobile_phone;
}
public String getString_00() {
	return string_00;
}
public void setString_00(String string_00) {
	this.string_00 = string_00;
}
public String getStrng_01() {
	return strng_01;
}
public void setStrng_01(String strng_01) {
	this.strng_01 = strng_01;
}
public String getDepartment() {
	return department;
}
public void setDepartment(String department) {
	this.department = department;
}
public String getString_02() {
	return string_02;
}
public void setString_02(String string_02) {
	this.string_02 = string_02;
}
public String getCase_exact_string04() {
	return case_exact_string04;
}
public void setCase_exact_string04(String case_exact_string04) {
	this.case_exact_string04 = case_exact_string04;
}
public String getCost_center() {
	return cost_center;
}
public void setCost_center(String cost_center) {
	this.cost_center = cost_center;
}
public String getCase_exact_string03() {
	return case_exact_string03;
}
public void setCase_exact_string03(String case_exact_string03) {
	this.case_exact_string03 = case_exact_string03;
}
public String getString_03() {
	return string_03;
}
public void setString_03(String string_03) {
	this.string_03 = string_03;
}
public String getCase_exact_string02() {
	return case_exact_string02;
}
public void setCase_exact_string02(String case_exact_string02) {
	this.case_exact_string02 = case_exact_string02;
}
public String getString_04() {
	return string_04;
}
public void setString_04(String string_04) {
	this.string_04 = string_04;
}
public String getCase_exact_string01() {
	return case_exact_string01;
}
public void setCase_exact_string01(String case_exact_string01) {
	this.case_exact_string01 = case_exact_string01;
}
public String getString_08() {
	return string_08;
}
public void setString_08(String string_08) {
	this.string_08 = string_08;
}
public String getCase_exact_string00() {
	return case_exact_string00;
}
public void setCase_exact_string00(String case_exact_string00) {
	this.case_exact_string00 = case_exact_string00;
}
public String getString_09() {
	return string_09;
}
public void setString_09(String string_09) {
	this.string_09 = string_09;
}
public String getString_06() {
	return string_06;
}
public void setString_06(String string_06) {
	this.string_06 = string_06;
}
public String getString_07() {
	return string_07;
}
public void setString_07(String string_07) {
	this.string_07 = string_07;
}
public String getEmployee_type() {
	return employee_type;
}
public void setEmployee_type(String employee_type) {
	this.employee_type = employee_type;
}
public Boolean getEnable() {
	return enable;
}
public void setEnable(Boolean enable) {
	this.enable = enable;
}
public Date getLast_update() {
	return last_update;
}
public void setLast_update(Date last_update) {
	this.last_update = last_update;
}


}
