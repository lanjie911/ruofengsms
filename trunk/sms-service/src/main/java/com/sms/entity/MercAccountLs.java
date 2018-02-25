package com.sms.entity;

import java.io.Serializable;

public class MercAccountLs implements Serializable{

	private static final long serialVersionUID = -8404937874518642392L;
	private Long lsId;
	private Integer transType ;  //类型
	private Long accountNo;  		//商户账号
	private Integer amount;               //单位，条数
	private String mobile;  
	private String createDatetime ;  
	
	public MercAccountLs(){}
	
	public MercAccountLs(Integer transType, Long accountNo, Integer amount,String mobile) {
		super();
		this.transType = transType;
		this.accountNo = accountNo;
		this.amount = amount;
		this.mobile = mobile;
	}

	public Long getLsId() {
		return lsId;
	}
	public void setLsId(Long lsId) {
		this.lsId = lsId;
	}
	public Integer getTransType() {
		return transType;
	}
	public void setTransType(Integer transType) {
		this.transType = transType;
	}
	public Long getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(Long accountNo) {
		this.accountNo = accountNo;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getCreateDatetime() {
		return createDatetime;
	}
	public void setCreateDatetime(String createDatetime) {
		this.createDatetime = createDatetime;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	
}
