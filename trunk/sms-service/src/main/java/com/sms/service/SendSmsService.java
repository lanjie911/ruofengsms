package com.sms.service;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.sms.dao.primarydao.SmsInboxDao;
import com.sms.entity.MercAccount;
import com.sms.entity.PlainSendRecord;
import com.sms.entity.SmsInbox;
import com.sms.entity.Unsubscribe;
import com.sms.service.send.AuditingService;
import com.sms.service.send.ChannelService;
import com.sms.service.send.MercAccountService;
import com.sms.service.send.ReservationSendRecordService;
import com.sms.service.send.ThreadService;
import com.sms.service.send.UnsubscribeService;
import com.sms.service.smsupload.SmsApplayService;
import com.sms.util.DatetimeUtil;
import com.sms.util.ResultCommon;
import com.sms.util.TradeException;
/**
 * @author Cao
 * 短信发送
 */
@Service
public class SendSmsService {

	private static Logger logger = LoggerFactory.getLogger(SendSmsService.class);
	
	@Autowired
	private PrepareParamService prepareParamService;
	
	@Autowired
	private MercAccountService mercAccountService;
	
	@Autowired
	private AuditingService auditingService;
	
	@Autowired
	private ChannelService channelService;
	
	@Autowired
	private UnsubscribeService unsubscribeService;
	
	@Autowired
	private SendToCheckService sendToCheckService;
	
	@Autowired
	private SmsInboxDao smsInboxDao;
	
	@Autowired
	private SmsApplayService smsApplayService;
	
	@Autowired
	private ThreadService threadService;
	
	@Autowired
	private ReservationSendRecordService reservationSendRecordService;
	
	@Value("${sms.batch.size}")
	private Integer batchSize;
	
	public void sendMsg(JSONObject jsonObject) throws TradeException {
		logger.info("jsonObject.jsonObject :{}", jsonObject.toJSONString());
		Long accountNo = jsonObject.getLongValue("accountNo");
		String messageId = jsonObject.getString("messageId");
		String content = jsonObject.getString("content");
		String signTip = jsonObject.getString("signTip");
		Integer orderFlag = jsonObject.getInteger("orderFlag");
		String reservationDatetime = jsonObject.getString("reservationDatetime");
		String mobile = jsonObject.getString("mobile");
		String[] mobiles = mobile.split(",");
		String mercReqTime = DatetimeUtil.getCurrentDateTime("yyyy-MM-dd HH:mm:ss");
		Integer sendNum = 1;
		if(content.length()+signTip.length() > 70)
			sendNum = (int) (Math.ceil(content.length()/(68d-signTip.length())));
		mercAccountService.frozenBalance(accountNo, sendNum, mobiles, messageId);			//冻结资金异步生成流水
		MercAccount mercAccount = prepareParamService.getMercAccount(accountNo);
		
		Integer costQuanteNum = 0;															//扣量
		if(0 < mercAccount.getCostQuantity() && mobiles.length > 1)
			costQuanteNum = costQuante(mercAccount, mobiles, sendNum, mercAccount.getCostQuantity());
		String[] mobileStrs = Arrays.copyOfRange(mobiles, costQuanteNum, mobiles.length);
		
		/*if(100 == mercAccount.getSenseWordFlag() && 100 == mercAccount.getTemplateMatchFlag()){
			sendToCheckService.doAll(accountNo,content);			//模板+敏感
		}else if(100 == mercAccount.getSenseWordFlag() && 200 == mercAccount.getTemplateMatchFlag()){
			sendToCheckService.doSensitive(accountNo,content);		//敏感
		}else{
			sendToCheckService.doTemplate(accountNo,content);		//模板
		}*/
		if(100 == mercAccount.getSendAuditFlag()){					//若需审核，插入审核表
			auditingService.insertToAudit(orderFlag,reservationDatetime,messageId,content,mercAccount, sendNum, mobileStrs,signTip);
			throw new TradeException("0000","{\"msg\":\"SUCCESS\",\"code\":\"0000\",\"messageId\":\""+messageId+"\"}");
		}
		if(200 == orderFlag){										//预约短信
			reservationSendRecordService.insertReservationMsg(content, messageId, reservationDatetime, mercAccount, mobileStrs,signTip);
			throw new TradeException("0000","{\"msg\":\"SUCCESS\",\"code\":\"0000\",\"messageId\":\""+messageId+"\"}");
		}
		channelService.switchChannelToSend(null, content, messageId, orderFlag, mercAccount, sendNum, mobileStrs, signTip, mercReqTime);
	}

	public void sendMsgDiff(JSONObject jsonObject) throws TradeException{
		Long accountNo = jsonObject.getLongValue("accountNo");
		String messageId = jsonObject.getString("messageId");
		String signTip = jsonObject.getString("signTip");
		Integer orderFlag = jsonObject.getInteger("orderFlag");
		String reservationDatetime = jsonObject.getString("reservationDatetime");
		String strs = jsonObject.getString("content");
		String[] StringList = strs.split("#@@#");
		String mercReqTime = DatetimeUtil.getCurrentDateTime("yyyy-MM-dd HH:mm:ss");
		
		if(batchSize < StringList.length)
			throw new TradeException(ResultCommon.ErrorCodeTypeEnum.BATCH_SEND_CONTENT_ERRO.getValue(), ResultCommon.ErrorCodeTypeEnum.BATCH_SEND_CONTENT_ERRO.getText());
		for(int i=0;i<StringList.length;i++){
			String[] str = StringList[i].split("#@#");
			String mobile = str[0];
			String content = str[1];
			Integer sendNum = 1;
			if(content.length()+signTip.length()>70)
				sendNum = (int) (Math.ceil(content.length()/(68d-signTip.length())));
			mercAccountService.frozenBalanceOne(accountNo, sendNum,messageId);	//冻结金额
			
			MercAccount mercAccount = prepareParamService.getMercAccount(accountNo);
			if(100 == mercAccount.getSenseWordFlag() && 100 == mercAccount.getTemplateMatchFlag()){
				sendToCheckService.doAll(accountNo,content);			//模板+敏感
			}else if(100 == mercAccount.getSenseWordFlag() && 200 == mercAccount.getTemplateMatchFlag()){
				sendToCheckService.doSensitive(accountNo,content);		//敏感
			}else{
				sendToCheckService.doTemplate(accountNo,content);		//模板
			}
			
			if(100 == mercAccount.getSendAuditFlag()){					//若需审核，插入审核表
				auditingService.insertToAuditOne(orderFlag,null,messageId,content,mercAccount, sendNum, mobile,signTip);
				continue;
			}
			if(200 == orderFlag){										//预约短信
				reservationSendRecordService.insertReservationMsgOne(content, messageId, reservationDatetime, mercAccount, mobile,signTip);
				continue;
			}
			channelService.switchChannelToSendOne(null,content,messageId,orderFlag,mercAccount,sendNum,mobile,signTip,mercReqTime);
		}
	}

	public void sendMsgMarket(JSONObject jsonObject) throws TradeException {
		Long accountNo = jsonObject.getLongValue("accountNo");
		String messageId = jsonObject.getString("messageId");
		String content = jsonObject.getString("content");
		String signTip = jsonObject.getString("signTip");
		Integer orderFlag = jsonObject.getInteger("orderFlag");
		String reservationDatetime = jsonObject.getString("reservationDatetime");
		String mobile = jsonObject.getString("mobile");
		String[] mobiles = mobile.split(",");
		
		Integer sendNum = 1;
		if(content.length()+signTip.length()>70)
			sendNum = (int) (Math.ceil(content.length()/(68d-signTip.length())));
		mercAccountService.frozenBalance(accountNo, sendNum, mobiles, messageId);			//冻结资金异步生成流水
		MercAccount mercAccount = prepareParamService.getMercAccount(accountNo);
		
		Integer costQuanteNum = 0;															//扣量
		if(0 < mercAccount.getCostQuantity() && mobiles.length>1)											
			costQuanteNum = costQuante(mercAccount,mobiles,sendNum,mercAccount.getCostQuantity());
		String[] mobileStrs = Arrays.copyOfRange(mobiles, costQuanteNum, mobiles.length);
		
		if (100 == mercAccount.getSenseWordFlag() && 100 == mercAccount.getTemplateMatchFlag()) {
			sendToCheckService.doAll(accountNo, content); // 模板+敏感
		} else if (100 == mercAccount.getSenseWordFlag() && 200 == mercAccount.getTemplateMatchFlag()) {
			sendToCheckService.doSensitive(accountNo, content); // 敏感
		} else if (200 == mercAccount.getSenseWordFlag() && 100 == mercAccount.getTemplateMatchFlag()) {
			sendToCheckService.doTemplate(accountNo, content); // 模板
		}
		//auditingService.insertToAudit(orderFlag,reservationDatetime,messageId,content,mercAccount, sendNum, mobileStrs,signTip);	//营销短信直接入库
		logger.info("<<<<<<<<<<<<<<");
		smsApplayService.insertApply(orderFlag, reservationDatetime, messageId, signTip, content, mercAccount, sendNum,
				mobile); // 营销短信直接入库
	}

	private Integer costQuante(MercAccount mercAccount, String[] mobiles, Integer sendNum, Double costQuantity) throws TradeException {
		Integer costQuanteNum = (int)Math.floor((double)mobiles.length * costQuantity);
		if(1 > costQuanteNum)
			return 0;
		PlainSendRecord plainSendRecord = new PlainSendRecord(null, "cost", mercAccount.getAccountNo(), mercAccount.getMerchantNameAbbreviation(), mercAccount.getAccountType(), mercAccount.getFailToReissueFlag(), null, "cost", 500, "发送成功", null,null, "cost",null,"【cost】");
		String[] strs = Arrays.copyOfRange(mobiles, 0, costQuanteNum); 
		threadService.genPlanrecord(strs, plainSendRecord);								//异步生成伪发送成功记录
		mercAccountService.unFrozenBalance(mercAccount.getAccountNo(), costQuanteNum * sendNum,"cost");			//解冻并扣减
		return costQuanteNum;
	}
	
	public void tdToUnsubscribe(JSONObject jsonObject) {
		String content = jsonObject.getString("content");
		String mobile = jsonObject.getString("mobile");
		SmsInbox inbox = new SmsInbox(mobile, content,100);
		Unsubscribe unsubscribe = unsubscribeService.getByMobile(mobile);
		if(null == unsubscribe && content.equals("TD")){
			unsubscribe = new Unsubscribe(100, mobile, 100, "退订");
			unsubscribeService.insert(unsubscribe);
			smsInboxDao.insert(inbox);
			prepareParamService.reloadBlack(mobile, "100");
			return;
		}else if(null != unsubscribe  && content.equals("TD")){
			unsubscribe.setUnsubscribeStatus(100);
			unsubscribeService.update(unsubscribe);
			smsInboxDao.insert(inbox);
			prepareParamService.reloadBlack(mobile, "100");
			return;
		}
		inbox.setTypes(200);
		smsInboxDao.insert(inbox);
	}
}