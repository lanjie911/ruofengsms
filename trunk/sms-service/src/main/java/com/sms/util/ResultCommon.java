package com.sms.util;

public interface ResultCommon {
	
	interface ErrorCodeType {
		String SUCCESS = "0000";		//交易成功
		String ACCEPT_SUCCESS = "0001";	//受理成功
		String SYSTEM_ERROR = "9999";	//系统内部错误
		String NO_ORDER_FLAG = "1100";	//缺少请求参数：orderFlag
		String NO_MOBILE = "1101";		//缺少请求参数：mobile
		String NO_CONTENT = "1102";		//缺少请求参数：content
		String NO_MERC_ID = "1103";		//缺少请求参数：merchantId
		String NO_RESERVATION_DATETIME = "1104";	//缺少请求参数：reservationDatetime
		String NO_ACCOUNT_NO = "1105";		//缺少请求参数：accountNo
		String NO_BALANCE = "2001";		//可用余额不足
		String SMS_OFFER_QUEUE_ERROR = "2002";		//smsQueue执行异常
		String BATCH_SEND_CONTENT_ERRO = "2003";	//短信批次数量必须为1-500
		String NO_ACCOUNT = "2004";
		String NO_MESSAGE_ID = "2005";
		String NO_TEMPLATE = "2006";
		String CHECK_WORD = "2007";
	}
	
	enum ErrorCodeTypeEnum {
		SUCCESS(ErrorCodeType.SUCCESS,"成功"),
		ACCEPT_SUCCESS(ErrorCodeType.ACCEPT_SUCCESS, "受理成功"),
		NO_ORDER_FLAG(ErrorCodeType.NO_ORDER_FLAG, "缺少请求参数：orderFlag"),
		NO_RESERVATION_DATETIME(ErrorCodeType.NO_RESERVATION_DATETIME, "缺少请求参数：reservationDatetime"),
		NO_MOBILE(ErrorCodeType.NO_MOBILE, "缺少请求参数：mobile"),
		NO_CONTENT(ErrorCodeType.NO_CONTENT, "缺少请求参数：content"),
		NO_MERC_ID(ErrorCodeType.NO_MERC_ID, "缺少请求参数：merchantId"),
		NO_ACCOUNT_NO(ErrorCodeType.NO_ACCOUNT_NO, "缺少请求参数：accountNo"),
		NO_BALANCE(ErrorCodeType.NO_BALANCE, "可用余额不足"),
		SMS_OFFER_QUEUE_ERROR(ErrorCodeType.SMS_OFFER_QUEUE_ERROR, "smsQueue执行异常"),
		BATCH_SEND_CONTENT_ERRO(ErrorCodeType.BATCH_SEND_CONTENT_ERRO, "短信批次数量必须为1-500"),
		NO_ACCOUNT(ErrorCodeType.NO_ACCOUNT, "账户不存在"),
		NO_MESSAGE_ID(ErrorCodeType.NO_MESSAGE_ID, "缺少请求参数：messageId"),
		SYSTEM_ERROR(ErrorCodeType.SYSTEM_ERROR, "系统内部错误"), 
		CHECK_WORD(ErrorCodeType.CHECK_WORD, "包含敏感字符"), 
		NO_TEMPLATE(ErrorCodeType.NO_TEMPLATE, "模板未报备");
		
		private String value;
		private String text;
		ErrorCodeTypeEnum(String value, String text) {
			this.value = value;
			this.text = text;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		public String getText() {
			return text;
		}
		public void setText(String text) {
			this.text = text;
		}
	}

}