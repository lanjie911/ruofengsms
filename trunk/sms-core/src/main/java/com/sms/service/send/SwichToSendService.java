package com.sms.service.send;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sms.service.process.DealSmsQueue;
import com.sms.service.thirdpart.ChuangRuiSend;
import com.sms.service.thirdpart.JuXinSend;
import com.sms.service.thirdpart.LexinSend;
import com.sms.service.thirdpart.MeiLianSend;
import com.sms.service.thirdpart.Net263Send;
import com.sms.service.thirdpart.fenghuo.FengHuoSend;
import com.sms.service.thirdpart.jumeng.JuMengGdqmService;
import com.sms.service.thirdpart.jumeng.JuMengService;
import com.sms.service.thirdpart.jumeng.JuMengYxService;
import com.sms.service.thirdpart.tengda.TengDaService;
import com.sms.service.thirdpart.wuxianxunqi.WuXianXunQiService;

@Service
public class SwichToSendService {

	private static Logger logger = LoggerFactory.getLogger(DealSmsQueue.class);

	@Autowired
	private MeiLianSend meiLianSend;

	@Autowired
	private ChuangRuiSend chuangRuiSend;

	@Autowired
	private Net263Send net263Send;

	@Autowired
	private JuXinSend juXinSend;

	@Autowired
	private FengHuoSend fengHuoSend;

	@Autowired
	private LexinSend lexinSend;

	@Autowired
	private WuXianXunQiService wuXianXunQiService;

	@Autowired
	private TengDaService tengDaService;

	@Autowired
	private JuMengService juMengService;

	@Autowired
	private JuMengYxService juMengYxService;

	@Autowired
	private JuMengGdqmService juMengGdqmService;

	public Map<String, Object> send(String mobile, String content, String channelCode, Integer accountType) {
		Map<String, Object> result = new HashMap<>();
		if (100 == accountType) { // 行业账户
			switch (channelCode) {
			case "chuangrui":
				result = chuangRuiSend.smsSend(mobile, content);
				break;
			case "meilian":
				result = meiLianSend.sendSmsByMeiLian(mobile, content);
				break;
			case "net263":
				result = net263Send.sendSms(mobile, content);
				break;
			case "juxin":
				result = juXinSend.sendSms(mobile, content);
				break;
			case "fenghuoCMCC":
				result = fengHuoSend.smsSend(mobile, content);
				break;
			case "fenghuoUN":
				result = fengHuoSend.smsSendUN(mobile, content);
				break;
			case "fenghuoCN":
				result = fengHuoSend.smsSendCN(mobile, content);
				break;
			case "lexinCMCC":
				result = lexinSend.sendMsgCMCC(mobile, content);
				break;
			case "lexinUN":
				result = lexinSend.sendMsgUN(mobile, content);
				break;
			case "lexinCN":
				result = lexinSend.sendMsgCN(mobile, content);
				break;
			case "xunqiCMCC":
				result = wuXianXunQiService.smsSendByXunQi(mobile, content, accountType, 300);
				break;
			case "xunqiUN":
				result = wuXianXunQiService.smsSendByXunQi(mobile, content, accountType, 200);
				break;
			case "xunqiCN":
				result = wuXianXunQiService.smsSendByXunQi(mobile, content, accountType, 100);
				break;
			case "tengdaCMCC":
				result = tengDaService.smsSendByTengDa(mobile, content, accountType, 300);
				break;
			case "tengdaCN":
				result = tengDaService.smsSendByTengDa(mobile, content, accountType, 100);
				break;
			case "tengdaUN":
				result = tengDaService.smsSendByTengDa(mobile, content, accountType, 200);
				break;
			case "jumengCMCC":
				result = juMengService.doSendByCMCC(mobile, content, 0l);
				break;
			case "jumengCN":
				result = juMengService.doSendByChinaTelephone(mobile, content, 0l);
				break;
			case "jumengUN":
				result = juMengService.doSendByChinaUnicom(mobile, content, 0l);
				break;
			case "jumengzdyCMCC":
				result = juMengService.SendCustomSmsHy(mobile, content, accountType, 300);
				break;
			}
		} else {
			switch (channelCode) {
			case "meilian":
				result = meiLianSend.sendSmsByMeiLianYX(mobile, content);
				break;
			case "fenghuo":
				result = fengHuoSend.smsSendYX(mobile, content);
				break;
			case "juxinyxUN":
				result = juXinSend.sendSmsYX(mobile, content);
				break;
			case "xunqiyxCMCC":
				result = wuXianXunQiService.smsSendByXunQi(mobile, content, accountType, 300);
				break;
			case "xunqiyxUN":
				result = wuXianXunQiService.smsSendByXunQi(mobile, content, accountType, 200);
				break;
			case "xunqiyxCN":
				result = wuXianXunQiService.smsSendByXunQi(mobile, content, accountType, 100);
				break;
			case "jumengxykyxCMCC":
				result = juMengYxService.SendCreditCardSmsYx(mobile, content, accountType, 300);
				break;
			case "jumengxykyxUN":
				result = juMengYxService.SendCreditCardSmsYx(mobile, content, accountType, 200);
				break;
			case "jumengxykyxCN":
				result = juMengYxService.SendCreditCardSmsYx(mobile, content, accountType, 100);
				break;
			case "jumengwdyxUN":
				result = juMengService.SendNetLoanSmsYx(mobile, content, accountType, 200);
				break;
			case "jumenggdqmyxCMCC":
				result = juMengGdqmService.SendByJumengGdqm(mobile, content, accountType, 300);
				break;
			case "jumenggdqmyxUN":
				result = juMengGdqmService.SendByJumengGdqm(mobile, content, accountType, 200);
				break;
			case "jumenggdqmyxCN":
				result = juMengGdqmService.SendByJumengGdqm(mobile, content, accountType, 100);
				break;

			}
		}
		return result;
	}

	public static void main(String[] args) {
		String respStr = "{\"status\":100,\"count\":2,\"list\":[{\"p\":\"18511293080\",\"mid\":\"c7e8ffc209604aeb\"},{\"p\":\"18600463969\",\"mid\":\"6e1b495d5fc36dfb\"}]}";
		JSONObject requestJson = JSON.parseObject(respStr);
		System.out.println(requestJson.getString("status"));
		System.out.println(requestJson.getString("count"));
		// JSONArray list = requestJson.parseArray(requestJson.getString("list"));

		List<Map<String, String>> list = (List<Map<String, String>>) requestJson.get("list");
		System.out.println(list.get(0).get("p") + "\t" + list.get(0).get("mid"));
	}

	public Map<String, Object> batchSendSameContent(Long batchNo, Long accountNo, String mobilesData, String signTip,
			String smsContent, String channelCode, int accountType) {
		Map<String, Object> result = new HashMap<>();
		String content = signTip + smsContent;
		switch (accountType + channelCode) {
		case "100chuangrui":
			result = chuangRuiSend.smsSend(mobilesData, content);
			break;
		case "100meilian":
			result = meiLianSend.sendSmsByMeiLian(mobilesData, content);
			break;
		case "100net263":
			result = net263Send.sendSms(mobilesData, content);
			break;
		case "100juxin":
			result = juXinSend.sendSms(mobilesData, content);
			break;
		case "100fenghuoCMCC":
			result = fengHuoSend.smsSend(mobilesData, content);
			break;
		case "100fenghuoUN":
			result = fengHuoSend.smsSendUN(mobilesData, content);
			break;
		case "100fenghuoCN":
			result = fengHuoSend.smsSendCN(mobilesData, content);
			break;
		case "100lexinCMCC":
			result = lexinSend.sendMsgCMCC(mobilesData, content);
			break;
		case "100lexinUN":
			result = lexinSend.sendMsgUN(mobilesData, content);
			break;
		case "100lexinCN":
			result = lexinSend.sendMsgCN(mobilesData, content);
			break;
		case "200meilian":
			result = meiLianSend.sendSmsByMeiLianYX(mobilesData, content);
			break;
		case "200fenghuo":
			result = fengHuoSend.smsSendYX(mobilesData, content);
			break;
		case "200juxinyxUN":
			result = juXinSend.sendSmsYX_batch(mobilesData, content);
			break;
		case "200juxinyxCN":
			result = juXinSend.sendSmsCN_batch(mobilesData, content);
			break;
		case "200xunqiyxCMCC":
			result = wuXianXunQiService.smsSendByXunQi(mobilesData, content, accountType, 300);
			break;
		case "200xunqiyxUN":
			result = wuXianXunQiService.smsSendByXunQi(mobilesData, content, accountType, 200);
			break;
		case "200xunqiyxCN":
			result = wuXianXunQiService.smsSendByXunQi(mobilesData, content, accountType, 100);
			break;
		case "200jumengxykyxCMCC":
			result = juMengYxService.SendCreditCardSmsYx(mobilesData, content, accountType, 300);
			break;
		case "200jumengxykyxUN":
			result = juMengYxService.SendCreditCardSmsYx(mobilesData, content, accountType, 200);
			break;
		case "200jumengxykyxCN":
			result = juMengYxService.SendCreditCardSmsYx(mobilesData, content, accountType, 100);
			break;
		case "200jumengwdyxUN":
			result = juMengService.SendNetLoanSmsYx(mobilesData, content, accountType, 200);
			break;
		case "200jumenggdqmyxCMCC":
			result = juMengGdqmService.SendByJumengGdqm(mobilesData, content, accountType, 300);
			break;
		case "200jumenggdqmyxUN":
			result = juMengGdqmService.SendByJumengGdqm(mobilesData, content, accountType, 200);
			break;
		case "200jumenggdqmyxCN":
			result = juMengGdqmService.SendByJumengGdqm(mobilesData, content, accountType, 100);
			break;

		}
		return result;
	}
}