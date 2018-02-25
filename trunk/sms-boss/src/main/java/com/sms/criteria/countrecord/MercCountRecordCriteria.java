package com.sms.criteria.countrecord;

import com.sms.criteria.AbstractCriteria;

public class MercCountRecordCriteria extends AbstractCriteria {
	private Long merchantId;                   //商户
	private String merchantName;               //商户名称
	private Long mercAccountNo;              //商户账号
	private Integer accountType;            //商户账户类型
	private Integer paymentMethods;            //付费方式
	private Integer smsGroupId;            //短信通道
	private String recordDateBegin;               //报表日期开始
	private String recordDateEnd;               //报表日期结束
	
	
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
	public Long getMercAccountNo() {
		return mercAccountNo;
	}
	public void setMercAccountNo(Long mercAccountNo) {
		this.mercAccountNo = mercAccountNo;
	}
	public Integer getAccountType() {
		return accountType;
	}
	public void setAccountType(Integer accountType) {
		this.accountType = accountType;
	}
	public Integer getPaymentMethods() {
		return paymentMethods;
	}
	public void setPaymentMethods(Integer paymentMethods) {
		this.paymentMethods = paymentMethods;
	}
	public Integer getSmsGroupId() {
		return smsGroupId;
	}
	public void setSmsGroupId(Integer smsGroupId) {
		this.smsGroupId = smsGroupId;
	}
	public String getRecordDateBegin() {
		return recordDateBegin;
	}
	public void setRecordDateBegin(String recordDateBegin) {
		this.recordDateBegin = recordDateBegin;
	}
	public String getRecordDateEnd() {
		return recordDateEnd;
	}
	public void setRecordDateEnd(String recordDateEnd) {
		this.recordDateEnd = recordDateEnd;
	}
	
}