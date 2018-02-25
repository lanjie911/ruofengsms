package com.sms.util;

public class TradeException extends Exception {
	private static final long serialVersionUID = 1L;
	private String errorCode;
	private String errorMsg;
	
	public TradeException(){}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public TradeException(String errorCode, String errorMsg) {
		super();
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}

	@Override
	public String toString() {
		return "TradeException [errorCode=" + errorCode + ", errorMsg=" + errorMsg + "]";
	}
	
	
}
