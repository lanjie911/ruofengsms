package com.sms.entity.bizdict;

import java.io.Serializable;

public class BatchType implements Serializable {
	private static final long serialVersionUID = -7483197455997705970L;
	private Integer batchTypeId;
	private Integer batchType;
	private String batchTypeDes;
	
	public Integer getBatchTypeId() {
		return batchTypeId;
	}
	public Integer getBatchType() {
		return batchType;
	}
	public String getBatchTypeDes() {
		return batchTypeDes;
	}
	public void setBatchTypeId(Integer batchTypeId) {
		this.batchTypeId = batchTypeId;
	}
	public void setBatchType(Integer batchType) {
		this.batchType = batchType;
	}
	public void setBatchTypeDes(String batchTypeDes) {
		this.batchTypeDes = batchTypeDes;
	}
}