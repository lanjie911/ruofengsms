package com.sms.util;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AesUtil {
	
	private final static int size = 256;
	
	private static final Logger logger = LoggerFactory.getLogger(AesUtil.class);
	
	public static String encrypt(String content) {
		try {
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG", "SUN");
			secureRandom.setSeed("!~@#$qAZXSW".getBytes());
			kgen.init(size, secureRandom);
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance("AES"); 
			byte[] byteContent = content.getBytes("UTF-8");
			cipher.init(Cipher.ENCRYPT_MODE, key); 
			byte[] result = cipher.doFinal(byteContent);
			return toHex(result);  
		} catch (Exception e) {
			logger.error(e.getMessage() , e);
		}
		return null;
	}
	

	public static String decrypt(String content) {
		try {
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG", "SUN");
			secureRandom.setSeed("!~@#$qAZXSW".getBytes());
			kgen.init(size, secureRandom);
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance("AES"); 
			cipher.init(Cipher.DECRYPT_MODE, key); 
			byte[] result = cipher.doFinal(toByte(content));
			String txt = "";
			try {
				txt = new String(result , "UTF-8");
			} catch (Exception e) {
				
			}
			return txt;
		} catch (Exception e) {
			logger.error(e.getMessage() , e);
		}
		return null;
	}
	
	
	public static String toHex(byte[] buf) {
		if (buf == null)
			return "";
		StringBuffer result = new StringBuffer(2 * buf.length);
		for (int i = 0; i < buf.length; i++) {
			appendHex(result, buf[i]);
		}
		return result.toString();
	}

	private final static String HEX = "0123456789ABCDEF";

	private static void appendHex(StringBuffer sb, byte b) {
		sb.append(HEX.charAt((b >> 4) & 0x0f)).append(HEX.charAt(b & 0x0f));
	}
	
	
	public static byte[] toByte(String hexString) {
		int len = hexString.length() / 2;
		byte[] result = new byte[len];
		for (int i = 0; i < len; i++)
			result[i] = Integer.valueOf(hexString.substring(2 * i, 2 * i + 2),
					16).byteValue();
		return result;
	}
	
	public static void main(String[] args) {
		String content = "hello 北京";
		String enStr = AesUtil.encrypt(content);
		System.out.println(AesUtil.decrypt(enStr));
	}

}
