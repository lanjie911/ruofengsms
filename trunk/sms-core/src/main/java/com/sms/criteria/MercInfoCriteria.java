package com.sms.criteria;

public class MercInfoCriteria extends AbstractCriteria {
	private Long merchantId;                   //商户
	private String merchantName;               //商户名称
	private String legalRepresentative;              //法人姓名
	private Integer merchantStatus;            //商户状态
		
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
	public String getLegalRepresentative() {
		return legalRepresentative;
	}
	public void setLegalRepresentative(String legalRepresentative) {
		this.legalRepresentative = legalRepresentative;
	}
	public Integer getMerchantStatus() {
		return merchantStatus;
	}
	public void setMerchantStatus(Integer merchantStatus) {
		this.merchantStatus = merchantStatus;
	}
	
	
}