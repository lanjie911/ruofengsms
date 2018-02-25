package com.godai.trade.common.exception;

import com.alibaba.fastjson.JSONObject;

public class TradeException extends Exception {

	private static final long serialVersionUID = 8274344649360306896L;
	private String errorCode;
	private JSONObject context;
	
	
	
	public TradeException(String errorCode, JSONObject context) {
		this.errorCode = errorCode;
		this.context = context;
	}

	public TradeException(String errorCode,String message, JSONObject context) {
		super(message);
		this.errorCode = errorCode;
		this.context = context;
	}
	
	public TradeException(String errorCode,String message) {
		super(message);
		this.errorCode = errorCode;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public JSONObject getContext() {
		return context;
	}

	public void setContext(JSONObject context) {
		this.context = context;
	}
	
}
