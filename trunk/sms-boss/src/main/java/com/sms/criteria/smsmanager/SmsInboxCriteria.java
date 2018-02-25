package com.sms.criteria.smsmanager;

import com.sms.criteria.AbstractCriteria;

public class SmsInboxCriteria extends AbstractCriteria {
	
	private Long mobile; //手机号
	private String smsContent; //短信内容
	
	public Long getMobile() {
		return mobile;
	}
	public void setMobile(Long mobile) {
		this.mobile = mobile;
	}
	public String getSmsContent() {
		return smsContent;
	}
	public void setSmsContent(String smsContent) {
		this.smsContent = smsContent;
	}
}