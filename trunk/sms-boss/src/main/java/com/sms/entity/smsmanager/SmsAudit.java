package com.sms.entity.smsmanager;

import java.io.Serializable;
/**
 * @author Cao
 * 通道
 */
public class SmsAudit implements Serializable{

	private static final long serialVersionUID = -1L;
	
	private Long auditingId;
	private Long accountNo;
	private String merchantNameAbbreviation;
	private Integer accountType;
	private String accountTypeDes;
	private Integer orderFlag;
	private String orderFlagDes;
	private String reservationDatetime;
	private String batchNo;
	private String mobile;
	private String smsContent;
	private Integer smsCount;
	private Integer smsUnitCount;
	private Integer smsAccountNum;
	private Integer auditingStatus;
	private String auditingStatusDes;
	private String auditingOperator;
	private String registrars;
	private String createDatetime;
	private String auditingDatetime;
	private Long channelId; //	通道编号
	private String channelName; //	通道名称
	private String signTip; //	签名
	private Integer costTip; //	签名
	
	public String getSignTip() {
		return signTip;
	}
	public void setSignTip(String signTip) {
		this.signTip = signTip;
	}
	public String getOrderFlagDes() {
		return orderFlagDes;
	}
	public void setOrderFlagDes(String orderFlagDes) {
		this.orderFlagDes = orderFlagDes;
	}
	public String getAuditingStatusDes() {
		return auditingStatusDes;
	}
	public void setAuditingStatusDes(String auditingStatusDes) {
		this.auditingStatusDes = auditingStatusDes;
	}
	public Long getChannelId() {
		return channelId;
	}
	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	public String getAccountTypeDes() {
		return accountTypeDes;
	}
	public void setAccountTypeDes(String accountTypeDes) {
		this.accountTypeDes = accountTypeDes;
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
	public String getRegistrars() {
		return registrars;
	}
	public void setRegistrars(String registrars) {
		this.registrars = registrars;
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
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public Integer getCostTip() {
		return costTip;
	}
	public void setCostTip(Integer costTip) {
		this.costTip = costTip;
	}
	public Integer getSmsAccountNum() {
		return smsAccountNum;
	}
	public void setSmsAccountNum(Integer smsAccountNum) {
		this.smsAccountNum = smsAccountNum;
	}
}
