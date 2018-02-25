package com.sms.service.tradeserviceimpl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.sms.criteria.ReservationSendRecordCriteria;
import com.sms.entity.MercAccount;
import com.sms.service.PrepareParamService;
import com.sms.service.SendToCheckService;
import com.sms.service.send.AuditingService;
import com.sms.service.send.ChannelService;
import com.sms.service.send.MercAccountService;
import com.sms.service.send.ReservationSendRecordService;
import com.sms.tradeservice.api.service.TradeExcuteService;
import com.sms.util.DatetimeUtil;
import com.sms.util.ResultCommon;
import com.sms.util.TradeException;


/**
 * 
 * 理财交易服务接口
 */
@Service
public class TradeExcuteServiceImpl implements TradeExcuteService {
	
	private static final Logger logger = Logger.getLogger(TradeExcuteServiceImpl.class);
	
	@Autowired
	private PrepareParamService prepareParamService;
	
	@Autowired
	private MercAccountService mercAccountService;
	
	@Autowired
	private SendToCheckService sendToCheckService;
	
	@Autowired
	private AuditingService auditingService;
	
	@Autowired
	private ChannelService channelService;
	
	@Autowired
	private ReservationSendRecordService reservationSendRecordService;
	
	@Value("${sms.batch.size}")
	private Integer batchSize;

	//及时短信 单条+批量
	@Override
	public String sendMsg(String request) {
		logger.info("发送短信接口，请求信息："+request);
		JSONObject jsObject = new JSONObject();
		jsObject.put("code", "0000");
		jsObject.put("message", "受理成功");
		
		try {
			if(StringUtils.isBlank(request))
				throw new TradeException("9998", "请求参数为空");
			JSONObject j = JSONObject.parseObject(request);
			if(null == j.getLong("accountNo") || StringUtils.isBlank(j.getString("messageId"))|| StringUtils.isBlank(j.getString("mobile")) || StringUtils.isBlank(j.getString("content")))
				throw new TradeException("9997", "缺少请求参数");
			
			Long accountNo = j.getLongValue("accountNo");
			String messageId = j.getString("messageId");
			String content = j.getString("content");
			Integer orderFlag = 100;
			String signTip = j.getString("signTip");
			String mercReqTime = DatetimeUtil.getCurrentDateTime("yyyy-MM-dd HH:mm:ss");
			String mobile = j.getString("mobile");
			String[] mobiles = mobile.split(",");
			
			Integer sendNum = 1;
			if(content.length()+signTip.length()>70)
				sendNum = (int) (Math.ceil(content.length()/(68d-signTip.length())));
			mercAccountService.frozenBalance(accountNo, sendNum, mobiles, messageId);			//冻结资金异步生成流水
			MercAccount mercAccount = prepareParamService.getMercAccount(accountNo);
			
			Integer costQuanteNum = 0;															//扣量
			if(0 < mercAccount.getCostQuantity() && mobiles.length>1)											
				costQuanteNum = mercAccountService.costQuante(mercAccount,mobiles,sendNum,mercAccount.getCostQuantity());
			String[] mobileStrs = Arrays.copyOfRange(mobiles, costQuanteNum, mobiles.length);
			
			if(100 == mercAccount.getSenseWordFlag() && 100 == mercAccount.getTemplateMatchFlag()){
				sendToCheckService.doAll(accountNo,content);			//模板+敏感
			}else if(100 == mercAccount.getSenseWordFlag() && 200 == mercAccount.getTemplateMatchFlag()){
				sendToCheckService.doSensitive(accountNo,content);		//敏感
			}else{
				sendToCheckService.doTemplate(accountNo,content);		//模板
			}
			if(100 == mercAccount.getSendAuditFlag()){					//若需审核，插入审核表
				auditingService.insertToAudit(orderFlag,null,messageId,content,mercAccount, sendNum, mobileStrs,signTip);
				throw new TradeException("0000","审核受理成功");
			}
			channelService.switchChannelToSend(null,content,messageId,orderFlag,mercAccount,sendNum,mobileStrs,signTip,mercReqTime);
		} catch (TradeException e) {
			jsObject.put("code", e.getErrorCode());
			jsObject.put("message", e.getErrorMsg());
		} catch (Exception e) {
			jsObject.put("code", "9999");
			jsObject.put("message", e.getMessage());
		}
		logger.info("发送短信接口，响应信息："+jsObject.toJSONString());
		return jsObject.toJSONString();
	}
	
	//行业预约短信
	@Override
	public String sendMsgMarket(String request) {
		logger.info("发送预约短信接口，请求信息："+request);
		JSONObject jsObject = new JSONObject();
		jsObject.put("code", "0000");
		jsObject.put("message", "受理成功");
		
		try {
			if(StringUtils.isBlank(request))
				throw new TradeException("9998", "请求参数为空");
			JSONObject j = JSONObject.parseObject(request);
			if(null == j.getLong("accountNo") || StringUtils.isBlank(j.getString("messageId"))|| StringUtils.isBlank(j.getString("mobile")) || StringUtils.isBlank(j.getString("content"))|| StringUtils.isBlank(j.getString("reservationDatetime")))
				throw new TradeException("9997", "缺少请求参数");
		
			Long accountNo = jsObject.getLongValue("accountNo");
			String messageId = jsObject.getString("messageId");
			String content = jsObject.getString("content");
			Integer orderFlag = 200;
			String signTip = j.getString("signTip");
			String reservationDatetime = jsObject.getString("reservationDatetime");
			String mobile = jsObject.getString("mobile");
			String[] mobiles = mobile.split(",");
		
			Integer sendNum = 1;
			if(content.length()+signTip.length()>70)
				sendNum = (int) (Math.ceil(content.length()/(68d-signTip.length())));
			mercAccountService.frozenBalance(accountNo, sendNum, mobiles, messageId);			//冻结资金异步生成流水
			MercAccount mercAccount = prepareParamService.getMercAccount(accountNo);
			
			Integer costQuanteNum = 0;															//扣量
			if(0 < mercAccount.getCostQuantity() && mobiles.length>1)											
				costQuanteNum = mercAccountService.costQuante(mercAccount,mobiles,sendNum,mercAccount.getCostQuantity());
			String[] mobileStrs = Arrays.copyOfRange(mobiles, costQuanteNum, mobiles.length);
			
			if(100 == mercAccount.getSenseWordFlag() && 100 == mercAccount.getTemplateMatchFlag()){
				sendToCheckService.doAll(accountNo,content);			//模板+敏感
			}else if(100 == mercAccount.getSenseWordFlag() && 200 == mercAccount.getTemplateMatchFlag()){
				sendToCheckService.doSensitive(accountNo,content);		//敏感
			}else{
				sendToCheckService.doTemplate(accountNo,content);		//模板
			}
			if(100 == mercAccount.getSendAuditFlag()){					//若需审核，插入审核表
				auditingService.insertToAudit(orderFlag,reservationDatetime,messageId,content,mercAccount, sendNum, mobileStrs,signTip);
				throw new TradeException("0000","审核受理成功");
			}
			reservationSendRecordService.insertReservationMsg(content, messageId, reservationDatetime, mercAccount, mobiles,signTip);
		} catch (TradeException e) {
			jsObject.put("code", e.getErrorCode());
			jsObject.put("message", e.getErrorMsg());
		} catch (Exception e) {
			jsObject.put("code", "9999");
			jsObject.put("message", e.getMessage());
		}
		logger.info("发送预约短信接口，响应信息："+jsObject.toJSONString());
		return jsObject.toJSONString();
	}

	//个性短信
	@Override
	public String sendDifferentMsg(String request) {
		logger.info("发送个性短信接口，请求信息："+request);
		JSONObject jsObject = new JSONObject();
		jsObject.put("code", "0000");
		jsObject.put("message", "受理成功");
		
		try {
			if(StringUtils.isBlank(request))
				throw new TradeException("9998", "请求参数为空");
			JSONObject j = JSONObject.parseObject(request);
			if(null == j.getLong("accountNo") || StringUtils.isBlank(j.getString("messageId")) || null == j.getInteger("orderFlag") || StringUtils.isBlank(j.getString("reservationDatetime")) || StringUtils.isBlank(j.getString("content")))
				throw new TradeException("9997", "缺少请求参数");
			
			Long accountNo = j.getLongValue("accountNo");
			String messageId = j.getString("messageId");
			Integer orderFlag = j.getInteger("orderFlag");
			String reservationDatetime = j.getString("reservationDatetime");
			String mercReqTime = DatetimeUtil.getCurrentDateTime("yyyy-MM-dd HH:mm:ss");
			String signTip = j.getString("signTip");
			String strs = j.getString("content");
			String[] StringList = strs.split("#@@#");
		
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
		} catch (TradeException e) {
			jsObject.put("code", e.getErrorCode());
			jsObject.put("message", e.getErrorMsg());
		} catch (Exception e) {
			jsObject.put("code", "9999");
			jsObject.put("message", e.getMessage());
		}
		logger.info("发送个性短信接口，响应信息："+jsObject.toJSONString());
		return jsObject.toJSONString();
	}

	//余额查询
	@Override
	public String balanceQuery(String request) {
		logger.info("余额查询接口，请求信息："+request);
		JSONObject jsObject = new JSONObject();
		jsObject.put("code", "0000");
		jsObject.put("message", "受理成功");
		
		try {
			if(StringUtils.isBlank(request))
				throw new TradeException("9998", "请求参数为空");
			JSONObject j = JSONObject.parseObject(request);
			if(null == j.getLong("accountNo"))
				throw new TradeException("9997", "缺少请求参数");
			
			Long accountNo = jsObject.getLongValue("accountNo");
			MercAccount mercAccount = prepareParamService.getMercAccount(accountNo);
			if(null == mercAccount)
				throw new TradeException(ResultCommon.ErrorCodeTypeEnum.NO_ACCOUNT.getValue(), ResultCommon.ErrorCodeTypeEnum.NO_ACCOUNT.getText());
			jsObject.put("total", mercAccount.getTotalBalance());
			jsObject.put("free", mercAccount.getFreeBalance());
			jsObject.put("frozen", mercAccount.getFrozenBalance());
		} catch (TradeException e) {
			jsObject.put("code", e.getErrorCode());
			jsObject.put("message", e.getErrorMsg());
		} catch (Exception e) {
			jsObject.put("code", "9999");
			jsObject.put("message", e.getMessage());
		}
		logger.info("余额查询接口，响应信息："+jsObject.toJSONString());
		return jsObject.toJSONString();
	}

	//结果查询
	@Override
	public String resultQuery(String request) {
		logger.info("结果查询接口，请求信息："+request);
		JSONObject jsObject = new JSONObject();
		jsObject.put("code", "0000");
		jsObject.put("message", "受理成功");
		
		try {
			if(StringUtils.isBlank(request))
				throw new TradeException("9998", "请求参数为空");
			JSONObject j = JSONObject.parseObject(request);
			if(StringUtils.isBlank(j.getString("messageId")))
				throw new TradeException("9997", "缺少请求参数");
			
			String messageId = jsObject.getString("messageId");
			ReservationSendRecordCriteria criteria = new ReservationSendRecordCriteria();
			criteria.setMessageId(messageId);
			List<Map<String,Object>> list = reservationSendRecordService.queryResult(criteria);
			jsObject.put("list", list);
		} catch (TradeException e) {
			jsObject.put("code", e.getErrorCode());
			jsObject.put("message", e.getErrorMsg());
		} catch (Exception e) {
			jsObject.put("code", "9999");
			jsObject.put("message", e.getMessage());
		}
		logger.info("结果查询接口，响应信息："+jsObject.toJSONString());
		return jsObject.toJSONString();
	}
	
}