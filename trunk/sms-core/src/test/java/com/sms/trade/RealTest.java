package com.sms.trade;

import java.io.IOException;
import java.net.URLDecoder;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;

public class RealTest {
	
	private static Logger logger = Logger.getLogger(RealTest.class);
	
	
	public static void main(String[] args) {
		sendSms();
//		sendMsgDiff();
//		sendMsgMarket();
//		result();
//		balance();
//		receive();
	}
	
	//发送实时，预约短信（支持批量）
	public static void sendSms(){
		try {
	        String url ="http://api.juhedx.com/sms/trade";
	        JSONObject jsObject = new JSONObject();
	        String password = "Ry8p8PYc";
	        String sign = "736044";
	        jsObject.put("accountNo", 10000016);		//行业账户号
	        jsObject.put("mobile", "18511293080");		//手机号
//	        jsObject.put("content", "您的临时额度即将失效，30000元现金等您来，请尽快使用。点击http://t.cn/RK8iVzo 回TD退定");
	        jsObject.put("content", "验证码是12333");
	        jsObject.put("messageId", "201711151455092");	//批次号不可重复
	        jsObject.put("orderFlag", "100");		//100-实时发送	200-预约发送
	        jsObject.put("reservationDatetime", "2017-10-15 15:42:00");	//预约发送时必填
	        jsObject.put("password", password);		//密码
	        jsObject.put("sign", sign);				//密钥
	        jsObject.put("signTip", "【掌e贷】");		//短信签名
	        String jsonResult = httpPost(url, jsObject, false);
			System.out.println(jsonResult);
		} catch (Exception e) {
			logger.error("HttpUtil Error",e);
		}
	}
	
	//个性短信（支持批量）
	public static void sendMsgDiff(){
		try {
	        String url ="http://api.juhedx.com/sms/tradediff";
	        JSONObject jsObject = new JSONObject();
	        String password = "1234qwer";
	        String sign = "830359";
	        jsObject.put("accountNo", 10000003);		//行业账户号
	        jsObject.put("content", "13838443333#@#您好，您的验证码是123456.#@@#15010470000#@#您好，您的验证码是654321.");
	        jsObject.put("messageId", "20170827150602112");	//批次号不可重复
	        jsObject.put("orderFlag", "100");		//100-实时发送	200-预约发送
	        jsObject.put("reservationDatetime", "2017-09-27 15:50:22");	//预约发送时必填
	        jsObject.put("password", password);		//密码
	        jsObject.put("sign", sign);				//密钥
	        jsObject.put("signTip", "【圆芯网】");		//短信签名
	        String jsonResult = httpPost(url, jsObject, false);
			System.out.println(jsonResult);
		} catch (Exception e) {
			logger.error("HttpUtil Error",e);
		}
	}
	
	
	//结果查询
	public static void result(){
		try {
	        String url ="http://api.juhedx.com/query/result";
	        JSONObject jsObject = new JSONObject();
	        String password = "1234qwer";
	        String sign = "830359";
	        jsObject.put("accountNo", 10000003);		//账户号
	        jsObject.put("orderNo", "20170827150602");	//上送的messegeId
	        jsObject.put("password", password);		//密码
	        jsObject.put("sign", sign);				//密钥
	        String jsonResult = httpPost(url, jsObject, false);
			System.out.println(jsonResult);
		} catch (Exception e) {
			logger.error("HttpUtil Error",e);
		}
	}
	
	//余额查询
	public static void balance(){
		try {
	        String url ="http://api.juhedx.com/query/balance";
	        JSONObject jsObject = new JSONObject();
	        String password = "1234qwer";
	        String sign = "830359";
	        jsObject.put("accountNo", 10000003);		//账户号
	        jsObject.put("password", password);		//密码
	        jsObject.put("sign", sign);		//密钥
	        String jsonResult = httpPost(url, jsObject, false);
			System.out.println(jsonResult);
		} catch (Exception e) {
			logger.error("HttpUtil Error",e);
		}
	}
	
	//退订与收件箱
	public static void receive(){
		try {
	        String url ="http://api.juhedx.com/callback/receive";
	        JSONObject jsObject = new JSONObject();
	        String password = "1234qwer";
	        String sign = "830359";
	        jsObject.put("accountNo", 10000003);		//账户号
	        jsObject.put("mobile", "18511293080");		//手机
	        jsObject.put("content", "TD");		//TD为退订，其他内容进入收件箱待处理
	        jsObject.put("password", password);		//密码
	        jsObject.put("sign", sign);		//密钥
	        String jsonResult = httpPost(url, jsObject, false);
			System.out.println(jsonResult);
		} catch (Exception e) {
			logger.error("HttpUtil Error",e);
		}
	}
	
 	public static String httpPost(String url,JSONObject jsonParam, boolean noNeedResponse){
        //post请求返回结果
        DefaultHttpClient httpClient = new DefaultHttpClient();
        JSONObject jsonResult = null;
        HttpPost method = new HttpPost(url);
        String str = "";
        try {
            if (null != jsonParam) {
                //中文乱码问题
                StringEntity entity = new StringEntity(jsonParam.toString(), "utf-8");
                entity.setContentEncoding("UTF-8");
                entity.setContentType("application/json");
                method.setEntity(entity);
            }
            HttpResponse result = httpClient.execute(method);
            url = URLDecoder.decode(url, "UTF-8");
           //**请求发送成功，并得到响应**//*
            if (result.getStatusLine().getStatusCode() == 200) {
                try {
                    str = EntityUtils.toString(result.getEntity());
                } catch (Exception e) {
                    logger.error("post请求提交失败:" + url, e);
                }
            }
        } catch (IOException e) {
            logger.error("post请求提交失败:" + url, e);
        }
        return str;
    }

}
