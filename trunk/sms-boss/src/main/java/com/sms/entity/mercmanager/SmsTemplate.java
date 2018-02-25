package com.sms.entity.mercmanager;

import java.io.Serializable;

/**
 * @author Cao
 * 短信模板报备
 */
public class SmsTemplate implements Serializable{

	private static final long serialVersionUID = -1L;
	
	private Long templateId;
	private Long accountNo;
	private String merchantNameAbbreviation;
	private String merchantName;
	private Integer accountType;
	private String accountTypeDes;
	private String templateContent;
	private Integer templateLength;
	private String operator;
	private Integer status;
	private String statusDes;
	private String remark;
	private String createDatetime;
	private Integer paymentMethods;
	private String paymentMethodsDes;
	private Long extendNo;
	
	
	public Long getExtendNo() {
		return extendNo;
	}
	public void setExtendNo(Long extendNo) {
		this.extendNo = extendNo;
	}
	public Integer getPaymentMethods() {
		return paymentMethods;
	}
	public void setPaymentMethods(Integer paymentMethods) {
		this.paymentMethods = paymentMethods;
	}
	public String getPaymentMethodsDes() {
		return paymentMethodsDes;
	}
	public void setPaymentMethodsDes(String paymentMethodsDes) {
		this.paymentMethodsDes = paymentMethodsDes;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public Long getTemplateId() {
		return templateId;
	}
	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
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
	public String getAccountTypeDes() {
		return accountTypeDes;
	}
	public void setAccountTypeDes(String accountTypeDes) {
		this.accountTypeDes = accountTypeDes;
	}
	public String getTemplateContent() {
		return templateContent;
	}
	public void setTemplateContent(String templateContent) {
		this.templateContent = templateContent;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getStatusDes() {
		return statusDes;
	}
	public void setStatusDes(String statusDes) {
		this.statusDes = statusDes;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCreateDatetime() {
		return createDatetime;
	}
	public void setCreateDatetime(String createDatetime) {
		this.createDatetime = createDatetime;
	}
	public Integer getTemplateLength() {
		return templateLength;
	}
	public void setTemplateLength(Integer templateLength) {
		this.templateLength = templateLength;
	}
	
}
