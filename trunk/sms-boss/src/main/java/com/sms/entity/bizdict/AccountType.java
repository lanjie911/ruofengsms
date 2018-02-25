package com.sms.entity.bizdict;

import java.io.Serializable;

public class AccountType implements Serializable {
	private static final long serialVersionUID = 6235247443956872801L;
	private Integer accountTypeId;
	private Integer accountType;
	private String accountTypeDes;
	
	public Integer getAccountTypeId() {
		return accountTypeId;
	}
	public Integer getAccountType() {
		return accountType;
	}
	public String getAccountTypeDes() {
		return accountTypeDes;
	}
	public void setAccountTypeId(Integer accountTypeId) {
		this.accountTypeId = accountTypeId;
	}
	public void setAccountType(Integer accountType) {
		this.accountType = accountType;
	}
	public void setAccountTypeDes(String accountTypeDes) {
		this.accountTypeDes = accountTypeDes;
	}
}