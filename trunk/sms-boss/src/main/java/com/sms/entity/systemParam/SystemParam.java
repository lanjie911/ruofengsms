package com.sms.entity.systemParam;

import java.io.Serializable;

public class SystemParam implements Serializable{
	
	private static final long serialVersionUID = -987259005477582500L;
	
	private Long paramId; 					//主键ID 用户在合作商户唯一标识
	private String paramCode;				//参数编码由基金公司分配 
	private String paramValue;  			//参数值
	private String remark;					//备注
	
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
	public String getParamValue() {
		return paramValue;
	}
	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}