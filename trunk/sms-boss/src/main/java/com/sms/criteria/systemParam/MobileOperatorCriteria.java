package com.sms.criteria.systemParam;

import com.sms.criteria.AbstractCriteria;

public class MobileOperatorCriteria extends AbstractCriteria {
	private Long phoneOperatorId;
	private String pref;
	private Integer supportOperators;

	public Long getPhoneOperatorId() {
		return phoneOperatorId;
	}
	public void setPhoneOperatorId(Long phoneOperatorId) {
		this.phoneOperatorId = phoneOperatorId;
	}
	public String getPref() {
		return pref;
	}
	public void setPref(String pref) {
		this.pref = pref;
	}
	public Integer getSupportOperators() {
		return supportOperators;
	}
	public void setSupportOperators(Integer supportOperators) {
		this.supportOperators = supportOperators;
	} 
}