package com.sms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * 配置文件基类
 * @author robin
 * <br>创建于 2016年8月10日 下午8:36:38
 * <br>组织：北京汽车集团产业投资有限公司
 */
public class BaseConf {	
	private static Logger log = Logger.getLogger(BaseConf.class);
	
	private static Map<String,String> msgMap = new HashMap<String, String>();
	static Properties pps;
	
	static {
		InputStream inputStream = BaseConf.class.getResourceAsStream("/config.properties");
		BufferedReader bf = new BufferedReader(new InputStreamReader(inputStream));
		try { 
			pps=getInstance();
			pps.load(bf);
			Enumeration<?> enum1 = pps.propertyNames();// 得到配置文件的名字
			while (enum1.hasMoreElements()) {
				String strKey = (String) enum1.nextElement();
				String strValue = pps.getProperty(strKey);
				msgMap.put(strKey, strValue);
			}
			if(inputStream!=null){
				inputStream.close();
			}
		} catch (IOException e) {
			log.debug(e.toString());
		}
	}
	public static String getString(String propertyKey){
		if(null == propertyKey || "".equals(propertyKey))
			return "";
		return msgMap.get(propertyKey);
	}
	
	public static int getInt(String propertyKey){
		if(null == propertyKey || "".equals(propertyKey))
			return 0;
		return Integer.parseInt(msgMap.get(propertyKey));
	}
	
	public static double getDouble(String propertyKey){
		if(null == propertyKey || "".equals(propertyKey))
			return 0.0;
		return Double.parseDouble(msgMap.get(propertyKey));
	}
	
	public static Properties getInstance(){
		if(pps==null)
			pps = new Properties();
		return pps;
	}
	
	public static void main(String[] args) {
		System.out.println(BaseConf.getString("PRODUCT_NAME"));
	}
}