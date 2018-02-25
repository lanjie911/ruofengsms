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
	
	public Map<String, Object> sendSms(String mobile, String content) {
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
	
	public Map<String, Object> sendSms(Integer accountType, String useOperator, String mobile, String content){
		Map<String,Object> result = new HashMap<>();
		try {
			String username = prepareParamService.getParam("juxin_name").getParamValue();
			String password_md5 = toMD5(prepareParamService.getParam("juxin_pwd").getParamValue());
			String uri = prepareParamService.getParam("juxin_uri").getParamValue();
			if("CMCC".equals(useOperator)){
				username = prepareParamService.getParam("JUXIN_USR_CMCC_"+accountType).getParamValue();
				password_md5 = prepareParamService.getParam("JUXIN_PWD_MD5_CMCC_"+accountType).getParamValue();
			}else if("CHINAUNION".equals(useOperator)){
				username = prepareParamService.getParam("JUXIN_USR_CN_"+accountType).getParamValue();
				password_md5 = prepareParamService.getParam("JUXIN_PWD_MD5_CN_"+accountType).getParamValue();
			}else if("CHINATELECOM".equals(useOperator)){
				username = prepareParamService.getParam("JUXIN_USR_CT_"+accountType).getParamValue();
				password_md5 = prepareParamService.getParam("JUXIN_PWD_MD5_CT_"+accountType).getParamValue();
			}
			
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
			if(null == postStr || "".equals(postStr)) {
				result.put("status", false);
				return result;
			}
			JSONObject jsonObject = JSONObject.parseObject(postStr);
			String status = jsonObject.getString("status");
			if("100".equals(status)){
				result.put("status", true);
				List<Map<String,String>> list = (List<Map<String, String>>) jsonObject.get("list");
				result.put("reqMsgId", (String)list.get(0).get("mid"));
			}else{
				result.put("status", false);
			}
		} catch (Exception e) {
			logger.error("聚信通道发送异常:" + e);
			result.put("status", false);
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
				result.put("reqMsgId", (String)list.get(0).get("mid"));
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
				result.put("reqMsgId", (String)list.get(0).get("mid"));
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
		try {
			String username = "zqslthy";
			String password_md5 = "e2ff2c4422c396405f1d6ac2ff429a5f";
			String uri = "http://api.app2e.com/smsBigSend.api.php";
			
			int randNum = 1 + (int) (Math.random() * ((999999 - 1) + 1));
			List <NameValuePair> nvps = new ArrayList <NameValuePair>();
			nvps.add(new BasicNameValuePair("username", username));
			nvps.add(new BasicNameValuePair("pwd", password_md5));
			nvps.add(new BasicNameValuePair("extnum", "8688"));
			nvps.add(new BasicNameValuePair("p", "18511293080"));
			nvps.add(new BasicNameValuePair("isUrlEncode", "no"));
			nvps.add(new BasicNameValuePair("charSetStr", "utf"));
			nvps.add(new BasicNameValuePair("msg", "【闪电借款】您的注册验证码为：" + randNum));
			logger.info("聚信账号:{},通道发送内容:{}", username, nvps.toString());
			String postStr = HttpClientUtil.httpPost(uri, nvps);
			logger.info("聚信账号:{},通道发送结果:{}", username, postStr);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("聚信通道发送异常:" + e);
		}
	}
}
