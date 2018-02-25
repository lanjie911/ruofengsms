package com.sms.service.thirdpart;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.sms.service.PrepareParamService;

@Service
public class JuXinSend {
	
	private static Logger logger = LoggerFactory.getLogger(JuXinSend.class);

	@Autowired
	private PrepareParamService prepareParamService;
	
	public Map<String, Object> sendSms(String mobile,String content){
		Map<String,Object> result = new HashMap<>();
		try {
			String encode = prepareParamService.getParam("juxin_encode").getParamValue();
			String username = prepareParamService.getParam("juxin_name").getParamValue();
			String password_md5 = toMD5(prepareParamService.getParam("juxin_pwd").getParamValue());
			String uri = prepareParamService.getParam("juxin_uri").getParamValue();
			
			Map<String, String> params = new HashMap<String, String>();
			params.put("username", username);
			params.put("pwd", password_md5);
			params.put("extnum", "8688");
			params.put("p", mobile);
			params.put("isUrlEncode", "no");
			params.put("charSetStr", "utf");
			params.put("msg", content);
			logger.info("聚信通道发送内容：" + params.toString());
			String postStr = HttpClientUtil.sendPostRequestByJava(uri, params);
			logger.info("聚信通道发送结果：" + postStr);
			JSONObject jsonObject = JSONObject.parseObject(postStr.substring(0, postStr.length()-4));
			String status = jsonObject.getString("status");
			List<Map<String,String>> list = (List<Map<String, String>>) jsonObject.get("list");
			if("100".equals(status)){
				result.put("status", true);
            	result.put("reqMsgId", (String)list.get(0).get("mid"));
			}else{
				result.put("status", false);
			}
		} catch (Exception e) {
			logger.error("聚信通道发送异常:" + e);
		}
		return result;
	}
	
	public Map<String, Object> sendSmsYX(String mobile, String content) {
		Map<String,Object> result = new HashMap<>();
		try {
			String encode = prepareParamService.getParam("juxin_encode").getParamValue();
			String username = prepareParamService.getParam("juxin_yx_name").getParamValue();
			String password_md5 = toMD5(prepareParamService.getParam("juxin_yx_pwd").getParamValue());
			String uri = prepareParamService.getParam("juxin_uri").getParamValue();
			
			Map<String, String> params = new HashMap<String, String>();
			params.put("username", username);
			params.put("pwd", password_md5);
			params.put("extnum", "8688");
			params.put("p", mobile);
			params.put("isUrlEncode", "no");
			params.put("charSetStr", "utf");
			params.put("msg", content);
			logger.info("聚信账号:{},通道发送内容：{}", username, params.toString());
			String postStr = HttpClientUtil.sendPostRequestByJava(uri, params);
			logger.info("聚信账号：{},通道发送结果：{}", username, postStr);
			JSONObject jsonObject = JSONObject.parseObject(postStr.substring(0, postStr.length()-4));
			String status = jsonObject.getString("status");
			List<Map<String,String>> list = (List<Map<String, String>>) jsonObject.get("list");
			if("100".equals(status)){
				result.put("status", true);
            	result.put("reqMsgId", (String)list.get(0).get("mid"));
			}else{
				result.put("status", false);
			}
		} catch (Exception e) {
			logger.error("聚信通道发送异常:" + e);
		}
		return result;
	}
	
	public Map<String, Object> sendSmsYX_batch(String mobile, String content) {
		Map<String,Object> result = new HashMap<>();
		try {
			String encode = prepareParamService.getParam("juxin_encode").getParamValue();
			String username = prepareParamService.getParam("juxin_yx_name").getParamValue();
			String password_md5 = toMD5(prepareParamService.getParam("juxin_yx_pwd").getParamValue());
			String uri = prepareParamService.getParam("juxin_uri").getParamValue();
			
			if(mobile.contains(","))
				mobile = mobile.substring(0, mobile.lastIndexOf(","));
			
			List <NameValuePair> nvps = new ArrayList <NameValuePair>();
	        nvps.add(new BasicNameValuePair("username", username));
	        nvps.add(new BasicNameValuePair("pwd", password_md5));
	        nvps.add(new BasicNameValuePair("extnum", "8688"));
	        nvps.add(new BasicNameValuePair("p", mobile));
	        nvps.add(new BasicNameValuePair("isUrlEncode", "no"));
	        nvps.add(new BasicNameValuePair("charSetStr", "utf"));
	        nvps.add(new BasicNameValuePair("msg", content));
	        logger.info("聚信账号:{},通道发送内容:{}", username, nvps.toString());
			String postStr = HttpClientUtil.httpPost(uri, nvps);
			logger.info("聚信账号:{},通道发送结果:{}", username, postStr);
			if(null == postStr){
				result.put("status", false);
				return result;
			}
			JSONObject jsonObject = JSONObject.parseObject(postStr);
			String status = jsonObject.getString("status");
			if("100".equals(status)){
				result.put("status", true);
				List<Map<String,String>> list = (List<Map<String, String>>) jsonObject.get("list");
				result.put("respObj", list);
			}else{
				result.put("status", false);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("聚信通道发送异常:" + e);
		}
		return result;
	}
	
	public Map<String, Object> sendSmsCN_batch(String mobile, String content) {
		Map<String,Object> result = new HashMap<>();
		try {
			String encode = prepareParamService.getParam("juxin_encode").getParamValue();
			String username = prepareParamService.getParam("JUXIN_USR_CT_200").getParamValue();
			String password_md5 = prepareParamService.getParam("JUXIN_PWD_MD5_CT_200").getParamValue();
			String uri = prepareParamService.getParam("juxin_uri").getParamValue();
			
			if(mobile.contains(","))
				mobile = mobile.substring(0, mobile.lastIndexOf(","));
			
			List <NameValuePair> nvps = new ArrayList <NameValuePair>();
			nvps.add(new BasicNameValuePair("username", username));
			nvps.add(new BasicNameValuePair("pwd", password_md5));
			nvps.add(new BasicNameValuePair("extnum", "8688"));
			nvps.add(new BasicNameValuePair("p", mobile));
			nvps.add(new BasicNameValuePair("isUrlEncode", "no"));
			nvps.add(new BasicNameValuePair("charSetStr", "utf"));
			nvps.add(new BasicNameValuePair("msg", content));
			logger.info("聚信账号:{},通道发送内容:{}", username, nvps.toString());
			String postStr = HttpClientUtil.httpPost(uri, nvps);
			logger.info("聚信账号:{},通道发送结果:{}", username, postStr);
			if(null == postStr){
				result.put("status", false);
				return result;
			}
			JSONObject jsonObject = JSONObject.parseObject(postStr);
			String status = jsonObject.getString("status");
			if("100".equals(status)){
				result.put("status", true);
				List<Map<String,String>> list = (List<Map<String, String>>) jsonObject.get("list");
				result.put("respObj", list);
			}else{
				result.put("status", false);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("聚信通道发送异常:" + e);
		}
		return result;
	}
	
	private static String toMD5(String plainText) {
		String entryStr = null;
		try {
			// 生成实现指定摘要算法的 MessageDigest 对象。
			MessageDigest md = MessageDigest.getInstance("MD5");
			// 使用指定的字节数组更新摘要。
			md.update(plainText.getBytes());
			// 通过执行诸如填充之类的最终操作完成哈希计算。
			byte b[] = md.digest();
			// 生成具体的md5密码到buf数组
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
			entryStr = buf.toString();
			// System.out.println("32位: " + buf.toString());// 32位的加密
			// System.out.println("16位: " + buf.toString().substring(8, 24));//
			// 16位的加密，其实就是32位加密后的截取
		} catch (Exception e) {
			e.printStackTrace();
		}
		return entryStr;
	}
	
	public static void main(String[] args) {
		
		StringBuffer str = new StringBuffer();
		for(int i=0; i<1000;i++){
			str.append("18511293080").append(",");
		}
		
		String mobiles = str.toString();
		
		String username = "bjrfwy";
		String password_md5 = toMD5("q6sTolHU");
		String uri = "http://api.app2e.com/smsBigSend.api.php";
//		String uri = "http://localhost:8119/sms-core/TestServlet";	
		String content = "【测试短信】短信测试，请勿发，谢谢。11月18日13:29时";
		if(mobiles.contains(","))
			mobiles = mobiles.substring(0, mobiles.lastIndexOf(","));
		Map<String,Object> result = new HashMap<>();
		List <NameValuePair> nvps = new ArrayList <NameValuePair>();
        nvps.add(new BasicNameValuePair("username", username));
        nvps.add(new BasicNameValuePair("pwd", password_md5));
        nvps.add(new BasicNameValuePair("extnum", "8688"));
        nvps.add(new BasicNameValuePair("p", mobiles));
        nvps.add(new BasicNameValuePair("isUrlEncode", "no"));
        nvps.add(new BasicNameValuePair("charSetStr", "utf"));
        nvps.add(new BasicNameValuePair("msg", content));
        logger.info("聚信账号:{},通道发送内容:{}", username, nvps.toString());
		String postStr = HttpClientUtil.httpPost(uri, nvps);
		logger.info("聚信账号:{},通道发送结果:{}", username, postStr);
		JSONObject jsonObject = JSONObject.parseObject(postStr);
		String status = jsonObject.getString("status");
		
		if("100".equals(status)){
			result.put("status", true);
			List<Map<String,String>> list = (List<Map<String, String>>) jsonObject.get("list");
			for(Map<String, String> mm : list){
				System.out.println(mm.get("p") + "\t" + mm.get("mid"));
			}
			result.put("respObj", list);
		}else{
			result.put("status", false);
		}
	}
}