package com.sms.service.thirdpart.tengda;

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

import com.sms.dto.TengDaReportDto;
import com.sms.service.PrepareParamService;
import com.sms.service.thirdpart.HttpClientUtil;

@Service
public class TengDaService {
	private  Logger logger = LoggerFactory.getLogger(TengDaService.class);

	@Autowired
	private PrepareParamService prepareParamService;
/*
 * 	发送短信
*/
	public Map<String, Object> smsSendByTengDa(String mobiles, String content, Integer accountType,
			Integer opertorType) {
		Map<String, Object> result = new HashMap<>();
		try {
			String uri = prepareParamService.getParam("TENGDA_REQ_URI").getParamValue();
			String account = prepareParamService.getParam("TENGDA_ACCOUNT_" + opertorType + "_" + accountType).getParamValue();
			String pwd = prepareParamService.getParam("TENGDA_PWD_" + opertorType + "_" + accountType).getParamValue();
			String extno = prepareParamService.getParam("TENGDA_EXTNO_" + opertorType + "_" + accountType).getParamValue();
			HttpPost httpPost = new HttpPost(uri);
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("action", "send"));
			nvps.add(new BasicNameValuePair("account", account));
			nvps.add(new BasicNameValuePair("password", pwd));
			nvps.add(new BasicNameValuePair("extno", extno));
			nvps.add(new BasicNameValuePair("content", content));
			nvps.add(new BasicNameValuePair("mobile", mobiles));
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, Charset.forName("UTF-8")));
			logger.info("TengDaService.smsSendByTengDa-account：{}，account：{}，Request：{}",  account,nvps.toString());
			String reponseJsonStr = HttpClientUtil.httpPost(uri, nvps);
			logger.info("TengDaService.smsSendByTengDa-account：{}，account：{}，Reponse：{}",  account,reponseJsonStr);
			if (null == reponseJsonStr) {
				result.put("status", false);
				return result;
			}
			JAXBContext ctx = JAXBContext.newInstance(TengDaReportDto.class);
			Unmarshaller marchaller = ctx.createUnmarshaller();
			TengDaReportDto dto = (TengDaReportDto) marchaller.unmarshal(new StringReader(reponseJsonStr));
			
			String returnstatus = dto.getReturnstatus();
			if ("Success".equalsIgnoreCase(returnstatus)) {
				String taskID = dto.getTaskID();
				result.put("status", true);
				result.put("reqMsgId", taskID);
			} else {
				result.put("status", false);
			}
		} catch (Exception e) {
			logger.error("TengDaService.smsSendByTengDa-Exception:{}", e);
		}
		return result;
	}



	/*
	 * 解析xml
	 */
	
	public  void dealReportXml(String reportXml) {
		try {
			JAXBContext ctx = JAXBContext.newInstance(TengDaReportDto.class);
			Unmarshaller marchaller = ctx.createUnmarshaller();
			TengDaReportDto dto = (TengDaReportDto) marchaller.unmarshal(new StringReader(reportXml));
			String returnstatus = dto.getReturnstatus();
			String message = dto.getMessage();
			String remainpoint = dto.getRemainpoint();
			String taskID = dto.getTaskID();
			String successCounts = dto.getSuccessCounts();
			String[] resplist = dto.getResplist();
			 if(resplist.length==0 )
		        	return; 
			for (String resp : resplist) {
				System.out.println("taskID:" + taskID + "\tresp:" + resp + "\treturnstatus:" + returnstatus + "\tmessage:"
					+ message + "\tremainpoint:" + remainpoint+"\tsuccessCounts:"+successCounts);
			}
			
		} catch (Exception e) {
			logger.error("TengDaService.dealReportXml-Exception:{}", e);
		}
	}
}