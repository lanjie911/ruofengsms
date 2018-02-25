package com.sms.trade;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;

public class RealTest {

	private static Logger logger = Logger.getLogger(RealTest.class);

	public static void main(String[] args) {
		sendSms();
		// sendMsgDiff();
		// sendMsgMarket();
		// result();
		// balance();
		// receive();
		// reload();
	}

	// 发送实时，预约短信（支持批量）
	public static void sendSms() {
		try {
			int randNum = 1 + (int) (Math.random() * ((999999 - 1) + 1));
			String url = "http://api.juhedx.com/sms/trade";
			// String url = "http://sms.juhedx.com/sms/trade";
			// String url = "http://127.0.0.1:8080/ROOT/sms/trade";
			JSONObject jsObject = new JSONObject();
			// String password = "PPnTRDa4";
			// String sign = "611595";
			String password = "QzAbtijz";// 营销账户号
			String sign = "213163";

			// jsObject.put("accountNo", 10000001); // 行业账户号
			jsObject.put("accountNo", 10000008); // 营销账户号
			// jsObject.put("mobile", "13046096806");
			jsObject.put("mobile", "13651056688,13271230890,13363640109");
			// jsObject.put("content",
			// "恭喜您成为交通银行特邀客户，额度高，审批快，天天享优惠，免年费申请！点击http://t.cn/RQXZjUk 回T退订");
			// jsObject.put("content", "登录验证码为" + randNum + "。有效时间为10分钟，打死也不要告诉别人哦！");// 聚信
			jsObject.put("content",
					"尊敬的用户，您好！恭喜您通过达飞云贷授信额度审批，如有用款或分期购买需求，立即申请>> https://t.dafy.com/NBvqY3.do 客服热线400-625-9898");// 聚梦
			jsObject.put("messageId", "20180123091122"); // 批次号不可重复
			jsObject.put("orderFlag", "100"); // 100-实时发送 200-预约发送
			jsObject.put("reservationDatetime", ""); // 预约发送时必传
			jsObject.put("password", password);
			jsObject.put("sign", sign); // 加密言
			jsObject.put("signTip", "【达飞云贷】"); // 短信签名
			String jsonResult = httpPost2(url, jsObject.toJSONString());

			System.out.println(jsonResult);
		} catch (Exception e) {
			logger.error("HttpUtil Error", e);
		}
	}

	// 个性短信（支持批量）
	public static void sendMsgDiff() {
		try {
			String url = "http://127.0.0.1:10037/smsservice/sms/tradediff";
			JSONObject jsObject = new JSONObject();
			String password = "1234qwer";
			String sign = "830359";
			jsObject.put("accountNo", 10000010); // 行业账户号
			jsObject.put("content",
					"15011477777#@#验证码为123098，提示您保管好自己的验证码，请勿泄露!#@@#13838812345#@#验证码为123098，提示您保管好自己的验证码，请勿泄露!");
			jsObject.put("messageId", "20170827150602"); // 批次号不可重复
			jsObject.put("orderFlag", "100"); // 100-实时发送 200-预约发送
			jsObject.put("reservationDatetime", "2017-09-27 15:50:22"); // 预约发送时必传
			jsObject.put("password", password); // 加密盐
			jsObject.put("sign", sign); // 验签
			jsObject.put("signTip", "【若峰伟业】"); // 短信签名
			String jsonResult = httpPost(url, jsObject, false);
			System.out.println(jsonResult);
		} catch (Exception e) {
			logger.error("HttpUtil Error", e);
		}
	}

	// 营销短信
	public static void sendMsgMarket() {
		try {
			// String url = "http://127.0.0.1:9140/smsservice/sms/trademarket";
			String url = "http://sms.juhedx.com/sms/trademarket";
			JSONObject jsObject = new JSONObject();
			String password = "1234qwer";
			String sign = "830359";
			jsObject.put("accountNo", 10000004); // 营销账户号
			jsObject.put("mobile", "13271230890");
			jsObject.put("content", "123");
			jsObject.put("messageId", "20170827150602"); // 批次号不可重复
			jsObject.put("orderFlag", "100"); // 100-实时发送 200-预约发送
			jsObject.put("reservationDatetime", ""); // 预约发送时必传
			jsObject.put("password", password); // 加密盐
			jsObject.put("sign", sign); // 验签
			jsObject.put("signTip", "【若峰伟业】"); // 短信签名
			String jsonResult = httpPost(url, jsObject, false);
			System.out.println(jsonResult);
		} catch (Exception e) {
			logger.error("HttpUtil Error", e);
		}
	}

	// 结果查询
	public static void result() {
		try {
			String url = "http://127.0.0.1:9140/smsservice/query/result";
			JSONObject jsObject = new JSONObject();
			String password = "1234qwer";
			String sign = "830359";
			jsObject.put("accountNo", 10000004); // 账户号
			jsObject.put("orderNo", "20170827150602112"); // 订单号
			jsObject.put("password", password); // 加密盐
			jsObject.put("sign", sign); // 验签
			String jsonResult = httpPost(url, jsObject, false);
			System.out.println(jsonResult);
		} catch (Exception e) {
			logger.error("HttpUtil Error", e);
		}
	}

	// 余额查询
	public static void balance() {
		try {
			String url = "http://127.0.0.1:9140/smsservice/query/balance";
			JSONObject jsObject = new JSONObject();
			String password = "1234qwer";
			String sign = "830359";
			jsObject.put("accountNo", 10000004); // 账户号
			jsObject.put("password", password); // 加密盐
			jsObject.put("sign", sign); // 验签
			String jsonResult = httpPost(url, jsObject, false);
			System.out.println(jsonResult);
		} catch (Exception e) {
			logger.error("HttpUtil Error", e);
		}
	}

	// 退订与收件箱
	public static void receive() {
		try {
			String url = "http://127.0.0.1:9140/smsservice/callback/receive";
			JSONObject jsObject = new JSONObject();
			String password = "1234qwer";
			String sign = "830359";
			jsObject.put("accountNo", 10000004); // 账户号
			jsObject.put("mobile", "15010477761"); // 手机
			jsObject.put("content", "TD"); // TD为退订，其他内容进入收件箱待处理
			jsObject.put("password", password); // 加密盐
			jsObject.put("sign", sign); // 验签
			String jsonResult = httpPost(url, jsObject, false);
			System.out.println(jsonResult);
		} catch (Exception e) {
			logger.error("HttpUtil Error", e);
		}
	}

	public static void reload() {
		try {
			String url = "http://127.0.0.1:9140/smsservice/query/reload";
			JSONObject jsObject = new JSONObject();
			jsObject.put("transCode", "500"); // 账户号
			jsObject.put("password", "boss"); // 加密盐
			jsObject.put("mobile", ""); // 账户号
			jsObject.put("type", ""); // 账户号
			String jsonResult = httpPost(url, jsObject, false);
			System.out.println(jsonResult);
		} catch (Exception e) {
			logger.error("HttpUtil Error", e);
		}
	}

	public static String httpPost2(String uri, String reqJson) {
		String reponseStr = null;
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			HttpPost httpPost = new HttpPost(uri);
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			httpPost.setEntity(new StringEntity(reqJson, Charset.forName("UTF-8")));
			CloseableHttpResponse response2 = httpclient.execute(httpPost);

			try {
				System.out.println(response2.getStatusLine());
				HttpEntity entity2 = response2.getEntity();
				// do something useful with the response body
				// and ensure it is fully consumed
				// EntityUtils.consume(entity2);

				InputStream instreams = entity2.getContent();
				BufferedReader reader = new BufferedReader(new InputStreamReader(instreams));
				StringBuilder respStr = new StringBuilder();
				String jsonStr = null;
				while ((jsonStr = reader.readLine()) != null)
					respStr.append(jsonStr);
				reader.close();// 关闭输入流
				reponseStr = URLDecoder.decode(respStr.toString(), "UTF-8");
			} finally {
				response2.close();
			}
		} finally {
			try {
				httpclient.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return reponseStr;
		}
	}

	public static String httpPost(String url, JSONObject jsonParam, boolean noNeedResponse) {
		// post请求返回结果
		DefaultHttpClient httpClient = new DefaultHttpClient();
		JSONObject jsonResult = null;
		HttpPost method = new HttpPost(url);
		String str = "";
		try {
			if (null != jsonParam) {
				// 解决中文乱码问题
				StringEntity entity = new StringEntity(jsonParam.toString(), "utf-8");
				entity.setContentEncoding("UTF-8");
				entity.setContentType("application/json");
				method.setEntity(entity);
			}
			HttpResponse result = httpClient.execute(method);
			url = URLDecoder.decode(url, "UTF-8");
			// **请求发送成功，并得到响应**//*
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