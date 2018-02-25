package com.sms.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {

	/**
	 * 将字符串装换为MD5
	 * @param str
	 * @return
	 */
	public static String toMD5(String str) {
		String md5Str = null;
		if (str != null && str.length() != 0) {
			try {
				MessageDigest md = MessageDigest.getInstance("MD5");
				md.update(str.getBytes());
				byte b[] = md.digest();
				int i;
				StringBuffer buf = new StringBuffer("");
				for (int offset = 0; offset < b.length; offset++) {
					i = b[offset];
					if (i < 0)
						i += 256;
					if (i < 16)
						buf.append("0");
					buf.append(Integer.toHexString(i));
				}
				// 32位
				md5Str = buf.toString();
				// 16位
				// md5Str = buf.toString().substring(8, 24);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} 
		}
		return md5Str;
	}
	
	public static void main(String[] args) {
		String str = "123456";
		String md5 = MD5.toMD5(str);
		System.out.println(md5);// 32位的加密usertest001 -->
		
		// System.out.println("result: " + md5.substring(8, 24));//
		// 16位的加密usertest001 -->1e551421158f2bdf
	}
}
