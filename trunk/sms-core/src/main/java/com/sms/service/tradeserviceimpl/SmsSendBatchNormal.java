package com.sms.service.tradeserviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sms.dao.PlainSendRecordDao;
import com.sms.entity.MercChannel;
import com.sms.entity.PlainSendRecord;
import com.sms.service.PrepareParamService;
import com.sms.service.send.SwichToSendService;

@Service
public class SmsSendBatchNormal {
	private static final Logger logger = LoggerFactory.getLogger(SmsSendBatchNormal.class);
	@Autowired
	private PrepareParamService prepareParamService;

	@Autowired
	private SwichToSendService swichToSendService;

	@Autowired
	private PlainSendRecordDao plainSendRecordDao;

	public void batchNormalSend(Long batchNo, Long accountNo, String mobilesData, int accountType,
			String mobileOperator, String signTip, String smsContent, int chargeCount) {
		Long channelId = null;

		// 加载商户关联通道
		String channelCode = selectSmsChannel(accountNo, accountType, mobileOperator, channelId);
		if (null == channelCode) {
			batchgenFailureSendRecord(batchNo, accountNo, accountType, mobilesData, channelCode, channelId, signTip,
					smsContent, "无通道信息");
			return;
		}

		String content = signTip + smsContent;
		// 准备批量短信发送
		Map<String, Object> resultMap = swichToSendService.batchSendSameContent(batchNo, accountNo, mobilesData,
				signTip, content, channelCode, accountType);
		// 处理发送结果-生成短信发送记录
		dealResult(resultMap, batchNo, accountNo, accountType, mobilesData, channelCode, channelId, signTip,
				smsContent);
	}

	private void dealResult(Map<String, Object> resultMap, Long batchNo, Long accountNo, int accountType,
			String mobilesData, String channelCode, Long channelId, String signTip, String smsContent) {
		if (null == resultMap || resultMap.isEmpty()) {
			logger.error("batchNo:{},accountNo:{},mobilesData{} batchNormalSend-Failure", batchNo, accountNo,
					accountType, mobilesData);
			return;
		}
		boolean sendResult = (boolean) resultMap.get("status");
		if (sendResult) {
			batchGenSuccessSendRecord(batchNo, accountNo, accountType, mobilesData, resultMap, channelCode, channelId,
					signTip, smsContent);

			logger.info("batchNo:{},accountNo:{},mobilesData{} batchGenSuccessSendRecord", batchNo, accountNo,
					accountType, mobilesData);
		} else {
			batchgenFailureSendRecord(batchNo, accountNo, accountType, mobilesData, channelCode, channelId, signTip,
					smsContent, "发送失败");

			logger.info("batchNo:{},accountNo:{},mobilesData{} batchgenFailureSendRecord", batchNo, accountNo,
					accountType, mobilesData);
		}
	}

	private void batchgenFailureSendRecord(Long batchNo, Long accountNo, int accountType, String mobilesData,
			String channelCode, Long channelId, String signTip, String smsContent, String errorMsg) {
		String[] mobileArray = mobilesData.split(",");
		List<PlainSendRecord> list = new ArrayList<PlainSendRecord>();
		PlainSendRecord sc = null;
		for (String tmpMob : mobileArray) {
			sc = new PlainSendRecord();
			sc.setBatchNo(batchNo);
			sc.setChannelId(channelId);
			sc.setChannelName(channelCode);
			sc.setAccountNo(accountNo);
			sc.setContent(signTip + smsContent);
			sc.setFailedNum(1);
			sc.setMobile(tmpMob);
			sc.setReqMsgId(null);
			sc.setSignTip(signTip);
			sc.setSendStatus(400);
			sc.setSendMsg(errorMsg);
			list.add(sc);
		}
		// TODO 批量添加
		plainSendRecordDao.insertBatchList(list);
	}

	private void batchGenSuccessSendRecord(Long batchNo, Long accountNo, int accountType, String mobilesData,
			Map<String, Object> resultMap, String channelCode, Long channelId, String signTip, String smsContent) {
		List<PlainSendRecord> list = new ArrayList<PlainSendRecord>();
		PlainSendRecord sc = null;
		if (channelCode.contains("juxin")) {
			List<Map<String, String>> resultList = (List<Map<String, String>>) resultMap.get("respObj");
			for (Map<String, String> tmpMap : resultList) {
				sc = new PlainSendRecord();
				sc.setBatchNo(batchNo);
				sc.setAccountNo(accountNo);
				sc.setAccountType(accountType);
				sc.setChannelId(channelId);
				sc.setChannelName(channelCode);
				sc.setContent(signTip + smsContent);
				sc.setFailedNum(0);
				sc.setMobile(tmpMap.get("p"));
				sc.setReqMsgId(tmpMap.get("mid"));
				sc.setSignTip(signTip);
				sc.setSendStatus(200);
				sc.setSendMsg("提交成功");
				list.add(sc);
			}
		} else if (channelCode.contains("meilian")) {
			String[] mobiles = mobilesData.split(",");
			String reqMid = (String) resultMap.get("reqMsgId");
			for (String mbl : mobiles) {
				sc = new PlainSendRecord();
				sc.setBatchNo(batchNo);
				sc.setAccountNo(accountNo);
				sc.setAccountType(accountType);
				sc.setChannelId(channelId);
				sc.setChannelName(channelCode);
				sc.setContent(signTip + smsContent);
				sc.setFailedNum(0);
				sc.setMobile(mbl);
				sc.setReqMsgId(reqMid);
				sc.setSignTip(signTip);
				sc.setSendStatus(200);
				sc.setSendMsg("提交成功");
				list.add(sc);
			}
		} else if (channelCode.contains("xunqi")) {
			String[] mobiles = mobilesData.split(",");
			String reqMid = (String) resultMap.get("reqMsgId");
			for (String mbl : mobiles) {
				sc = new PlainSendRecord();
				sc.setBatchNo(batchNo);
				sc.setAccountNo(accountNo);
				sc.setAccountType(accountType);
				sc.setChannelId(channelId);
				sc.setChannelName(channelCode);
				sc.setContent(signTip + smsContent);
				sc.setFailedNum(0);
				sc.setMobile(mbl);
				sc.setReqMsgId(reqMid);
				sc.setSignTip(signTip);
				sc.setSendStatus(200);
				sc.setSendMsg("提交成功");
				list.add(sc);
			}
		} else if (channelCode.contains("jumeng")) {
			String[] mobiles = mobilesData.split(",");
			String reqMid = (String) resultMap.get("reqMsgId");
			for (String mbl : mobiles) {
				sc = new PlainSendRecord();
				sc.setBatchNo(batchNo);
				sc.setAccountNo(accountNo);
				sc.setAccountType(accountType);
				sc.setChannelId(channelId);
				sc.setChannelName(channelCode);
				sc.setContent(signTip + smsContent);
				sc.setFailedNum(0);
				sc.setMobile(mbl);
				sc.setReqMsgId(reqMid);
				sc.setSignTip(signTip);
				sc.setSendStatus(200);
				sc.setSendMsg("提交成功");
				list.add(sc);
			}
		}else if (channelCode.contains("maiyuan")) {
			String[] mobiles = mobilesData.split(",");
			String reqMid = (String) resultMap.get("reqMsgId");
			for (String mbl : mobiles) {
				sc = new PlainSendRecord();
				sc.setBatchNo(batchNo);
				sc.setAccountNo(accountNo);
				sc.setAccountType(accountType);
				sc.setChannelId(channelId);
				sc.setChannelName(channelCode);
				sc.setContent(signTip + smsContent);
				sc.setFailedNum(0);
				sc.setMobile(mbl);
				sc.setReqMsgId(reqMid);
				sc.setSignTip(signTip);
				sc.setSendStatus(200);
				sc.setSendMsg("提交成功");
				list.add(sc);
			}
		}
		// TODO 批量insert
		plainSendRecordDao.insertBatchList(list);
	}

	private String selectSmsChannel(Long accountNo, int accountType, String mobileOperator, Long channelId) {
		String channelCode = null;
		List<MercChannel> mercChannelList = prepareParamService.getMercChannel(accountNo);
		if (mercChannelList.isEmpty() || mercChannelList.size() == 0)
			return null;
		String phoneShip = null;
		if (mobileOperator.equals("100"))
			phoneShip = "电信";
		if (mobileOperator.equals("200"))
			phoneShip = "联通";
		if (mobileOperator.equals("300"))
			phoneShip = "移动";
		if (accountType == 100) {
			for (MercChannel mercChannel : mercChannelList) {
				if (mercChannel.getChannelAttribute() == 100) {
					if (mobileOperator == mercChannel.getSupportOperators()) {
						switch (phoneShip) {
						case "移动":
							channelCode = mercChannel.getChannelCode() + "CMCC";
							break;
						case "联通":
							channelCode = mercChannel.getChannelCode() + "UN";
							break;
						case "电信":
							channelCode = mercChannel.getChannelCode() + "CN";
							break;
						}
						return channelCode;
					}
				}
			}
			for (MercChannel mercChannel : mercChannelList) {
				if (mercChannel.getChannelAttribute() == 100 && mercChannel.getSupportOperatorsDes().equals("全网通")) {
					channelCode = mercChannel.getChannelCode();
					return channelCode;
				}
			}
		} else {
			for (MercChannel mercChannel : mercChannelList) {
				if (mercChannel.getChannelAttribute() == 200) {
					if (phoneShip.equals(mercChannel.getSupportOperatorsDes())) {
						switch (phoneShip) {
						case "移动":
							channelCode = mercChannel.getChannelCode() + "yxCMCC";
							break;
						case "联通":
							channelCode = mercChannel.getChannelCode() + "yxUN";
							break;
						case "电信":
							channelCode = mercChannel.getChannelCode() + "yxCN";
							break;
						}
						return channelCode;
					}
				}
			}
			for (MercChannel mercChannel : mercChannelList) {
				if (mercChannel.getChannelAttribute() == 200 && mercChannel.getSupportOperatorsDes().equals("全网通")) {
					channelCode = mercChannel.getChannelCode();
					return channelCode;
				}
			}
		}
		return channelCode;
		// String channelDes = mercChannelList.get(0).getChannelDes();
		// channelId = mercChannelList.get(0).getChannelId();
		/*
		 * switch(accountType + mobileOperator +
		 * mercChannelList.get(0).getSupportOperators()) { case "100100200": channelCode
		 * = channelDes+"UN"; break; case "100200300": channelCode = channelDes+"CMCC";
		 * break; case "100300100": channelCode = channelDes+"CN"; break; case
		 * "100100400": channelCode = channelDes; break; case "100200400": channelCode =
		 * channelDes; break; case "100300400": channelCode = channelDes; break; case
		 * "200100200": channelCode = channelDes+"yxUN"; break; case "200200300":
		 * channelCode = channelDes+"yxCMCC"; break; case "200300100": channelCode =
		 * channelDes+"yxCN"; break; case "200100400": channelCode = channelDes; break;
		 * case "200200400": channelCode = channelDes; break; case "200300400":
		 * channelCode = channelDes; break; } return channelCode;
		 */
	}
}