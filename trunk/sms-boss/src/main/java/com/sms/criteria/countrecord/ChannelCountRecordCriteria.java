package com.sms.criteria.countrecord;

import com.sms.criteria.AbstractCriteria;

public class ChannelCountRecordCriteria extends AbstractCriteria {
	private Long channelId;                   //商户
	private String channelName;               //商户名称
	private Long mercAccountNo;              //商户账号
	private Integer channelType;            //商户账户类型
	private Integer paymentMethods;            //付费方式
	private Integer supportOperators;            //短信通道
	private String recordDateBegin;               //报表日期开始
	private String recordDateEnd;               //报表日期结束
	public Long getChannelId() {
		return channelId;
	}
	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	public Long getMercAccountNo() {
		return mercAccountNo;
	}
	public void setMercAccountNo(Long mercAccountNo) {
		this.mercAccountNo = mercAccountNo;
	}
	public Integer getChannelType() {
		return channelType;
	}
	public void setChannelType(Integer channelType) {
		this.channelType = channelType;
	}
	public Integer getPaymentMethods() {
		return paymentMethods;
	}
	public void setPaymentMethods(Integer paymentMethods) {
		this.paymentMethods = paymentMethods;
	}
	public Integer getSupportOperators() {
		return supportOperators;
	}
	public void setSupportOperators(Integer supportOperators) {
		this.supportOperators = supportOperators;
	}
	public String getRecordDateBegin() {
		return recordDateBegin;
	}
	public void setRecordDateBegin(String recordDateBegin) {
		this.recordDateBegin = recordDateBegin;
	}
	public String getRecordDateEnd() {
		return recordDateEnd;
	}
	public void setRecordDateEnd(String recordDateEnd) {
		this.recordDateEnd = recordDateEnd;
	}
	
	
}