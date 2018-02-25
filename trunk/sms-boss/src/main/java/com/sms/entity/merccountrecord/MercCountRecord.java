package com.sms.entity.merccountrecord;

import java.io.Serializable;

public class MercCountRecord implements Serializable{

	private static final long serialVersionUID = -8404937874518642392L;
	private Long merchantId ;  //商户编号
	private Long accountNo;  //商户账号
	private String merchantName;               //商户名称
	private Integer paymentMethods;   //付费方式
	private String paymentMethodsDes;   //付费方式描述
	private Long smsGroupId;  //短信通道组
	private String smsGroupDesc ;   //短信通道组描述
	
	private Integer accountType ;  //账户类型
	private String accountTypeDes ;  //账户类型描述
	
	private Integer depositSum;   //充值
	private Integer paySum;  //消费
	private Integer paySumSuccess;  //成功
	private Integer paySumFail;  //失败
	public Long getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}
	public Long getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(Long accountNo) {
		this.accountNo = accountNo;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
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
	public Long getSmsGroupId() {
		return smsGroupId;
	}
	public void setSmsGroupId(Long smsGroupId) {
		this.smsGroupId = smsGroupId;
	}
	public String getSmsGroupDesc() {
		return smsGroupDesc;
	}
	public void setSmsGroupDesc(String smsGroupDesc) {
		this.smsGroupDesc = smsGroupDesc;
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
	public Integer getDepositSum() {
		return depositSum;
	}
	public void setDepositSum(Integer depositSum) {
		this.depositSum = depositSum;
	}
	public Integer getPaySum() {
		return paySum;
	}
	public void setPaySum(Integer paySum) {
		this.paySum = paySum;
	}
	public Integer getPaySumSuccess() {
		return paySumSuccess;
	}
	public void setPaySumSuccess(Integer paySumSuccess) {
		this.paySumSuccess = paySumSuccess;
	}
	public Integer getPaySumFail() {
		return paySumFail;
	}
	public void setPaySumFail(Integer paySumFail) {
		this.paySumFail = paySumFail;
	}
}
