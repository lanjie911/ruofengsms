package com.sms.entity.smsmanager;

import java.io.Serializable;
/**
 * @author Cao
 * 通道
 */
public class SmsInbox implements Serializable{

	private static final long serialVersionUID = -1L;
	
	private Long smsId; //	记录编号
	private Long mobile; //	手机号
	private String content; //	短信内容
	private Integer status; //	短信状态
	private String statusDes; //	短信状态描述
	private Integer types; //	短信类型
	private String typesDes; //	短信类型描述
	private String createDatetime; //	创建时间
	private String updateDatetime; //	更新时间
	
	public Long getSmsId() {
		return smsId;
	}
	public void setSmsId(Long smsId) {
		this.smsId = smsId;
	}
	public Long getMobile() {
		return mobile;
	}
	public void setMobile(Long mobile) {
		this.mobile = mobile;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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
	public Integer getTypes() {
		return types;
	}
	public void setTypes(Integer types) {
		this.types = types;
	}
	public String getTypesDes() {
		return typesDes;
	}
	public void setTypesDes(String typesDes) {
		this.typesDes = typesDes;
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
	
}
