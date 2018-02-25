package com.sms.service.send;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sms.service.process.DealSmsQueue;
import com.sms.service.thirdpart.ChuangRuiSend;
import com.sms.service.thirdpart.JuXinSend;
import com.sms.service.thirdpart.LexinSend;
import com.sms.service.thirdpart.MeiLianSend;
import com.sms.service.thirdpart.Net263Send;
import com.sms.service.thirdpart.fenghuo.FengHuoSend;
import com.sms.service.thirdpart.jumei.JuMengGdqmService;
import com.sms.service.thirdpart.jumei.JuMengService;
import com.sms.service.thirdpart.jumei.JuMengYxService;
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
	private JuMengService juMengService;

	@Autowired
	private WuXianXunQiService wuXianXunQiService;

	@Autowired
	private TengDaService tengDaService;
	@Autowired
	private JuMengYxService juMengYxService;

	@Autowired
	private JuMengGdqmService juMengGdqmService;

	public Map<String, Object> send(String signTip, String mobile, String content, String channelCode,
			Integer accountType, Integer operatorType) {
		logger.info("SwichToSendService.send-accountType：{}，channelCode：{}", accountType, channelCode);
		Map<String, Object> result = new HashMap<>();
		if (100 == accountType) { // 行业账户
			switch (channelCode) {
			case "chuangrui":
				result = chuangRuiSend.smsSend(mobile, signTip + content);
				break;
			case "meilian":
				result = meiLianSend.sendSmsByMeiLian(mobile, signTip + content);
				break;
			case "meilianCMCC":
				result = meiLianSend.sendSms(accountType, operatorType, mobile, signTip, content);
				break;
			case "meilianUN":
				result = meiLianSend.sendSms(accountType, operatorType, mobile, signTip, content);
				break;
			case "meilianCN":
				result = meiLianSend.sendSms(accountType, operatorType, mobile, signTip, content);
				break;
			case "net263":
				result = net263Send.sendSms(mobile, signTip + content);
				break;
			case "juxin":
				result = juXinSend.sendSms(mobile, signTip + content);
				break;
			case "juxinCMCC":
				result = juXinSend.sendSms(accountType, "CMCC", mobile, signTip + content);
				break;
			case "juxinUN":
				result = juXinSend.sendSms(accountType, "CHINAUNION", mobile, signTip + content);
				break;
			case "juxinCN":
				result = juXinSend.sendSms(accountType, "CHINATELECOM", mobile, signTip + content);
				break;
			case "fenghuoCMCC":
				result = fengHuoSend.smsSend(mobile, signTip + content);
				break;
			case "fenghuoUN":
				result = fengHuoSend.smsSendUN(mobile, signTip + content);
				break;
			case "fenghuoCN":
				result = fengHuoSend.smsSendCN(mobile, signTip + content);
				break;
			case "lexinCMCC":
				result = lexinSend.sendMsgCMCC(mobile, signTip + content);
				break;
			case "lexinUN":
				result = lexinSend.sendMsgUN(mobile, signTip + content);
				break;
			case "lexinCN":
				result = lexinSend.sendMsgCN(mobile, signTip + content);
				break;
			case "jumengCN":
				result = juMengService.doSendByChinaTelephone(mobile, signTip + content, 0l);
				break;
			case "jumengCMCC":
				result = juMengService.doSendByCMCC(mobile, signTip + content, 0l);
				break;
			case "jumengUN":
				result = juMengService.doSendByChinaUnicom(mobile, signTip + content, 0l);
				break;
			case "jumengzdyCMCC":
				result = juMengService.SendCustomSmsHy(mobile, signTip, content, accountType, 300);
				break;
			case "xunqiCMCC":
				result = wuXianXunQiService.smsSendByXunQi(mobile, signTip, content, accountType, 300);
				break;
			case "xunqiUN":
				result = wuXianXunQiService.smsSendByXunQi(mobile, signTip, content, accountType, 200);
				break;
			case "xunqiCN":
				result = wuXianXunQiService.smsSendByXunQi(mobile, signTip, content, accountType, 100);
				break;
			case "tengdaCMCC":
				result = tengDaService.smsSendByTengDa(mobile, signTip, content, accountType, 300);
				break;
			case "tengdaUN":
				result = tengDaService.smsSendByTengDa(mobile, signTip, content, accountType, 200);
				break;
			case "tengdaCN":
				result = tengDaService.smsSendByTengDa(mobile, signTip, content, accountType, 100);
				break;

			}
		} else if (200 == accountType) { // 营销账户
			switch (channelCode) {
			case "meilian":
				result = meiLianSend.sendSmsByMeiLianYX(mobile, signTip + content);
				break;
			case "fenghuo":
				result = fengHuoSend.smsSendYX(mobile, signTip + content);
				break;
			case "juxinyxUN":
				result = juXinSend.sendSmsYX_batch(mobile, signTip + content);
				break;
			case "juxinyxCN":
				result = juXinSend.sendSmsCN_batch(mobile, signTip + content);
				break;
			case "chuangrui":
				result = chuangRuiSend.smsSend(accountType, 400, mobile, signTip, content);
				break;
			case "xunqiyxCMCC":
				result = wuXianXunQiService.smsSendByXunQi(mobile, signTip, content, accountType, 300);
				break;
			case "xunqiyxUN":
				result = wuXianXunQiService.smsSendByXunQi(mobile, signTip, content, accountType, 200);
				break;
			case "xunqiyxCN":
				result = wuXianXunQiService.smsSendByXunQi(mobile, signTip, content, accountType, 100);
				break;
			case "jumengxykyxCMCC":
				result = juMengYxService.SendCreditCardSmsYx(mobile, signTip, content, accountType, 300);
				break;
			case "jumengxykyxUN":
				result = juMengYxService.SendCreditCardSmsYx(mobile, signTip, content, accountType, 200);
				break;
			case "jumengxykyxCN":
				result = juMengYxService.SendCreditCardSmsYx(mobile, signTip, content, accountType, 100);
				break;
			case "jumengwdyxUN":
				result = juMengService.SendNetLoanSmsYx(mobile, signTip, content, accountType, 200);
				break;
			case "jumenggdqmyxCMCC":
				result = juMengGdqmService.SendByJumengGdqm(mobile, signTip, content, accountType, 300);
				break;
			case "jumenggdqmyxUN":
				result = juMengGdqmService.SendByJumengGdqm(mobile, signTip, content, accountType, 200);
				break;
			case "jumenggdqmyxCN":
				result = juMengGdqmService.SendByJumengGdqm(mobile, signTip, content, accountType, 100);
				break;

			}
		} else if (300 == accountType) {
			switch (channelCode) {
			case "juxinCMCC":
				result = juXinSend.sendSms(accountType, "CMCC", mobile, content);
				break;
			case "juxinUN":
				result = juXinSend.sendSms(accountType, "CHINAUNION", mobile, content);
				break;
			case "juxinCN":
				result = juXinSend.sendSms(accountType, "CHINATELECOM", mobile, content);
				break;
			case "meilianUN":
				result = meiLianSend.sendSms(accountType, operatorType, mobile, signTip, content);
				break;
			case "meilianCN":
				result = meiLianSend.sendSms(accountType, operatorType, mobile, signTip, content);
				break;
			case "meilianCMCC":
				result = meiLianSend.sendSms(accountType, operatorType, mobile, signTip, content);
				break;
			}
		} else if (400 == accountType) {
			switch (channelCode) {
			case "meilianUN":
				result = meiLianSend.sendSms(accountType, operatorType, mobile, signTip, content);
				break;
			case "meilianCMCC":
				result = meiLianSend.sendSms(accountType, operatorType, mobile, signTip, content);
				break;
			}
		} else if (500 == accountType) {
			switch (channelCode) {
			case "meilian":
				result = meiLianSend.sendSms(accountType, 400, mobile, signTip, content);
				break;
			}
		}
		return result;
	}
}