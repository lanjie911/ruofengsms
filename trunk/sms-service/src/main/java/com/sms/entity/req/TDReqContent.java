package com.sms.entity.req;

import java.io.Serializable;

public class TDReqContent implements Serializable {
	private static final long serialVersionUID = -6025892837974028504L;
	private String reqTime;						//请求时间
	private String reqCnl;						//请求渠道
	private String reqSign;						//请求sign
	
	private String mobile;
	private String content;						//内容
	
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
}
