package com.sms.criteria.trademanager;

import com.sms.criteria.AbstractCriteria;

public class SmsCriteria extends AbstractCriteria {
	
	private String dateBegin; //开始日期
	private String dateEnd; //结束日期
	private String mobile; //手机号
	
	public String getDateBegin() {
		return dateBegin;
	}
	public void setDateBegin(String dateBegin) {
		this.dateBegin = dateBegin;
	}
	public String getDateEnd() {
		return dateEnd;
	}
	public void setDateEnd(String dateEnd) {
		this.dateEnd = dateEnd;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	
}