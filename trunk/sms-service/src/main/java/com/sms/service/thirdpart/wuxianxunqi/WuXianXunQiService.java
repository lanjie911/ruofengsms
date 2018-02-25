package com.sms.service.thirdpart.wuxianxunqi;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sms.service.PrepareParamService;
import com.sms.service.thirdpart.HttpClientUtil;

@Service
public class WuXianXunQiService {
	private Logger logger = LoggerFactory.getLogger(WuXianXunQiService.class);

	@Autowired
	private PrepareParamService prepareParamService;

	/*
	 * 发送短信
	 */
	public Map<String, Object> smsSendByXunQi(String mobiles, String signTip, String content, Integer accountType,
			Integer opertorType) {
		Map<String, Object> result = new HashMap<>();
		try {
			String uri = prepareParamService.getParam("XUNQI_REQ_URI").getParamValue();
			String userId = prepareParamService.getParam("XUNQI_USERID_" + opertorType + "_" + accountType)
					.getParamValue();
			String account = prepareParamService.getParam("XUNQI_ACCOUNT_" + opertorType + "_" + accountType)
					.getParamValue();
			String pwd = prepareParamService.getParam("XUNQI_PWD_" + opertorType + "_" + accountType).getParamValue();
			String smsContent = signTip + content;
			HttpPost httpPost = new HttpPost(uri);
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("action", "send"));
			nvps.add(new BasicNameValuePair("userid", userId));
			nvps.add(new BasicNameValuePair("account", account));
			nvps.add(new BasicNameValuePair("password", pwd));
			nvps.add(new BasicNameValuePair("content", smsContent));
			nvps.add(new BasicNameValuePair("mobile", mobiles));
			nvps.add(new BasicNameValuePair("sendTime", ""));
			nvps.add(new BasicNameValuePair("extno", "8688"));
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, Charset.forName("UTF-8")));
			logger.info("WuXianXunQiService.smsSendByXunQi-userId：{}，account：{}，Request：{}", userId, account,
					nvps.toString());
			String reponseJsonStr = HttpClientUtil.httpPost(uri, nvps);
			logger.info("WuXianXunQiService.smsSendByXunQi-userId：{}，account：{}，Reponse：{}", userId, account,
					reponseJsonStr);
			if (null == reponseJsonStr) {
				result.put("status", false);
				return result;
			}
			JSONObject jsonObj = JSON.parseObject(reponseJsonStr);
			String returnStatus = jsonObj.getString("returnstatus");
			if ("Success".equalsIgnoreCase(returnStatus)) {
				String taskID = jsonObj.getString("taskID");
				result.put("status", true);
				result.put("reqMsgId", taskID);
			} else {
				result.put("status", false);
			}
		} catch (Exception e) {
			logger.error("WuXianXunQiService.smsSendByXunQi-Exception:{}", e);
		}
		return result;
	}

}