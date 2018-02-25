package com.sms.entity.bizdict;

import java.io.Serializable;

public class CustomParam implements Serializable{

	private static final long serialVersionUID = -419892904500588864L;
	
	private Long paramId; 					//主键ID 用户在合作商户唯一标识
	private String paramCode;				//参数编码由基金公司分配 
	private String paramValue;  			//参数值
	private String status;  				//状态 1-使用  2-禁用
	private Integer indexDate;  			//分片日期
	private String remark;					//备注
	private String createDateTime;  		//创建时间
	private String updateDateTime; 		//更新时间
	
	
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
	public Integer getIndexDate() {
		return indexDate;
	}
	public void setIndexDate(Integer indexDate) {
		this.indexDate = indexDate;
	}
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCreateDateTime() {
		return createDateTime;
	}
	public void setCreateDateTime(String createDateTime) {
		this.createDateTime = createDateTime;
	}
	public String getUpdateDateTime() {
		return updateDateTime;
	}
	public void setUpdateDateTime(String updateDateTime) {
		this.updateDateTime = updateDateTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
