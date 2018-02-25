package com.sms.entity;

import java.io.Serializable;

/**
 * @author Cao
 * 退订列表
 */
public class Unsubscribe implements Serializable{

	private static final long serialVersionUID = -1L;
	
	private Long unsubscribeId;
	private Integer transType;
	private String unsubscribeMobile;
	private Integer unsubscribeStatus;
	private String createDatetime;
	private String remark;
	
	public Unsubscribe(){}
	
	public Unsubscribe(Integer transType, String unsubscribeMobile, Integer unsubscribeStatus, String remark) {
		super();
		this.transType = transType;
		this.unsubscribeMobile = unsubscribeMobile;
		this.unsubscribeStatus = unsubscribeStatus;
		this.remark = remark;
	}

	public Unsubscribe(String unsubscribeMobile) {
		this.unsubscribeMobile = unsubscribeMobile;
	}

	public Long getUnsubscribeId(){
		return unsubscribeId;
	}
	public void setUnsubscribeId(Long unsubscribeId) {
		this.unsubscribeId = unsubscribeId;
	}
	public Integer getTransType() {
		return transType;
	}
	public void setTransType(Integer transType) {
		this.transType = transType;
	}
	public String getUnsubscribeMobile() {
		return unsubscribeMobile;
	}
	public void setUnsubscribeMobile(String unsubscribeMobile) {
		this.unsubscribeMobile = unsubscribeMobile;
	}
	public Integer getUnsubscribeStatus() {
		return unsubscribeStatus;
	}
	public void setUnsubscribeStatus(Integer unsubscribeStatus) {
		this.unsubscribeStatus = unsubscribeStatus;
	}
	public String getCreateDatetime() {
		return createDatetime;
	}
	public void setCreateDatetime(String createDatetime) {
		this.createDatetime = createDatetime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
