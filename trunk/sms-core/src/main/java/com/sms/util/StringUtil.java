package com.sms.util;

import java.util.Date;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
	
public class StringUtil {
	
	public static String bankCardNoFormater(int subNum, String bankCardNo){
		if(bankCardNo.length() < subNum) return null;
		int replaceNum = bankCardNo.length() - subNum;
		Pattern p = Pattern.compile(".+(\\d{"+replaceNum+","+replaceNum+"})$");
		Matcher m = p.matcher(bankCardNo);
		m.matches();
		String replacer1 = m.group(1);
		String after_replacer1 = replacer1.replaceAll("\\d", "F");
		StringBuffer sb = new StringBuffer(bankCardNo);
		sb.replace((bankCardNo.length() - replaceNum), (bankCardNo.length()), after_replacer1);
		return sb.toString();
	}
	
	public static String maskBankAccountNo(String bankAccountNo) {
		if ((bankAccountNo == null) || (bankAccountNo.length()<10)) {
			throw new RuntimeException("Invaid bank account no.");
		}
			
		String prefix = bankAccountNo.substring(0, 6);
		String postfix = bankAccountNo.substring(bankAccountNo.length()-4);
		
		return prefix + "************" + postfix;
	}
	
	/**
	 * 获取随机串
	 * @author robin
	 * @param length 串的长度
	 * @return
	 */
	public static String getRandom(int length) {
		String val = "";
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num"; // 输出字母还是数字

			if ("char".equalsIgnoreCase(charOrNum)) // 字符串
			{
				// int choice = random.nextInt(2) % 2 == 0 ? 65 : 97;
				// //取得大写字母还是小写字母
				int choice = 97;
				val += (char) (choice + random.nextInt(26));
			} else if ("num".equalsIgnoreCase(charOrNum)) // 数字
			{
				val += String.valueOf(random.nextInt(10));
			}
		}
		return val;
	}
	
	/**
	 * 获取客户端产生的报文编号
	 * @author robin
	 * @param trnidType 交易类型
	 * @return
	 */
	public static String getTrnid(String trnidType) {
		return DatetimeUtil.getCurrentMonthDay() + trnidType + StringUtil.getRandom(5);
//		return DatetimeUtil.getCurrentMonthDay() + trnidType + StringUtil.getCode(5, 0);
	}
	
	/**
	 * 获取交易指令id
	 * @author robin
	 * @return
	 */
	public static String getInsid() {
		return DatetimeUtil.getCurrentFullTime3();
	}
	
	/**
	 * 获取des key
	 * @author robin
	 * @return
	 */
	public static String getDesKey() {
		return getCode(8,3);
	}
	
	
	private static String getCode(int passLength, int type)  
    {  
        StringBuffer buffer = null;  
        StringBuffer sb = new StringBuffer();  
        Random r = new Random();  
        r.setSeed(new Date().getTime());  
        switch (type)  
        {  
        case 0:  
            buffer = new StringBuffer("0123456789");  
            break;  
        case 1:  
            buffer = new StringBuffer("abcdefghijklmnopqrstuvwxyz");  
            break;  
        case 2:  
            buffer = new StringBuffer("ABCDEFGHIJKLMNOPQRSTUVWXYZ");  
            break;  
        case 3:  
            buffer = new StringBuffer(  
                    "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ~!@#$%^&*()");  
            break;  
        case 4:  
            buffer = new StringBuffer(  
                    "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789");  
            sb.append(buffer.charAt(r.nextInt(buffer.length() - 10)));  
            passLength -= 1;  
            break;  
        case 5:  
            String s = UUID.randomUUID().toString();  
            sb.append(s.substring(0, 8) + s.substring(9, 13)  
                    + s.substring(14, 18) + s.substring(19, 23)  
                    + s.substring(24));  
        }  
  
        if (type != 5)  
        {  
            int range = buffer.length();  
            for (int i = 0; i < passLength; ++i)  
            {  
                sb.append(buffer.charAt(r.nextInt(range)));  
            }  
        }  
        return sb.toString();  
    } 
	/**
	 * 生成指定长度的数字字符串
	 * @param pwd_len
	 * @return
	 */
	public static String genRandomNum(int pwd_len){
	    //35是因为数组是从0开始的，26个字母+10个数字
	    final int maxNum = 36;
	    int i; //生成的随机数
	    int count = 0; //生成的密码的长度
	    char[] str = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
	    StringBuffer pwd = new StringBuffer("");
	    Random r = new Random();
	    while(count < pwd_len){
	     //生成随机数，取绝对值，防止生成负数，
	     i = Math.abs(r.nextInt(maxNum)); //生成的数最大为36-1
	     if (i >= 0 && i < str.length) {
	      pwd.append(str[i]);
	      count ++;
	     }
	    }
	    return pwd.toString();
	 }
	
	
	public static void main(String[] args) {
		
		System.out.println(getCode(3,0));
//		BigDecimal bigDecimal = new BigDecimal("0.1213");
//		System.out.println(bigDecimal.toString());
		
		
		/*String bankCardNo = "6225880164931256";
		
		System.out.println(bankCardNo);
		System.out.println(bankCardNoFormater(2,bankCardNo));
		System.out.println(bankCardNoFormater(3,bankCardNo));
		System.out.println(bankCardNoFormater(4,bankCardNo));
		System.out.println(bankCardNoFormater(5,bankCardNo));
		System.out.println(bankCardNoFormater(6,bankCardNo));
		System.out.println(bankCardNoFormater(7,bankCardNo));
		System.out.println(bankCardNoFormater(8,bankCardNo));
		System.out.println(bankCardNoFormater(9,bankCardNo));
		System.out.println(bankCardNoFormater(10,bankCardNo));*/
	}

	

	
}
