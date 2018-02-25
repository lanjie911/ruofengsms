package com.sms.criteria.manager;

import com.sms.criteria.AbstractCriteria;

public class UserCriteria extends AbstractCriteria{
	private Long userId;
	private String userName;
	private String userLoginName;
	private String email;
	private String dkSerialNumber;
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserLoginName() {
		return userLoginName;
	}
	public void setUserLoginName(String userLoginName) {
		this.userLoginName = userLoginName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDkSerialNumber() {
		return dkSerialNumber;
	}
	public void setDkSerialNumber(String dkSerialNumber) {
		this.dkSerialNumber = dkSerialNumber;
	}
}
