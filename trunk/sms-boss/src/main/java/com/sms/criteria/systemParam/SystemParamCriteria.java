package com.sms.criteria.systemParam;

import com.sms.criteria.AbstractCriteria;

public class SystemParamCriteria extends AbstractCriteria {
	private Long paramId; 					
	private String paramCode;
	
	public Long getParamId() {
		return paramId;
	}
	public void setParamId(Long paramId) {
		this.paramId = paramId;
	}
	public String getParamCode() {
		return paramCode;
	}
	public void setParamCode(String paramCode) {
		this.paramCode = paramCode;
	}
	
	
	
}