package com.sms.entity.req;

import java.io.Serializable;

public class ResultQueryReqContent implements Serializable {
	private static final long serialVersionUID = -6025892837974028504L;
	private String reqTime;						//请求时间
	private String reqCnl;						//请求渠道
	private String reqSign;						//请求sign
	
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
	public String getMessageId() {
		return messageId;
	}
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
}
