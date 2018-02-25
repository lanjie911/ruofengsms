package com.sms.criteria;

import com.sms.criteria.AbstractCriteria;

public class ChannelCriteria extends AbstractCriteria {
	
	private Long channelNo; //通道编号
	private String channelName; //通道名称
	private Integer supportOperators; //运营商类型100 - 电信  200 - 联通  300 - 移动  400 - 全网通 500-第三方  600-其他
	private Integer channelType; //通道类型
	private String createDateBegin; //创建时间开始
	private String createDateEnd; //创建时间结束
	public Long getChannelNo() {
		return channelNo;
	}
	public void setChannelNo(Long channelNo) {
		this.channelNo = channelNo;
	}
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	public Integer getSupportOperators() {
		return supportOperators;
	}
	public void setSupportOperators(Integer supportOperators) {
		this.supportOperators = supportOperators;
	}
	public Integer getChannelType() {
		return channelType;
	}
	public void setChannelType(Integer channelType) {
		this.channelType = channelType;
	}
	public String getCreateDateBegin() {
		return createDateBegin;
	}
	public void setCreateDateBegin(String createDateBegin) {
		this.createDateBegin = createDateBegin;
	}
	public String getCreateDateEnd() {
		return createDateEnd;
	}
	public void setCreateDateEnd(String createDateEnd) {
		this.createDateEnd = createDateEnd;
	}
	
	
}