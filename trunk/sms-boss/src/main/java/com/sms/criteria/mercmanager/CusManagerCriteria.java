package com.sms.criteria.mercmanager;

import com.sms.criteria.AbstractCriteria;

public class CusManagerCriteria extends AbstractCriteria {
	
	private String managername;              
	private Long managermobile;  
	private String cusManagerId;   
	
	public String getManagername() {
		return managername;
	}
	public void setManagername(String managername) {
		this.managername = managername;
	}
	public Long getManagermobile() {
		return managermobile;
	}
	public void setManagermobile(Long managermobile) {
		this.managermobile = managermobile;
	}
	public String getCusManagerId() {
		return cusManagerId;
	}
	public void setCusManagerId(String cusManagerId) {
		this.cusManagerId = cusManagerId;
	}
	
	
}