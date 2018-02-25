package com.sms.criteria.mercmanager;

import com.sms.criteria.AbstractCriteria;

public class MercUserCriteria extends AbstractCriteria {
	private Long merchantId;                   //商户
	private String merchantName;               //商户名称
	private String userName;              //用户名
	private Integer userMobile;            //用户手机号
	
	public Long getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Integer getUserMobile() {
		return userMobile;
	}
	public void setUserMobile(Integer userMobile) {
		this.userMobile = userMobile;
	}
	
}