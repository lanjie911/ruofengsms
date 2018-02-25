package com.sms.entity.mercmanager;

import java.io.Serializable;

public class CusManager implements Serializable{

	private static final long serialVersionUID = -8404937874518642392L;
	private Long cusManagerId;  
	private Long cusManagerMobile ;  
	private String cusManagerName;               
	private String cusManagerEmail;
	private String merchantId;
	private String merchantName;
	
	public Long getCusManagerId() {
		return cusManagerId;
	}
	public void setCusManagerId(Long cusManagerId) {
		this.cusManagerId = cusManagerId;
	}
	public Long getCusManagerMobile() {
		return cusManagerMobile;
	}
	public void setCusManagerMobile(Long cusManagerMobile) {
		this.cusManagerMobile = cusManagerMobile;
	}
	public String getCusManagerName() {
		return cusManagerName;
	}
	public void setCusManagerName(String cusManagerName) {
		this.cusManagerName = cusManagerName;
	}
	public String getCusManagerEmail() {
		return cusManagerEmail;
	}
	public void setCusManagerEmail(String cusManagerEmail) {
		this.cusManagerEmail = cusManagerEmail;
	}
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
}
