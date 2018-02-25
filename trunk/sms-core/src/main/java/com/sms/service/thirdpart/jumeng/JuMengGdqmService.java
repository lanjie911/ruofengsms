package com.sms.service.thirdpart.jumeng;

import java.io.StringReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sms.dto.jumeng.Returnsms;
import com.sms.service.PrepareParamService;
import com.sms.service.thirdpart.HttpClientUtil;

@Service
public class JuMengGdqmService {
	private Logger logger = LoggerFactory.getLogger(JuMengService.class);

	@Autowired
	private PrepareParamService prepareParamService;

	// 营销短信接口
	public Map<String, Object> SendByJumengGdqm(String mobiles, String content, Integer accountType,
			Integer opertorType) {
		Map<String, Object> result = new HashMap<>();
		try {
			String uri = prepareParamService.getParam("JUMENGYX_GDQM_URI").getParamValue();
			String ac = prepareParamService.getParam("JUMENGYX_GDQM_AC_" + opertorType + "_" + accountType)
					.getParamValue();
			String spid = prepareParamService.getParam("JUMENGYX_GDQM_SPID_" + opertorType + "_" + accountType)
					.getParamValue();
			String pwd = prepareParamService.getParam("JUMENGYX_GDQM_PWD_" + opertorType + "_" + accountType)
					.getParamValue();
			HttpPost httpPost = new HttpPost(uri);
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("spid", spid));
			nvps.add(new BasicNameValuePair("password", pwd));
			nvps.add(new BasicNameValuePair("ac", ac));
			nvps.add(new BasicNameValuePair("content", content));
			nvps.add(new BasicNameValuePair("mobiles", mobiles));
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, Charset.forName("UTF-8")));
			logger.info("JuMengGdqmService.SendSmsGdqm-SPID：{}，AC：{}，Request：{}", spid, ac, nvps.toString());
			String responseXml = HttpClientUtil.httpPost(uri, nvps);
			logger.info("JuMengGdqmService.SendSmsGdqm-SPID：{}，AC：{}，Reponse：{}", spid, ac, responseXml);
			if (null == responseXml) {
				result.put("status", false);
				return result;
			}
			JAXBContext context = JAXBContext.newInstance(Returnsms.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			Returnsms returnsms = (Returnsms) unmarshaller.unmarshal(new StringReader(responseXml));
			if (returnsms.getResult() == 0) {
				result.put("status", true);
				result.put("reqMsgId", returnsms.getTaskid());
			} else {
				result.put("status", false);
			}
		} catch (Exception e) {
			logger.error("JuMengGdqmService.SendSmsGdqm-Exception:{}", e);
		}
		return result;
	}

}