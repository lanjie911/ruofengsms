package com.sms.entity;

import java.io.Serializable;

public class MercChannel implements Serializable {

	private static final long serialVersionUID = -1L;
	
	private Long accountNo;
	private Long channelId;
	private Integer channelAttribute;
	private String channelDes;
	private String supportOperators;	//100-电信   200-联通  300-移动   400-全网通 500-第三方  600-其它
	private String supportOperatorsDes;
	
	public Long getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(Long accountNo) {
		this.accountNo = accountNo;
	}
	public Long getChannelId() {
		return channelId;
	}
	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}
	public String getChannelDes() {
		return channelDes;
	}
	public void setChannelDes(String channelDes) {
		this.channelDes = channelDes;
	}
	public String getSupportOperators() {
		return supportOperators;
	}
	public void setSupportOperators(String supportOperators) {
		this.supportOperators = supportOperators;
	}
	public Integer getChannelAttribute() {
		return channelAttribute;
	}
	public void setChannelAttribute(Integer channelAttribute) {
		this.channelAttribute = channelAttribute;
	}
	public String getSupportOperatorsDes() {
		return supportOperatorsDes;
	}
	public void setSupportOperatorsDes(String supportOperatorsDes) {
		this.supportOperatorsDes = supportOperatorsDes;
	}
}