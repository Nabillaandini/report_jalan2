package ca.iam.backing;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Named;

import ca.iam.core.BasicSessionBacking;
import ca.iam.rules.SellRules;

@ManagedBean
@SessionScoped
@Stateful
@Named
public class SellBacking extends BasicSessionBacking {
	private static final long serialVersionUID = 8603100471162787393L;

	@EJB
	public SellRules sellRules;

	private String headName;
	private String currency;
	private String payment;
	private String rateString;
	private String grandTotalString;
	private String customerName;
	private String ktpNo;
	private String purchaseNo;
	private String remainString;
	private BigDecimal amount;
	private BigDecimal rate;
	private BigDecimal grandTotal;
	private String customer;
	private String transaction;
	private String transactionListSelect;
	private List<String> transactionList = new ArrayList<String>();
	private HashMap<String, Object> parameters = new HashMap<String, Object>();
	private boolean disCustomerName = false;
	int no = 1;

	public String getHeadName() {
		return headName;
	}

	public void setHeadName(String headName) {
		this.headName = headName;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getPayment() {
		return payment;
	}

	public void setPayment(String payment) {
		this.payment = payment;
	}

	public String getRateString() {
		return rateString;
	}

	public void setRateString(String rateString) {
		this.rateString = rateString;
	}

	public String getGrandTotalString() {
		return grandTotalString;
	}

	public void setGrandTotalString(String grandTotalString) {
		this.grandTotalString = grandTotalString;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getKtpNo() {
		return ktpNo;
	}

	public void setKtpNo(String ktpNo) {
		this.ktpNo = ktpNo;
	}

	public String getPurchaseNo() {
		return purchaseNo;
	}

	public void setPurchaseNo(String purchaseNo) {
		this.purchaseNo = purchaseNo;
	}

	public String getRemainString() {
		return remainString;
	}

	public void setRemainString(String remainString) {
		this.remainString = remainString;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public BigDecimal getGrandTotal() {
		return grandTotal;
	}

	public void setGrandTotal(BigDecimal grandTotal) {
		this.grandTotal = grandTotal;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public String getTransaction() {
		return transaction;
	}

	public void setTransaction(String transaction) {
		this.transaction = transaction;
	}

	public String getTransactionListSelect() {
		return transactionListSelect;
	}

	public void setTransactionListSelect(String transactionListSelect) {
		this.transactionListSelect = transactionListSelect;
	}

	public List<String> getTransactionList() {
		return transactionList;
	}

	public void setTransactionList(List<String> transactionList) {
		this.transactionList = transactionList;
	}

	public boolean isDisCustomerName() {
		return disCustomerName;
	}

	public void setDisCustomerName(boolean disCustomerName) {
		this.disCustomerName = disCustomerName;
	}

	public void load(ComponentSystemEvent event) {
		FacesContext context = FacesContext.getCurrentInstance();
		if (!context.isPostback()) {
			headName = "Report Summary";
			remainString = null;

		}
	}
}
