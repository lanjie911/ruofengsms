package com.sms.service.thirdpart.maiyuan;

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

import com.sms.dto.maiyuan.MaiYuantDto;
import com.sms.service.PrepareParamService;
import com.sms.service.thirdpart.HttpClientUtil;

@Service
public class MaiYuanService {
	private  Logger logger = LoggerFactory.getLogger(MaiYuanService.class);

	@Autowired
	private PrepareParamService prepareParamService;
/*
 * 	发送短信
*/
	public Map<String, Object> smsSendByMaiYuan(String mobiles,String content, Integer accountType,
			Integer opertorType) {
		Map<String, Object> result = new HashMap<>();
		try {
			String url = prepareParamService.getParam("MAIYUAN_REQ_URI").getParamValue();
			String userid = prepareParamService.getParam("MAIYUAN_USERID").getParamValue();
			String account = prepareParamService.getParam("MAIYUAN_ACCOUNT").getParamValue();
			String pwd = prepareParamService.getParam("MAIYUAN_PWD").getParamValue();
			String sendData ="action=send"+"&userid="+userid+"&account="+account+"&password="+pwd+"&mobile="+mobiles+"&content="+content;
			logger.info("MAIYUANService.smsSendByMAIYUAN-account：{}，account：{}，Request：{}",  account,sendData);
			String reponseJsonStr = HttpClientUtil.sendPostRequest(url, sendData, true, "UTF-8", "UTF-8");
			logger.info("MAIYUANService.smsSendByMAIYUAN-account：{}，account：{}，Reponse：{}",  account,reponseJsonStr);
			if (null == reponseJsonStr) {
				result.put("status", false);
				return result;
			}
			JAXBContext ctx = JAXBContext.newInstance(MaiYuantDto.class);
			Unmarshaller marchaller = ctx.createUnmarshaller();
			MaiYuantDto dto = (MaiYuantDto) marchaller.unmarshal(new StringReader(reponseJsonStr));
			
			String returnstatus = dto.getReturnstatus();
			if ("Success".equalsIgnoreCase(returnstatus)) {
				String taskID = dto.getTaskID();
				result.put("status", true);
				result.put("reqMsgId", taskID);
			} else {
				result.put("status", false);
			}
		} catch (Exception e) {
			logger.error("MAIYUANService.smsSendByMAIYUAN-Exception:{}", e);
		}
		return result;
	}
	
	
	
	public static void main(String[] args) {
		String url ="http://39.107.117.142:8088/sms.aspx";
		String userid = "11";
		String account = "ruofeng";
		String password = "asdf1234";
		String mobile = "13271230890,13651056688,19931868309";
		String content = "【达飞云贷】新春巨献，火速申请“活动A款”产品体验>> https://t.dafy.com/NBvqY3.do 机不可失！ 退订回N";
		String sendData ="action=send"+"&userid="+userid+"&account="+account+"&password="+password+"&mobile="+mobile+"&content="+content;
		String sendPostRequest = HttpClientUtil.sendPostRequest(url, sendData, true, "UTF-8", "UTF-8");
		System.out.println(sendPostRequest);
	}

}