package com.sms.util;

public class SignUtil {
	
	public static boolean validateSign(String time, String reqSign,String validateSignKey){
		String getSign = MD5.toMD5(time+validateSignKey);
		if(getSign.equals(reqSign))
			return true;
		return false;
	}
	
	public static String getSign(String time, String signKey){
		return MD5.toMD5(time + signKey);
	}
	
	public static void main(String[] args) {
		String str= getSign("d9aa70d7743a4127bde90b28f5fff26a","18612052120");
		System.out.println(str);
		
		
	}
}
