package com.sms.service.thirdpart;

import java.io.IOException;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.sms.service.PrepareParamService;
@Service
public class Net263Send {
	
	private Logger logger = LoggerFactory.getLogger(Net263Send.class);
	
	@Autowired
	private PrepareParamService prepareParamService;
	
	/**
	 * 短信发送-相同内容
	 * @return 
	 */
	public Map<String, Object> sendSms(String mobile,String content) {
		Map<String,Object> result = new HashMap<>();
		String resp = "";
		
		String account = prepareParamService.getParam("net263_name").getParamValue();
		String password = prepareParamService.getParam("net263_pwd").getParamValue();
		String prodId = prepareParamService.getParam("net263_prodid").getParamValue();
		String smsUrl = prepareParamService.getParam("net263_url").getParamValue();
		
		DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		format.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		String timestamp = format.format(new Date());

		Map<String, String> param = new HashMap<>();
		param.put("account", account);
		param.put("ts", timestamp);
//		param.put("pswd", DigestUtils.md5Hex(account + password + timestamp));
		param.put("pswd", toMD5(account + password + timestamp));
		param.put("mobile", mobile);
		param.put("msg", content);
		param.put("needstatus", "true");
		param.put("product", prodId);
		param.put("extno", "9559");
		param.put("resptype", "json");
		
		try {
			resp = WebUtils.doPost(smsUrl, param, "UTF-8", 3600, 3600);
			logger.info("响应信息===>>>>" + resp);
			JSONObject jsonObject = JSONObject.parseObject(resp);
			if(0 == jsonObject.getInteger("result")){
				result.put("status", true);
            	result.put("reqMsgId", jsonObject.getString("msgid"));
			}else{
				result.put("status", false);
			}
			// {"result":117,"ts":"20170729153542"}
			// {"result":0,"msgid":"1560731092123961300","ts":"20170731092123"}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 短信发送-不容内容
	 */
	private void sendSmsDifferenceContent() {
		DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		format.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		String timestamp = format.format(new Date());
		
		String account = prepareParamService.getParam("net263_name").getParamValue();
		String password = prepareParamService.getParam("net263_pwd").getParamValue();
		String prodId = prepareParamService.getParam("net263_prodid").getParamValue();
		
		int radom = (int)((Math.random()*9+1)*100000);
		String smsMsg = "用户您好！您的验证码是：" + radom + "【优信科技】";
		String smsMsg2 = "您的验证码是：" + radom + " 请妥善保管，切勿告诉他人！【舜鑫控股】";
		
		StringBuffer msg = new StringBuffer("18511293080|"+smsMsg).append(" 1&msg=15010477761|" + smsMsg2 + " 2");
		
		Map<String, String> param = new HashMap<>();
		param.put("account", account);
		param.put("ts", timestamp);
		param.put("pswd", toMD5(account + password + timestamp));
		param.put("msg", String.valueOf(msg));
		param.put("needstatus", "true");
		param.put("product", prodId);
		param.put("extno", "9559");
		param.put("resptype", "json");
		
		try {
			String smsUrl = "http://120.27.244.164/msg/HttpPkgSM";
			String resp = WebUtils.doPost(smsUrl, param, "UTF-8", 3600, 3600);
			System.out.println("响应信息===>>>>" + resp);
			// {"result":117,"ts":"20170729153542"}
			// {"result":0,"msgid":"1560731092123961300","ts":"20170731092123"}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 额度查询
	 */
	private void queryBalance() {
		DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		format.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		String timestamp = format.format(new Date());
		
		String account = prepareParamService.getParam("net263_name").getParamValue();
		String password = prepareParamService.getParam("net263_pwd").getParamValue();
		String prodId = prepareParamService.getParam("net263_prodid").getParamValue();
		
		Map<String, String> param = new HashMap<>();
		param.put("account", account);
		param.put("ts", timestamp);
		param.put("pswd", toMD5(account + password + timestamp));
		
		try {
			String smsUrl = "http://120.27.244.164/msg/QueryBalance";
			String resp = WebUtils.doPost(smsUrl, param, "UTF-8", 3600, 3600);
			System.out.println("响应信息===>>>>" + resp);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 查询MO短信
	 */
	private void queryMo() {
		DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		format.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		String timestamp = format.format(new Date());
		
		String account = prepareParamService.getParam("net263_name").getParamValue();
		String password = prepareParamService.getParam("net263_pwd").getParamValue();
		String prodId = prepareParamService.getParam("net263_prodid").getParamValue();
		
		Map<String, String> param = new HashMap<>();
		param.put("account", account);
		param.put("ts", timestamp);
		param.put("pswd", toMD5(account + password + timestamp));
		param.put("fromTime", "201707100000");
		param.put("toTime", "201707310000");
		
		try {
			String smsUrl = "http://120.27.244.164/msg/QueryMo";
			String resp = WebUtils.doPost(smsUrl, param, "UTF-8", 3600, 3600);
			System.out.println("响应信息===>>>>" + resp);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private String toMD5(String plainText) {
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
}