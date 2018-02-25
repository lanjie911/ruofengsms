package com.sms.service.thirdpart.jumeng;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sms.dto.jumeng.Returnsms;
import com.sms.service.PrepareParamService;
import com.sms.service.thirdpart.HttpClientUtil;

@Service
public class JuMengYxService {
	private Logger logger = LoggerFactory.getLogger(JuMengService.class);

	@Autowired
	private PrepareParamService prepareParamService;

	// 营销短信接口
	public Map<String, Object> SendCreditCardSmsYx(String mobiles, String content, Integer accountType,
			Integer opertorType) {
		Map<String, Object> result = new HashMap<>();
		try {
			String uri = prepareParamService.getParam("JUMENGYX_REQ_URI").getParamValue();
			String ac = prepareParamService.getParam("JUMENGYX_XYK_AC_" + opertorType + "_" + accountType)
					.getParamValue();
			String spid = prepareParamService.getParam("JUMENGYX_XYK_SPID_" + opertorType + "_" + accountType)
					.getParamValue();
			String pwd = prepareParamService.getParam("JUMENGYX_XYK_PWD_" + opertorType + "_" + accountType)
					.getParamValue();
			HttpPost httpPost = new HttpPost(uri);
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("spid", spid));
			nvps.add(new BasicNameValuePair("password", pwd));
			nvps.add(new BasicNameValuePair("ac", ac));
			nvps.add(new BasicNameValuePair("content", content));
			nvps.add(new BasicNameValuePair("mobiles", mobiles));
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, Charset.forName("UTF-8")));
			logger.info("JuMengYxService.SendCreditCardSmsYx-SPID：{}，AC：{}，Request：{}", spid, ac, nvps.toString());
			String responseXml = HttpClientUtil.httpPost(uri, nvps);
			logger.info("JuMengYxService.SendCreditCardSmsYx-SPID：{}，AC：{}，Reponse：{}", spid, ac, responseXml);
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
			logger.error("JuMengYxService.SendCreditCardSmsYx-Exception:{}", e);
		}
		return result;
	}

	public static void main(String[] args) {
		sendByJumengTest();
	}

	public static void sendByJumengTest() {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		try {
			int randNum = 1 + (int) (Math.random() * ((999999 - 1) + 1));
			// 行业
			// HttpPost("http://58.253.87.82:8718/smsgwhttp/sms/submit");
			// 金融营销
			HttpPost httpPost = new HttpPost("http://58.252.3.163:8357/smsgwhttp/sms/submit");

			String content = "【聚合信息】您的注册验证码为：" + randNum;
			// String content = "【交通银行】恭喜您成为交通银行特邀客户，额度高，审批快，天天享优惠，免年费申请！点击
			// http://t.cn/RQXZjUk 回T退订 ";
			// 联通号码
			String mobiles = "13271230890";
			// 移动号码
			// String mobiles = "13651056688";
			// 电信号码
			// String mobiles = "13363640109,19931868309";

			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			// 移动的
			// nvps.add(new BasicNameValuePair("ac", "10692345123499"));
			// nvps.add(new BasicNameValuePair("spid", "688099"));
			// nvps.add(new BasicNameValuePair("password", "123sfdhg"));
			// 联通的
			// nvps.add(new BasicNameValuePair("ac", "1069130603055"));
			// nvps.add(new BasicNameValuePair("spid", "401075"));
			// nvps.add(new BasicNameValuePair("password", "123wrwetr"));
			// 电信的
			// nvps.add(new BasicNameValuePair("ac", "1069130603531"));
			// nvps.add(new BasicNameValuePair("spid", "502031"));
			// nvps.add(new BasicNameValuePair("password", "123wetery"));
			// 移动行业自定义

			// nvps.add(newBasicNameValuePair("ac", "489011"));
			// nvps.add(new BasicNameValuePair("spid", "80011"));
			// nvps.add(newBasicNameValuePair("password", "DASfds"));

			// 联通金融网贷
			nvps.add(new BasicNameValuePair("ac", "1069130603652"));
			nvps.add(new BasicNameValuePair("spid", "402072"));
			nvps.add(new BasicNameValuePair("password", "123werey"));

			nvps.add(new BasicNameValuePair("content", content));
			nvps.add(new BasicNameValuePair("mobiles", mobiles));
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, Charset.forName("UTF-8")));
			response = httpclient.execute(httpPost);

			System.out.println("HTTP响应码：" + response.getStatusLine());

			HttpEntity entity = response.getEntity();
			InputStream instreams = entity.getContent();
			/*
			 * String jsonStr = null;
			 * 
			 * ByteArrayOutputStream baos = new ByteArrayOutputStream(); int i=-1; while((i
			 * = instreams.read())!=-1){ baos.write(i); } jsonStr =
			 * URLDecoder.decode(baos.toString(), "UTF-8");
			 */

			BufferedReader reader = new BufferedReader(new InputStreamReader(instreams));
			StringBuilder respStr = new StringBuilder();
			String jsonStr = null;
			while ((jsonStr = reader.readLine()) != null)
				respStr.append(jsonStr);
			reader.close(); // 关闭输入流
			jsonStr = URLDecoder.decode(respStr.toString(), "UTF-8");
			System.out.println("响应信息：" + jsonStr);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				response.close();
				httpclient.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

}