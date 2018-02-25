package com.sms.entity;

import java.io.Serializable;

public class Channel implements Serializable{

	private static final long serialVersionUID = -1L;
	
	private Long channelId;    //通道编号 
	private String channelName;    //通道名称
	private String channelCode;    //通道编码
	private Integer supportOperators;    //支持运营商 100 - 电信 200 - 联通 300 - 移动 400 - 全网通 500-第三方 600-其他
	private String supportOperatorsDes;
	private Integer channelStatus;    //通道状态100 - 使用 200 - 禁用
	private String channelStatusDes;
	private String createDatetime;    //创建时间
	private String remark;    //备注
	private Integer channelType;    //通道类型 100-短信 200-彩信 300-语音
	private String channelTypeDes;
	private Integer payMethod;    //付费方式 100-预付费 200-后付费
	private String payMethodDes;
	private Double unitPrice;    //单价
	private Integer priorityLevel;    //优先级
	private Integer billingWordsize ;    //计费字数
	private Integer supportLongsmsFlag; //是否支持长短信 100-是 -否
	private Integer componentSize;    //分量包
	private Integer flowSize;    //流量
	private Integer fromTelephone;    //下发号码
	private Integer flowSizeOneday ;    //日流量限制
	private Integer maxSendSize;    //最大并发数
	
	public String getSupportOperatorsDes() {
		return supportOperatorsDes;
	}
	public void setSupportOperatorsDes(String supportOperatorsDes) {
		this.supportOperatorsDes = supportOperatorsDes;
	}
	public String getChannelTypeDes() {
		return channelTypeDes;
	}
	public void setChannelTypeDes(String channelTypeDes) {
		this.channelTypeDes = channelTypeDes;
	}
	public String getChannelStatusDes() {
		return channelStatusDes;
	}
	public void setChannelStatusDes(String channelStatusDes) {
		this.channelStatusDes = channelStatusDes;
	}
	
	public String getPayMethodDes() {
		return payMethodDes;
	}
	public void setPayMethodDes(String payMethodDes) {
		this.payMethodDes = payMethodDes;
	}
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
	public String getChannelCode() {
		return channelCode;
	}
	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}
	public Integer getSupportOperators() {
		return supportOperators;
	}
	public void setSupportOperators(Integer supportOperators) {
		this.supportOperators = supportOperators;
	}
	public Integer getChannelStatus() {
		return channelStatus;
	}
	public void setChannelStatus(Integer channelStatus) {
		this.channelStatus = channelStatus;
	}
	public String getCreateDatetime() {
		return createDatetime;
	}
	public void setCreateDatetime(String createDatetime) {
		this.createDatetime = createDatetime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getChannelType() {
		return channelType;
	}
	public void setChannelType(Integer channelType) {
		this.channelType = channelType;
	}
	public Integer getPayMethod() {
		return payMethod;
	}
	public void setPayMethod(Integer payMethod) {
		this.payMethod = payMethod;
	}
	public Double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}
	public Integer getPriorityLevel() {
		return priorityLevel;
	}
	public void setPriorityLevel(Integer priorityLevel) {
		this.priorityLevel = priorityLevel;
	}
	public Integer getBillingWordsize() {
		return billingWordsize;
	}
	public void setBillingWordsize(Integer billingWordsize) {
		this.billingWordsize = billingWordsize;
	}
	public Integer getSupportLongsmsFlag() {
		return supportLongsmsFlag;
	}
	public void setSupportLongsmsFlag(Integer supportLongsmsFlag) {
		this.supportLongsmsFlag = supportLongsmsFlag;
	}
	public Integer getComponentSize() {
		return componentSize;
	}
	public void setComponentSize(Integer componentSize) {
		this.componentSize = componentSize;
	}
	public Integer getFlowSize() {
		return flowSize;
	}
	public void setFlowSize(Integer flowSize) {
		this.flowSize = flowSize;
	}
	public Integer getFromTelephone() {
		return fromTelephone;
	}
	public void setFromTelephone(Integer fromTelephone) {
		this.fromTelephone = fromTelephone;
	}
	public Integer getFlowSizeOneday() {
		return flowSizeOneday;
	}
	public void setFlowSizeOneday(Integer flowSizeOneday) {
		this.flowSizeOneday = flowSizeOneday;
	}
	public Integer getMaxSendSize() {
		return maxSendSize;
	}
	public void setMaxSendSize(Integer maxSendSize) {
		this.maxSendSize = maxSendSize;
	}
}