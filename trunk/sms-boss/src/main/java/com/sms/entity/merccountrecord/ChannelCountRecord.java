package com.sms.entity.merccountrecord;

import java.io.Serializable;

public class ChannelCountRecord implements Serializable{

	private static final long serialVersionUID = -8404937874518642392L;
	private Long channelId ;  //通道编号
	private String channelName;               //通道名称
	private Double unitPrice;  //单价
	private Integer channelType ;  //通道类型
	private String channelTypeDes ;  //通道类型描述
	private Integer paymentMethods;   //付费方式
	private String paymentMethodsDes;   //付费方式描述
	private Integer supportOperators;   //运营商
	private String supportOperatorsDes;  //运营商描述
	private Integer flowSum;  //流量
	private String time;  //流量
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
	public Double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}
	public Integer getChannelType() {
		return channelType;
	}
	public void setChannelType(Integer channelType) {
		this.channelType = channelType;
	}
	public String getChannelTypeDes() {
		return channelTypeDes;
	}
	public void setChannelTypeDes(String channelTypeDes) {
		this.channelTypeDes = channelTypeDes;
	}
	public Integer getPaymentMethods() {
		return paymentMethods;
	}
	public void setPaymentMethods(Integer paymentMethods) {
		this.paymentMethods = paymentMethods;
	}
	public String getPaymentMethodsDes() {
		return paymentMethodsDes;
	}
	public void setPaymentMethodsDes(String paymentMethodsDes) {
		this.paymentMethodsDes = paymentMethodsDes;
	}
	public Integer getSupportOperators() {
		return supportOperators;
	}
	public void setSupportOperators(Integer supportOperators) {
		this.supportOperators = supportOperators;
	}
	public String getSupportOperatorsDes() {
		return supportOperatorsDes;
	}
	public void setSupportOperatorsDes(String supportOperatorsDes) {
		this.supportOperatorsDes = supportOperatorsDes;
	}
	public Integer getFlowSum() {
		return flowSum;
	}
	public void setFlowSum(Integer flowSum) {
		this.flowSum = flowSum;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
}
