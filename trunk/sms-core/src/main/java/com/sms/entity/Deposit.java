package com.sms.entity;

import java.io.Serializable;
/**
 * @author Cao
 * 充值
 */
public class Deposit implements Serializable{

	private static final long serialVersionUID = -1L;
	
	private Long depositId;
	private String merchantNameAbbreviation;
	private Long accountNo;
	private Double amount;
	private Long depositNum;
	private Double uniPrice;
	private Integer depositType;
	private String createDatetime;
	private Integer depositStatus;
	private String remark;
	private String operator;
	
	public Long getDepositId() {
		return depositId;
	}
	public void setDepositId(Long depositId) {
		this.depositId = depositId;
	}
	public String getMerchantNameAbbreviation() {
		return merchantNameAbbreviation;
	}
	public void setMerchantNameAbbreviation(String merchantNameAbbreviation) {
		this.merchantNameAbbreviation = merchantNameAbbreviation;
	}
	public Long getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(Long accountNo) {
		this.accountNo = accountNo;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Long getDepositNum() {
		return depositNum;
	}
	public void setDepositNum(Long depositNum) {
		this.depositNum = depositNum;
	}
	public Double getUniPrice() {
		return uniPrice;
	}
	public void setUniPrice(Double uniPrice) {
		this.uniPrice = uniPrice;
	}
	public Integer getDepositType() {
		return depositType;
	}
	public void setDepositType(Integer depositType) {
		this.depositType = depositType;
	}
	public String getCreateDatetime() {
		return createDatetime;
	}
	public void setCreateDatetime(String createDatetime) {
		this.createDatetime = createDatetime;
	}
	public Integer getDepositStatus() {
		return depositStatus;
	}
	public void setDepositStatus(Integer depositStatus) {
		this.depositStatus = depositStatus;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	
}
