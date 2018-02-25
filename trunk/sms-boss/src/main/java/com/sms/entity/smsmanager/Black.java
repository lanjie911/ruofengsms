package com.sms.entity.smsmanager;

import java.io.Serializable;
/**
 * @author Cao
 * 通道
 */
public class Black implements Serializable{

	private static final long serialVersionUID = -1L;
	
	private Long unsubscribeId;
	private Integer transType;
	private String transTypeDes;
	private String unsubscribeMobile;
	private Integer unsubscribeStatus;
	private String unsubscribeStatusDes;
	private String createDatetime;
	private String remark;
	
	public Long getUnsubscribeId() {
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
	public String getTransTypeDes() {
		return transTypeDes;
	}
	public void setTransTypeDes(String transTypeDes) {
		this.transTypeDes = transTypeDes;
	}
	public Integer getUnsubscribeStatus() {
		return unsubscribeStatus;
	}
	public void setUnsubscribeStatus(Integer unsubscribeStatus) {
		this.unsubscribeStatus = unsubscribeStatus;
	}
	public String getUnsubscribeStatusDes() {
		return unsubscribeStatusDes;
	}
	public void setUnsubscribeStatusDes(String unsubscribeStatusDes) {
		this.unsubscribeStatusDes = unsubscribeStatusDes;
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
	public String getUnsubscribeMobile() {
		return unsubscribeMobile;
	}
	public void setUnsubscribeMobile(String unsubscribeMobile) {
		this.unsubscribeMobile = unsubscribeMobile;
	}
	
}
