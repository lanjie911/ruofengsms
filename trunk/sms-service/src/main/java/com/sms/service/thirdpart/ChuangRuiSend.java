package com.sms.service.thirdpart;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
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
public class ChuangRuiSend {
	
	private static Logger logger = LoggerFactory.getLogger(ChuangRuiSend.class);
	
	@Autowired
	private PrepareParamService prepareParamService;
	
	/**
	 * 单个短信发送
	 * @return 
	 */
	public Map<String, Object> smsSend(String mobile,String content) {
		Map<String,Object> result = new HashMap<>();
		String responseResult = "";
		String name = prepareParamService.getParam("chuangrui_name").getParamValue();
		String pwd = prepareParamService.getParam("chuangrui_pwd").getParamValue();
		// 电话号码字符串，中间用英文逗号间隔
		StringBuffer mobileString = new StringBuffer(mobile);
		// 内容字符串
		StringBuffer contextString = new StringBuffer(content);
		
//		String sign="【诺信科技】";
		// 追加发送时间，可为空，为空为及时发送
		// 扩展码，必须为数字 可为空
		StringBuffer extno = new StringBuffer("9888");
		
		try {
			StringBuffer param = new StringBuffer();
			param.append("name=" + name);
			param.append("&pwd=" + pwd);
			param.append("&mobile=").append(mobileString);
			param.append("&content=").append(URLEncoder.encode(contextString.toString(), "UTF-8"));
//			param.append("&stime=" + stime);
//			param.append("&sign=").append(URLEncoder.encode(sign, "UTF-8"));
			param.append("&type=pt");
			param.append("&extno=").append(extno);
			
			responseResult = doPost(param.toString());
			logger.info("响应信息====>>>>" + responseResult);
			String[] results = responseResult.split(",");
			if("0".equals(results[0])){
				result.put("status", true);
            	result.put("reqMsgId", results[1]);
			}else{
				result.put("status", false);
			}
			//0,2017072913462768337385045,0,1,0,提交成功
			//0,2017072913472747856486349,0,2,0,提交成功
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 创瑞3.0接口发送
	 * @param accountType 商户账号类型
	 * @param useOperator 运营商类型
	 * @param mobile 手机号数组
	 * @param sign 短信签名
	 * @param content 短信内容
	 * @return 发送结果
	 */
	public Map<String, Object> smsSend(Integer accountType, int useOperator, String mobile, String signTip, String content) {
		Map<String,Object> result = new HashMap<>();
		try {
			String accessKey = prepareParamService.getParam("ChunagRui_AccessKey_400").getParamValue();
			String accessSecret = prepareParamService.getParam("ChunagRui_AccessSecret_400").getParamValue();
			String uri = prepareParamService.getParam("ChunagRui_Uri_400").getParamValue();
			List <NameValuePair> nvps = new ArrayList <NameValuePair>();
			nvps.add(new BasicNameValuePair("accesskey", accessKey));
			nvps.add(new BasicNameValuePair("secret", accessSecret));
			nvps.add(new BasicNameValuePair("sign", signTip));
			nvps.add(new BasicNameValuePair("templateId", ""));
			nvps.add(new BasicNameValuePair("mobile", mobile));
			nvps.add(new BasicNameValuePair("content", content));
			nvps.add(new BasicNameValuePair("data", ""));
			nvps.add(new BasicNameValuePair("scheduleSendTime", ""));
			
			logger.info("创瑞3.0账号:{},通道发送内容:{}", accessKey, nvps.toString());
			String postStr = HttpClientUtil.httpPost(uri, nvps);
			logger.info("创瑞3.0账号:{},通道发送结果:{}", accessKey, postStr);
			
			if(null == postStr || "".equals(postStr)) {
				result.put("status", false);
				return result;
			}
			
			JSONObject jsonObject = JSONObject.parseObject(postStr);
			String code = jsonObject.getString("code");
			if("0".equals(code)){
				result.put("status", true);
				result.put("reqMsgId", jsonObject.getString("batchId"));
			}else{
				result.put("status", false);
			}
		} catch (Exception e) {
			logger.error("创瑞3.0通道发送异常：{}", e);
			result.put("status", false);
		}
		return result;
	}
	
	/**
	 * 个性短信发送
	 */
	private void sendSmsPersonality() {
		try {
			// 电话号码字符串，中间用英文逗号间隔
			StringBuffer contextString = new StringBuffer("你好张三#@#18511293080#@@#");
			contextString.append("你好李四#@#15010477761#@@#");
			String name = prepareParamService.getParam("chuangrui_name").getParamValue();
			String pwd = prepareParamService.getParam("chuangrui_pwd").getParamValue();
			String sign="【中鑫网络】";
			StringBuffer param = new StringBuffer();
			param.append("name=" + name);
			param.append("&pwd=" + pwd);
			param.append("&content=").append(URLEncoder.encode(contextString.toString(), "UTF-8"));
			param.append("&stime=");
			param.append("&sign=").append(URLEncoder.encode(sign, "UTF-8"));
			param.append("&type=gx");
			param.append("&extno=9559");
			
			String responseResult = doPost(param.toString());
			System.out.println("响应信息====>>>>" + responseResult);
			//0,2017072913575640997632997,0,2,0,提交成功
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 余额查询
	 */
	private void queryBalance() {
		try {
			String name = prepareParamService.getParam("chuangrui_name").getParamValue();
			String pwd = prepareParamService.getParam("chuangrui_pwd").getParamValue();
			StringBuffer param = new StringBuffer();
			param.append("name=" + name);
			param.append("&pwd=" + pwd);
			param.append("&type=balance");
			
			String responseResult = doPost(param.toString());
			System.out.println("当前余额：" + responseResult);
			// 0,93
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 发送短信
	 * 
	 * @param name 用户名
	 * @param pwd 密码
	 * @param mobileString 电话号码字符串，中间用英文逗号间隔
	 * @param contextString 内容字符串
	 * @param sign 签名
	 * @param stime 追加发送时间，可为空，为空为及时发送
	 * @param extno 扩展码，必须为数字 可为空
	 * @return
	 * @throws Exception
	 */
	public String doPost(String params) throws Exception {
		
		URL localURL = new URL(prepareParamService.getParam("chuangrui_url").getParamValue()+"?");
		URLConnection connection = localURL.openConnection();
		HttpURLConnection httpURLConnection = (HttpURLConnection) connection;

		httpURLConnection.setDoOutput(true);
		httpURLConnection.setRequestMethod("POST");
		httpURLConnection.setRequestProperty("Accept-Charset", "utf-8");
		httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		httpURLConnection.setRequestProperty("Content-Length", String.valueOf(params.length()));

		OutputStream outputStream = null;
		OutputStreamWriter outputStreamWriter = null;
		InputStream inputStream = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader reader = null;
		String resultBuffer = "";

		try {
			outputStream = httpURLConnection.getOutputStream();
			outputStreamWriter = new OutputStreamWriter(outputStream);
			System.out.println("请求信息====>>>>" + params);
			outputStreamWriter.write(params);
			outputStreamWriter.flush();

			if (httpURLConnection.getResponseCode() >= 300) {
				throw new Exception("HTTP Request is not success, Response code is " + httpURLConnection.getResponseCode());
			}

			inputStream = httpURLConnection.getInputStream();
			resultBuffer = convertStreamToString(inputStream);

		} finally {

			if (outputStreamWriter != null) {
				outputStreamWriter.close();
			}

			if (outputStream != null) {
				outputStream.close();
			}

			if (reader != null) {
				reader.close();
			}

			if (inputStreamReader != null) {
				inputStreamReader.close();
			}

			if (inputStream != null) {
				inputStream.close();
			}
		}

		return resultBuffer;
	}

	/**
	 * 转换返回值类型为UTF-8格式.
	 * @param is
	 * @return
	 */
	public static String convertStreamToString(InputStream is) {
		StringBuilder sb1 = new StringBuilder();
		byte[] bytes = new byte[4096];
		int size = 0;

		try {
			while ((size = is.read(bytes)) > 0) {
				String str = new String(bytes, 0, size, "UTF-8");
				sb1.append(str);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb1.toString();
	}
}