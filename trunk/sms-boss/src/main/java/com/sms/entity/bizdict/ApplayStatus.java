package com.sms.entity.bizdict;

import java.io.Serializable;

public class ApplayStatus implements Serializable {
	private static final long serialVersionUID = -4321517329493773027L;
	private Integer applayStatusId;
	private Integer applayStatus;
	private String applayStatusDes;
	
	public Integer getApplayStatusId() {
		return applayStatusId;
	}
	public Integer getApplayStatus() {
		return applayStatus;
	}
	public String getApplayStatusDes() {
		return applayStatusDes;
	}
	public void setApplayStatusId(Integer applayStatusId) {
		this.applayStatusId = applayStatusId;
	}
	public void setApplayStatus(Integer applayStatus) {
		this.applayStatus = applayStatus;
	}
	public void setApplayStatusDes(String applayStatusDes) {
		this.applayStatusDes = applayStatusDes;
	}
}