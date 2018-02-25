package com.sms.entity.mercmanager;

import java.io.Serializable;

public class MercUser implements Serializable{

	private static final long serialVersionUID = -1004732004471466507L;
	
	private Long operatorId;
	private String operatorLoginName;
	private String operatorName;
	private String operatorLoginPassword;
	private String pwdErrCnt;
	private String expirationDate;
	private Long mobile;
	private Long phone;
	private String email;
	private String operatorStatus;
	private String operatorStatusDes;
	private String createDatetime;
	private String updateDatetime;
	private String remark;
	private Long merchantId;
	private String merchantName;
	
	public Long getOperatorId() {
		return operatorId;
	}
	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}
	public String getOperatorLoginName() {
		return operatorLoginName;
	}
	public void setOperatorLoginName(String operatorLoginName) {
		this.operatorLoginName = operatorLoginName;
	}
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	public String getOperatorLoginPassword() {
		return operatorLoginPassword;
	}
	public void setOperatorLoginPassword(String operatorLoginPassword) {
		this.operatorLoginPassword = operatorLoginPassword;
	}
	public String getPwdErrCnt() {
		return pwdErrCnt;
	}
	public void setPwdErrCnt(String pwdErrCnt) {
		this.pwdErrCnt = pwdErrCnt;
	}
	public String getExpirationDate() {
		return expirationDate;
	}
	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getOperatorStatus() {
		return operatorStatus;
	}
	public void setOperatorStatus(String operatorStatus) {
		this.operatorStatus = operatorStatus;
	}
	public String getOperatorStatusDes() {
		return operatorStatusDes;
	}
	public void setOperatorStatusDes(String operatorStatusDes) {
		this.operatorStatusDes = operatorStatusDes;
	}
	public String getCreateDatetime() {
		return createDatetime;
	}
	public void setCreateDatetime(String createDatetime) {
		this.createDatetime = createDatetime;
	}
	public String getUpdateDatetime() {
		return updateDatetime;
	}
	public void setUpdateDatetime(String updateDatetime) {
		this.updateDatetime = updateDatetime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Long getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public Long getMobile() {
		return mobile;
	}
	public void setMobile(Long mobile) {
		this.mobile = mobile;
	}
	public Long getPhone() {
		return phone;
	}
	public void setPhone(Long phone) {
		this.phone = phone;
	}
	
}
