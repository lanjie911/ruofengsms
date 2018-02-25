package com.sms.util;

import java.util.HashMap;
import java.util.Map;

public class PhoneShipUtil {
	public static final String REGEX_MOBILE = "^((17[0-9])|(14[0-9])|(13[0-9])|(15[0-9])|(16[0-9])|(18[0-9])|(19[0-9]))\\d{8}$";
	public static Map<String, String> mobileOperator = new HashMap<String, String>();
	static {
		mobileOperator.put("130", "200");		
		mobileOperator.put("131", "200");
		mobileOperator.put("132", "200");
		mobileOperator.put("133", "100");
		mobileOperator.put("134", "300");
		mobileOperator.put("135", "300");
		mobileOperator.put("136", "300");
		mobileOperator.put("137", "300");
		mobileOperator.put("138", "300");
		mobileOperator.put("139", "300");
		mobileOperator.put("145", "200");
		mobileOperator.put("147", "300");
		mobileOperator.put("149", "100");
		mobileOperator.put("150", "300");
		mobileOperator.put("151", "300");
		mobileOperator.put("152", "300");
		mobileOperator.put("153", "100");
		mobileOperator.put("155", "200");
		mobileOperator.put("156", "200");
		mobileOperator.put("157", "300");
		mobileOperator.put("158", "300");
		mobileOperator.put("159", "300");
		mobileOperator.put("171", "200");
		mobileOperator.put("172", "200");
		mobileOperator.put("173", "100");
		mobileOperator.put("175", "200");
		mobileOperator.put("176", "200");
		mobileOperator.put("177", "100");
		mobileOperator.put("178", "300");
		mobileOperator.put("180", "100");
		mobileOperator.put("181", "100");
		mobileOperator.put("182", "300");
		mobileOperator.put("183", "300");
		mobileOperator.put("184", "300");
		mobileOperator.put("185", "200");
		mobileOperator.put("186", "200");
		mobileOperator.put("187", "300");
		mobileOperator.put("188", "300");
		mobileOperator.put("189", "100");
		mobileOperator.put("199", "100");
	}
	
	public static String query170OperatorMobile(String mobileStr) {
		String mobileOperator = "";
		int mobilePrefix = Integer.valueOf(mobileStr.substring(0,7));
		if(mobilePrefix >= 1700000 && mobilePrefix <= 1702999) mobileOperator =  "100";
		if(mobilePrefix >= 1703000 && mobilePrefix <= 1703999) mobileOperator =  "300";
		if(mobilePrefix >= 1704000 && mobilePrefix <= 1704999) mobileOperator =  "200";
		if(mobilePrefix >= 1705000 && mobilePrefix <= 1706999) mobileOperator =  "300";
		if(mobilePrefix >= 1707000 && mobilePrefix <= 1709999) mobileOperator =  "200";
		return mobileOperator;
	}
}
