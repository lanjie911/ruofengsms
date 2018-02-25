package com.sms.service.thirdpart.fenghuo.utils.codec;

import java.security.MessageDigest;

/**
 * Created by jerry lee on 2017/4/20.
 */
public class MD5Util {
        public static String MD5(String s , String charset , boolean isLower) {
            char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
            try {
                byte[] btInput = s.getBytes(charset);
                // 获得MD5摘要算法的 MessageDigest 对象
                MessageDigest mdInst = MessageDigest.getInstance("MD5");
                // 使用指定的字节更新摘要
                mdInst.update(btInput);
                // 获得密文
                byte[] md = mdInst.digest();
                // 把密文转换成十六进制的字符串形式
                int j = md.length;
                char str[] = new char[j * 2];
                int k = 0;
                for (int i = 0; i < j; i++) {
                    byte byte0 = md[i];
                    str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                    str[k++] = hexDigits[byte0 & 0xf];
                }
                String md5str = new String(str);
                return  isLower ? md5str.toLowerCase() : md5str ;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
    }

}
