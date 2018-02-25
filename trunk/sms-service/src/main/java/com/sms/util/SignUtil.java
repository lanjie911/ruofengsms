package com.sms.util;

public class SignUtil {

	public static boolean validateSign(String salt, String reqSign, String validateSignKey) {
		String getSign = MD5.toMD5(salt + validateSignKey);
		if (getSign.equals(reqSign))
			return true;
		return false;
	}

	public static String getSign(String salt, String signKey) {
		return MD5.toMD5(salt + signKey);
	}

	public static void main(String[] args) {
		String str = getSign("QzAbtijz", "213163");
		System.out.println(str);

	}
}
