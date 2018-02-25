package com.sms.entity.req;

import java.io.Serializable;

public class SendSmsReqContent implements Serializable {
	private static final long serialVersionUID = -6025892837974028504L;
	private String reqTime;						//请求时间
	private String reqCnl;						//请求渠道
	private String reqSign;						//请求sign
	
	private Long accountNo;
	private Long merchantId;					//商户编号
	private String mobile;						//手机号
	private String content;						//内容
	private Integer orderFlag;					//预约标识
	private String reservationDatetime;			//预约发送时间
	private String messageId;
	public String getReqTime() {
		return reqTime;
	}
	public void setReqTime(String reqTime) {
		this.reqTime = reqTime;
	}
	public String getReqCnl() {
		return reqCnl;
	}
	public void setReqCnl(String reqCnl) {
		this.reqCnl = reqCnl;
	}
	public String getReqSign() {
		return reqSign;
	}
	public void setReqSign(String reqSign) {
		this.reqSign = reqSign;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getReservationDatetime() {
		return reservationDatetime;
	}
	public void setReservationDatetime(String reservationDatetime) {
		this.reservationDatetime = reservationDatetime;
	}
	public Long getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}
	public Long getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(Long accountNo) {
		this.accountNo = accountNo;
	}
	public Integer getOrderFlag() {
		return orderFlag;
	}
	public void setOrderFlag(Integer orderFlag) {
		this.orderFlag = orderFlag;
	}
	public String getMessageId() {
		return messageId;
	}
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
	
}
