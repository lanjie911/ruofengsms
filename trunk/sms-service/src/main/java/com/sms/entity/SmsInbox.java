package com.sms.entity;

import java.io.Serializable;

/**
 * @author Cao
 * 短信收件箱
 */
public class SmsInbox implements Serializable{

	private static final long serialVersionUID = -1L;
	
	private Long smsId;
	private String mobile;
	private String content;
	private Integer status;
	private Integer types;
	private String createDatetime;
	private String updateDatetime;
	
	public SmsInbox(){}
	
	public SmsInbox(String mobile, String content, Integer types) {
		super();
		this.mobile = mobile;
		this.content = content;
		this.types = types;
	}

	public Long getSmsId() {
		return smsId;
	}

	public void setSmsId(Long smsId) {
		this.smsId = smsId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
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

	public Integer getTypes() {
		return types;
	}

	public void setTypes(Integer types) {
		this.types = types;
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
