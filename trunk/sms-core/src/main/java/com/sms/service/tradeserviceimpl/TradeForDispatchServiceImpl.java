package com.sms.service.tradeserviceimpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.sms.dao.AuditingDao;
import com.sms.dao.CountChannelDao;
import com.sms.dao.CountRecordDao;
import com.sms.entity.CountChannel;
import com.sms.entity.CountRecord;
import com.sms.entity.MercAccount;
import com.sms.service.PrepareParamService;
import com.sms.service.send.ChannelService;
import com.sms.service.send.MercAccountService;
import com.sms.service.send.PlainSendRecordService;
import com.sms.service.send.ReservationSendRecordService;
import com.sms.tradeservice.api.service.TradeForDispatchService;
import com.sms.util.DatetimeUtil;
import com.sms.util.TradeException;

@Service
public class TradeForDispatchServiceImpl implements TradeForDispatchService {
	
	private static final Logger logger =LoggerFactory.getLogger(TradeForDispatchServiceImpl.class);
	
	@Autowired
	private ChannelService channelService;
	
	@Autowired
	private ReservationSendRecordService reservationSendRecordService;
	
	@Autowired
	private MercAccountService mercAccountService;
	
	@Autowired
	private PlainSendRecordService plainSendRecordService;
	
	@Autowired
	private CountChannelDao countChannelDao;
	
	@Autowired
	private CountRecordDao countRecordDao;
	
	@Autowired
	private PrepareParamService prepareParamService;
	
	@Autowired
	private AuditingDao auditingDao;
	
	@Autowired
	private SmsSendBatchNormal smsSendBatchNormal;

	@Override
	public String doSendAfterAudit(String request) {
		logger.info("审核后发送短信接口，请求信息："+request);
		JSONObject jsObject = new JSONObject();
		jsObject.put("code", "0000");
		jsObject.put("message", "受理成功");
		
		try {
			if(StringUtils.isBlank(request))
				throw new TradeException("9998", "请求参数为空");
			JSONObject j = JSONObject.parseObject(request);
			if(null == j.get("auditing"))
				throw new TradeException("9997", "缺少请求参数");
			
			Map auditing = (Map)j.get("auditing");
			String mobile = (String)auditing.get("mobile");
			String smsContent = (String)auditing.get("smsContent");
			String signTip = (String)auditing.get("signTip");
			String costTip = (String)auditing.get("costTip");
			String messageId = (String)auditing.get("batchNo");
			Integer orderFlag = (Integer)auditing.get("orderFlag");
			String reservationDatetime = (String)auditing.get("reservationDatetime");
			String mercReqTime = (String)auditing.get("auditingDatetime");
			Long auditingId = Long.valueOf(String.valueOf((Integer)auditing.get("auditingId")));
			Long accountNo = Long.valueOf(String.valueOf((Integer)auditing.get("accountNo")));
			Integer ua = auditingDao.updateInitToNew(auditingId, "200", "201");
			if(1 != ua)
				return jsObject.toJSONString();;
				
			MercAccount mercAccount = prepareParamService.getMercAccount(accountNo);
			Integer sendNum = 1;
			if(smsContent.length()+signTip.length()>70)
				sendNum = (int) (Math.ceil(smsContent.length()/(68d-signTip.length())));
			if("200".equals(costTip)){								//扣量生成记录，扣减金额
				mercAccountService.unFrozenBalance(accountNo,sendNum,auditingId.toString());
				reservationSendRecordService.insertCostMsg(smsContent, messageId, reservationDatetime, mercAccount, mobile, signTip);
				auditingDao.updateInitToNew(auditingId, "201", "202");
				throw new TradeException("0000", "成功");
			}
			if(200 == orderFlag){
				reservationSendRecordService.insertReservationMsgOne(smsContent, messageId, reservationDatetime, mercAccount, mobile,signTip);
			}else{
				channelService.switchChannelToSendAfterAudit(smsContent,messageId,mercAccount,sendNum,mobile,signTip,mercReqTime);
			}
			auditingDao.updateInitToNew(auditingId, "201", "202");
		} catch (TradeException e) {
			jsObject.put("code", e.getErrorCode());
			jsObject.put("message", e.getErrorMsg());
		} catch (Exception e) {
			jsObject.put("code", "9999");
			jsObject.put("message", e.getMessage());
		}
		logger.info("审核后发送短信接口，响应信息："+jsObject.toJSONString());
		return jsObject.toJSONString();
	}

	@Override
	public String doSendMarketMsgAfterAudit(String request) {
		logger.info("重载接口，请求信息："+request);
		JSONObject jsObject = new JSONObject();
		jsObject.put("code", "0000");
		jsObject.put("message", "受理成功");
		
		try {
			if(StringUtils.isBlank(request))
				throw new TradeException("9998", "请求参数为空");
			JSONObject j = JSONObject.parseObject(request);
			switch (j.getString("transCode")) {
			case "100":
				prepareParamService.reloadAccount(j.getLong("accountNo"));
				break;
			case "200":
				prepareParamService.reloadBlack(j.getString("mobile"),j.getString("type"));
				break;
			case "300":
				prepareParamService.reloadWhite(j.getString("mobile"),j.getString("type"));
				break;
			case "500":
				prepareParamService.reloadParam();
				break;
			}
		} catch (TradeException e) {
			jsObject.put("code", e.getErrorCode());
			jsObject.put("message", e.getErrorMsg());
		} catch (Exception e) {
			jsObject.put("code", "9999");
			jsObject.put("message", e.getMessage());
		}
		logger.info("重载接口，响应信息："+jsObject.toJSONString());
		return jsObject.toJSONString();
	}

	@Override
	public String doSendReservationMsg(String request) {
		logger.info("预约发送短信接口，请求信息："+request);
		JSONObject jsObject = new JSONObject();
		jsObject.put("code", "0000");
		jsObject.put("message", "受理成功");
		
		try {
			if(StringUtils.isBlank(request))
				throw new TradeException("9998", "请求参数为空");
			JSONObject j = JSONObject.parseObject(request);
			if(null == j.get("reservationSendRecord"))
				throw new TradeException("9997", "缺少请求参数");
			
			Map reservationSendRecord = (Map)j.get("reservationSendRecord");
			String mobile = (String)reservationSendRecord.get("mobile");
			String content = (String)reservationSendRecord.get("content");
			String signTip = (String)reservationSendRecord.get("signTip");
			String messageId = (String)reservationSendRecord.get("messageId");
			String reservationDatetime = (String)reservationSendRecord.get("reservationDatetime");
			Integer recordId = (Integer)reservationSendRecord.get("recordId");
			Long accountNo = Long.valueOf(String.valueOf((Integer)reservationSendRecord.get("accountNo")));
			Integer ur = reservationSendRecordService.updateInitStatusToNew(recordId, 100, 101);
			if(1 != ur)
				throw new TradeException("1112", "未找到待发送的预约记录！");
			MercAccount mercAccount = prepareParamService.getMercAccount(accountNo);
			Integer sendNum = 1;
			if(content.length()+signTip.length()>70)
				sendNum = (int) (Math.ceil(content.length()/(68d-signTip.length())));
			channelService.switchChannelToSendReservation(reservationDatetime,content,messageId,200,mercAccount,sendNum,mobile,signTip);
			reservationSendRecordService.updateInitStatusToNew(recordId, 101, 200);
		} catch (TradeException e) {
			jsObject.put("code", e.getErrorCode());
			jsObject.put("message", e.getErrorMsg());
		} catch (Exception e) {
			jsObject.put("code", "9999");
			jsObject.put("message", e.getMessage());
		}
		logger.info("预约发送短信接口，响应信息："+jsObject.toJSONString());
		return jsObject.toJSONString();
	}

	@Override
	public String doCrectAfterFail(String request) {
		logger.info("冲正接口，请求信息："+request);
		JSONObject jsObject = new JSONObject();
		jsObject.put("code", "0000");
		jsObject.put("message", "受理成功");
		
		try {
			if(StringUtils.isBlank(request))
				throw new TradeException("9998", "请求参数为空");
			HashMap<String, String> jsonMap = JSON.parseObject(request, new TypeReference<HashMap<String, String>>() {});
		    for (Map.Entry<String, String> entry : jsonMap.entrySet()) {
		    	String[] recordIds = entry.getValue().split(",");
		    	Long accountNo = Long.valueOf(entry.getKey());
		    	for(int i=0;i<recordIds.length;i++){
		    		Integer count = plainSendRecordService.updateInitStatusToNew(Long.valueOf(recordIds[i]), 300, 400);
		    		if(1!=count)
						continue;
		    		mercAccountService.doCorect(accountNo, 1, recordIds[i]);
		    	}
		     }
		} catch (TradeException e) {
			jsObject.put("code", e.getErrorCode());
			jsObject.put("message", e.getErrorMsg());
		} catch (Exception e) {
			jsObject.put("code", "9999");
			jsObject.put("message", e.getMessage());
		}
		logger.info("冲正接口，响应信息："+jsObject.toJSONString());
		return jsObject.toJSONString();
	}

	@Override
	public String  countChannelData(String request) {
		
		Map<String, Object> result = new HashMap<>(2);
		try {
			List<CountChannel> list = countChannelDao.getCountChannel();
			if(list.size()>0){
				for (CountChannel countChannel : list) {
					CountRecord countRecord = new CountRecord();
					countRecord.setRefId(countChannel.getChannelId());
					countRecord.setSucCount(countChannel.getSucCount());
					countRecord.setFailCount(countChannel.getFailCount());
					countRecord.setColumnName(countChannel.getChannelName());
					countRecord.setColumnType("column");
					countRecordDao.insert(countRecord);
				}
				result.put("suc", true);
				result.put("msg", DatetimeUtil.getCurrentDate()+"的通道统计已处理完成");
			}else{
				result.put("suc", false);
				result.put("msg", DatetimeUtil.getCurrentDate()+"的通道统计处理失败");
			}
			return JSONObject.toJSONString(result);
		} catch (Exception e) {
			result.put("suc", false);
			result.put("msg", e);
			return JSONObject.toJSONString(result);
		}
		
	}

	@Override
	public String countPlatformData(String request) {
		Long sum = countChannelDao.getPlatformSumData();
		Long suc = countChannelDao.getPlatformSucData();
		Long fail = countChannelDao.getPlatformFailData();
		CountRecord temp = new CountRecord();
		temp.setSumCount(sum);
		temp.setSucCount(suc);
		temp.setFailCount(fail);
		temp.setColumnType("line");
		
		countRecordDao.insert(temp);
		return null;
	}
	
	/**
	 * 普通短信批量发送
	 * @param request
	 */
	@Override
	public void sendSmsBatchNormal(String request) {
		if(StringUtils.isBlank(request)) {
			logger.info("TradeForDispatchServiceImpl.sendSmsBatch-Request is blank.");
			return;
		}
		logger.info("TradeForDispatchServiceImpl.sendSmsBatch-Request：batch begin");
		JSONObject requestJson = JSON.parseObject(request);
		String smsContent = requestJson.getString("sms_content");
		String mobileOperator = requestJson.getString("mobile_operator");
		Long batchNo = Long.valueOf(requestJson.getString("batch_no"));
		
		logger.info("TradeForDispatchServiceImpl.sendSmsBatch-Request：{}" , batchNo);
		
		String signTip = requestJson.getString("sign_tip");
		Long accountNo = Long.valueOf(requestJson.getString("account_no"));
		String mobilesData = requestJson.getString("mobile_data");
		int chargeCount = Integer.valueOf(requestJson.getString("charge_count"));
		int accountType = Integer.valueOf(requestJson.getString("account_type"));
		
		smsSendBatchNormal.batchNormalSend(batchNo, accountNo, mobilesData, accountType, mobileOperator, signTip, smsContent, chargeCount);
	}
	
	public static void main(String[] args) {
		String smsContent = "单品鲜花，每周一束，全年52束，1188元（11.11下单送生日鲜花，99朵红玫瑰），（另+288元升级为/2.14情人节/七夕节/双节99朵红玫瑰）让她一年都能感受到你的爱与关怀，原价2688元，11.11只要1476元。退订回T";
		String signTip ="【花悠享】";
		Integer sendNum = 1;
		if(smsContent.length()+signTip.length()>70)
			sendNum = (int) (Math.ceil(smsContent.length()/(68d-signTip.length())));
		System.out.println(sendNum);
	}

}