package com.sms.entity;

import java.io.Serializable;

public class MercChannel implements Serializable{

	private static final long serialVersionUID = -1L;
	
	private Long mercAccountNo;
	private Long channelId;
	private Integer channelAttribute;
	private String channelCode;
	private String supportOperators;	//100-电信	200-联通  300-移动   400-全网通  500-第三方  600-其它
	private String supportOperatorsDes;
	
	public Long getMercAccountNo() {
		return mercAccountNo;
	}
	public Long getChannelId() {
		return channelId;
	}
	public Integer getChannelAttribute() {
		return channelAttribute;
	}
	public String getChannelCode() {
		return channelCode;
	}
	public String getSupportOperators() {
		return supportOperators;
	}
	public String getSupportOperatorsDes() {
		return supportOperatorsDes;
	}
	public void setMercAccountNo(Long mercAccountNo) {
		this.mercAccountNo = mercAccountNo;
	}
	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}
	public void setChannelAttribute(Integer channelAttribute) {
		this.channelAttribute = channelAttribute;
	}
	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}
	public void setSupportOperators(String supportOperators) {
		this.supportOperators = supportOperators;
	}
	public void setSupportOperatorsDes(String supportOperatorsDes) {
		this.supportOperatorsDes = supportOperatorsDes;
	}
}