package com.sms.entity;

import java.io.Serializable;
/**
 * @author Cao
 * 短信发送审核
 */
public class Auditing implements Serializable{

	private static final long serialVersionUID = -1L;
	
	private Long auditingId;
	private Long accountNo;
	private String merchantNameAbbreviation;
	private Integer accountType;
	private Integer orderFlag;
	private String reservationDatetime;
	private String batchNo;
	private String mobile;
	private String smsContent;
	private Integer smsCount;
	private Integer smsUnitCount;
	private Integer smsAccountNum;
	private Integer auditingStatus;
	private String auditingOperator;
	private String regiStrars;
	private String createDatetime;
	private String auditingDatetime;
	private String costTip;
	private String signTip;
	
	public Auditing(){}
	
	public Auditing(Long accountNo, String merchantNameAbbreviation, Integer accountType, Integer orderFlag,
			String reservationDatetime, String batchNo, String mobile, String smsContent, Integer smsCount,
			Integer smsUnitCount, Integer smsAccountNum, Integer auditingStatus,String signTip) {
		this.accountNo = accountNo;
		this.merchantNameAbbreviation = merchantNameAbbreviation;
		this.accountType = accountType;
		this.orderFlag = orderFlag;
		this.reservationDatetime = reservationDatetime;
		this.batchNo = batchNo;
		this.mobile = mobile;
		this.smsContent = smsContent;
		this.smsCount = smsCount;
		this.smsUnitCount = smsUnitCount;
		this.smsAccountNum = smsAccountNum;
		this.auditingStatus = auditingStatus;
		this.setSignTip(signTip);
	}

	public Long getAuditingId() {
		return auditingId;
	}
	public void setAuditingId(Long auditingId) {
		this.auditingId = auditingId;
	}
	public Long getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(Long accountNo) {
		this.accountNo = accountNo;
	}
	public String getMerchantNameAbbreviation() {
		return merchantNameAbbreviation;
	}
	public void setMerchantNameAbbreviation(String merchantNameAbbreviation) {
		this.merchantNameAbbreviation = merchantNameAbbreviation;
	}
	public Integer getAccountType() {
		return accountType;
	}
	public void setAccountType(Integer accountType) {
		this.accountType = accountType;
	}
	public Integer getOrderFlag() {
		return orderFlag;
	}
	public void setOrderFlag(Integer orderFlag) {
		this.orderFlag = orderFlag;
	}
	public String getReservationDatetime() {
		return reservationDatetime;
	}
	public void setReservationDatetime(String reservationDatetime) {
		this.reservationDatetime = reservationDatetime;
	}
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getSmsContent() {
		return smsContent;
	}
	public void setSmsContent(String smsContent) {
		this.smsContent = smsContent;
	}
	public Integer getSmsCount() {
		return smsCount;
	}
	public void setSmsCount(Integer smsCount) {
		this.smsCount = smsCount;
	}
	public Integer getSmsUnitCount() {
		return smsUnitCount;
	}
	public void setSmsUnitCount(Integer smsUnitCount) {
		this.smsUnitCount = smsUnitCount;
	}
	public Integer getSmsAccountNum() {
		return smsAccountNum;
	}
	public void setSmsAccountNum(Integer smsAccountNum) {
		this.smsAccountNum = smsAccountNum;
	}
	public Integer getAuditingStatus() {
		return auditingStatus;
	}
	public void setAuditingStatus(Integer auditingStatus) {
		this.auditingStatus = auditingStatus;
	}
	public String getAuditingOperator() {
		return auditingOperator;
	}
	public void setAuditingOperator(String auditingOperator) {
		this.auditingOperator = auditingOperator;
	}
	public String getCreateDatetime() {
		return createDatetime;
	}
	public void setCreateDatetime(String createDatetime) {
		this.createDatetime = createDatetime;
	}
	public String getAuditingDatetime() {
		return auditingDatetime;
	}
	public void setAuditingDatetime(String auditingDatetime) {
		this.auditingDatetime = auditingDatetime;
	}
	public String getRegiStrars() {
		return regiStrars;
	}
	public void setRegiStrars(String regiStrars) {
		this.regiStrars = regiStrars;
	}

	public String getCostTip() {
		return costTip;
	}

	public void setCostTip(String costTip) {
		this.costTip = costTip;
	}

	public String getSignTip() {
		return signTip;
	}

	public void setSignTip(String signTip) {
		this.signTip = signTip;
	}
	
}
