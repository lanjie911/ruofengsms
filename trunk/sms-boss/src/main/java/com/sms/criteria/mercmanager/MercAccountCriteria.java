package com.sms.criteria.mercmanager;

import com.sms.criteria.AbstractCriteria;

public class MercAccountCriteria extends AbstractCriteria {
	private Long merchantId;                   //商户
	private String merchantName;               //商户名称
	private Long mercAccountNo;              //商户账号
	private Integer mercAccountStatus;            //商户账户状态
	
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
	public Long getMercAccountNo() {
		return mercAccountNo;
	}
	public void setMercAccountNo(Long mercAccountNo) {
		this.mercAccountNo = mercAccountNo;
	}
	public Integer getMercAccountStatus() {
		return mercAccountStatus;
	}
	public void setMercAccountStatus(Integer mercAccountStatus) {
		this.mercAccountStatus = mercAccountStatus;
	}
	
}