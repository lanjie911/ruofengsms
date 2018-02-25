package com.sms.criteria;


public class CustomParamCriteria extends AbstractCriteria {
	private Long paramId; 					
	private String paramCode;				 
	private Integer status;  				
	
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
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
}