package com.sms.entity.smsmanager;

import java.io.Serializable;
/**
 * @author Cao
 * 通道
 */
public class White implements Serializable{

	private static final long serialVersionUID = -1L;
	
	private Long whiteId;
	private String mobile;
	private Integer status;
	private String statusDes;
	private String createDatetime;
	private String updateDatetime;
	private String remark;
	
	public Long getWhiteId() {
		return whiteId;
	}
	public void setWhiteId(Long whiteId) {
		this.whiteId = whiteId;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getStatusDes() {
		return statusDes;
	}
	public void setStatusDes(String statusDes) {
		this.statusDes = statusDes;
	}
	public String getCreateDatetime() {
		return createDatetime;
	}
	public void setCreateDatetime(String createDatetime) {
		this.createDatetime = createDatetime;
	}
	public String getUpdateDatetime() {
		return updateDatetime;
	}
	public void setUpdateDatetime(String updateDatetime) {
		this.updateDatetime = updateDatetime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
