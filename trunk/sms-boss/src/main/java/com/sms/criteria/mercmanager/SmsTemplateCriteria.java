package com.sms.criteria.mercmanager;

import com.sms.criteria.AbstractCriteria;

public class SmsTemplateCriteria extends AbstractCriteria {
	private Long merchantId;                   //商户
	private Integer mercAccountType;               //商户类型
	private Long mercAccountNo;              //商户账号
	private Integer templateStatus;            //商户账户状态
	
	public Long getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}
	public Integer getMercAccountType() {
		return mercAccountType;
	}
	public void setMercAccountType(Integer mercAccountType) {
		this.mercAccountType = mercAccountType;
	}
	public Long getMercAccountNo() {
		return mercAccountNo;
	}
	public void setMercAccountNo(Long mercAccountNo) {
		this.mercAccountNo = mercAccountNo;
	}
	public Integer getTemplateStatus() {
		return templateStatus;
	}
	public void setTemplateStatus(Integer templateStatus) {
		this.templateStatus = templateStatus;
	}
	
}