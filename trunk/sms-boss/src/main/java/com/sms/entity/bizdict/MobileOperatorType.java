package com.sms.entity.bizdict;

import java.io.Serializable;

public class MobileOperatorType implements Serializable {
	private static final long serialVersionUID = -559575221295475830L;
	private Integer mobileOperatorId;
	private Integer mobileOperator;
	private String mobileOperatorDes;
	
	public Integer getMobileOperatorId() {
		return mobileOperatorId;
	}
	public Integer getMobileOperator() {
		return mobileOperator;
	}
	public String getMobileOperatorDes() {
		return mobileOperatorDes;
	}
	public void setMobileOperatorId(Integer mobileOperatorId) {
		this.mobileOperatorId = mobileOperatorId;
	}
	public void setMobileOperator(Integer mobileOperator) {
		this.mobileOperator = mobileOperator;
	}
	public void setMobileOperatorDes(String mobileOperatorDes) {
		this.mobileOperatorDes = mobileOperatorDes;
	}
}