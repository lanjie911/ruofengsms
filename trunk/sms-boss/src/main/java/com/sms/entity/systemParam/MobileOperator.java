package com.sms.entity.systemParam;

import java.io.Serializable;

public class MobileOperator implements Serializable {
	private static final long serialVersionUID = -360802140750407284L;
	private Integer phoneOperatorId; 	// 主键ID
	private String pref; 				// 手机号段
	private Integer prefLength; 		// 号段长度
	private Integer supportOperators; 	// 支持运营商
	private String supportOperatorsDes; // 运营商描述

	public Integer getPhoneOperatorId() {
		return phoneOperatorId;
	}
	public void setPhoneOperatorId(Integer phoneOperatorId) {
		this.phoneOperatorId = phoneOperatorId;
	}
	public String getPref() {
		return pref;
	}
	public void setPref(String pref) {
		this.pref = pref;
	}
	public Integer getPrefLength() {
		return prefLength;
	}
	public void setPrefLength(Integer prefLength) {
		this.prefLength = prefLength;
	}
	public Integer getSupportOperators() {
		return supportOperators;
	}
	public void setSupportOperators(Integer supportOperators) {
		this.supportOperators = supportOperators;
	}
	public String getSupportOperatorsDes() {
		return supportOperatorsDes;
	}
	public void setSupportOperatorsDes(String supportOperatorsDes) {
		this.supportOperatorsDes = supportOperatorsDes;
	}

	@Override
	public String toString() {
		return "MobileOperator [phoneOperatorId=" + phoneOperatorId + ", pref=" + pref + ", prefLength=" + prefLength
				+ ", supportOperators=" + supportOperators + ", supportOperatorsDes=" + supportOperatorsDes + "]";
	}
}