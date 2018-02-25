package com.godai.trade.service.smsupload;

import java.sql.Timestamp;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.godai.trade.dao.smsupload.SmsApplayDao;
import com.godai.trade.dao.smsupload.SmsApplayDetailDao;
import com.godai.trade.entity.smsupload.SmsApplay;
import com.godai.trade.entity.smsupload.SmsApplayDetail;
import com.godai.trade.util.PackageRequestUtil;
import com.sms.tradeservice.api.service.TradeForDispatchService;

@Service
public class SmsApplayService {
	private final Logger logger = LoggerFactory.getLogger(SmsApplayService.class);
	
	@Autowired
	private SmsApplayDao smsApplayDao;
	
	@Autowired
	private SmsApplayDetailDao smsApplayDetailDao;
	
	@Autowired
	private TradeForDispatchService tradeForDispatchService;
	
	private Executor exec = Executors.newFixedThreadPool(6);
	
	public Runnable doSendNormal() {
		return new Runnable() {
			
			@Override
			public void run() {
				List<SmsApplay> list = smsApplayDao.loadNormalApprove();
				if(list.size() == 0 || list.isEmpty()) return;
				for(SmsApplay sc : list) {
					execSend(sc);
				}
			}
		};
	}
	
	private void execSend(SmsApplay sc) {
		int approvedNum = smsApplayDetailDao.loadApprovedNum(sc.getBatchNo());
		if(approvedNum == 0){
			smsApplayDao.setDealed(sc.getSmsApplayId());
			return;
		}
		
		sendCUData(sc);
		sendCMData(sc);
		sendCTData(sc);
		
		sendCUData(sc);
		sendCMData(sc);
		sendCTData(sc);
	}
	
	private void sendCUData(final SmsApplay smsApplay) {
		exec.execute(new Runnable() {
			
			@Override
			public void run() {
				try {
					Long batchNo = smsApplay.getBatchNo();
					String smsContent = smsApplay.getSmsContent();
					Long accountNo = smsApplay.getAccountNo();
					String signTip = smsApplay.getSignTip();
					int orderFlag = smsApplay.getOrderFlag();
					int accountType = smsApplay.getAccountType();
					Timestamp appointmentTime = smsApplay.getAppointmentTime();
					List<SmsApplayDetail> cnList = smsApplayDetailDao.loadApprovedByBatchNo(batchNo, 200, 0, 25);
					if(cnList.isEmpty() || cnList.size() == 0) return;
					
					int penddingRow = smsApplayDetailDao.batchUpdatePendding(cnList);
					if(penddingRow == 0) return;
					
					String cnReqJson = PackageRequestUtil.packageNormalSmsJsonStr(accountNo, 200, batchNo, smsContent, signTip, cnList, orderFlag, appointmentTime, accountType);
					logger.info("SmsApplayService.sendCUData-cnReqJson{}", cnReqJson);
					tradeForDispatchService.sendSmsBatchNormal(cnReqJson);
				} catch (Exception e) {
					logger.error("SmsApplayService.sendCUData-Exception{}", e);
				}
			}
		});
		
		
		/*new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					Long batchNo = smsApplay.getBatchNo();
					String smsContent = smsApplay.getSmsContent();
					Long accountNo = smsApplay.getAccountNo();
					String signTip = smsApplay.getSignTip();
					int orderFlag = smsApplay.getOrderFlag();
					int accountType = smsApplay.getAccountType();
					Timestamp appointmentTime = smsApplay.getAppointmentTime();
					List<SmsApplayDetail> cnList = smsApplayDetailDao.loadApprovedByBatchNo(batchNo, 200, 0, 25);
					if(cnList.isEmpty() || cnList.size() == 0) return;
					
					int penddingRow = smsApplayDetailDao.batchUpdatePendding(cnList);
					if(penddingRow == 0) return;
					
					String cnReqJson = PackageRequestUtil.packageNormalSmsJsonStr(accountNo, 200, batchNo, smsContent, signTip, cnList, orderFlag, appointmentTime, accountType);
					tradeForDispatchService.sendSmsBatchNormal(cnReqJson);
				} catch (Exception e) {
					logger.error("SmsApplayService.sendCUData-Exception{}", e);
				}
			}
		}).start();*/
	}
	
	private void sendCMData(final SmsApplay smsApplay) {
		exec.execute(new Runnable() {
			
			@Override
			public void run() {
				try {
					Long batchNo = smsApplay.getBatchNo();
					String smsContent = smsApplay.getSmsContent();
					Long accountNo = smsApplay.getAccountNo();
					String signTip = smsApplay.getSignTip();
					int orderFlag = smsApplay.getOrderFlag();
					int accountType = smsApplay.getAccountType();
					Timestamp appointmentTime = smsApplay.getAppointmentTime();
					List<SmsApplayDetail> cmList = smsApplayDetailDao.loadApprovedByBatchNo(batchNo, 300, 0, 25);
					if(cmList.isEmpty() || cmList.size() == 0) return;
					
					int penddingRow = smsApplayDetailDao.batchUpdatePendding(cmList);
					if(penddingRow == 0) return;
					
					String cmReqJson = PackageRequestUtil.packageNormalSmsJsonStr(accountNo, 300, batchNo, smsContent, signTip, cmList, orderFlag, appointmentTime, accountType);
					logger.info("SmsApplayService.sendCMData-cmReqJson{}", cmReqJson);
					tradeForDispatchService.sendSmsBatchNormal(cmReqJson);
				} catch (Exception e) {
					logger.error("SmsApplayService.sendCMData-Exception{}", e);
				}
			}
		});
		/*new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					Long batchNo = smsApplay.getBatchNo();
					String smsContent = smsApplay.getSmsContent();
					Long accountNo = smsApplay.getAccountNo();
					String signTip = smsApplay.getSignTip();
					int orderFlag = smsApplay.getOrderFlag();
					int accountType = smsApplay.getAccountType();
					Timestamp appointmentTime = smsApplay.getAppointmentTime();
					List<SmsApplayDetail> cmList = smsApplayDetailDao.loadApprovedByBatchNo(batchNo, 300, 0, 25);
					if(cmList.isEmpty() || cmList.size() == 0) return;
					
					int penddingRow = smsApplayDetailDao.batchUpdatePendding(cmList);
					if(penddingRow == 0) return;
					
					String cmReqJson = PackageRequestUtil.packageNormalSmsJsonStr(accountNo, 300, batchNo, smsContent, signTip, cmList, orderFlag, appointmentTime, accountType);
					tradeForDispatchService.sendSmsBatchNormal(cmReqJson);
				} catch (Exception e) {
					logger.error("SmsApplayService.sendCMData-Exception{}", e);
				}
			}
		}).start();*/
	}
	
	private void sendCTData(final SmsApplay smsApplay) {
		exec.execute(new Runnable() {
			
			@Override
			public void run() {
				try {
					Long batchNo = smsApplay.getBatchNo();
					String smsContent = smsApplay.getSmsContent();
					Long accountNo = smsApplay.getAccountNo();
					String signTip = smsApplay.getSignTip();
					int orderFlag = smsApplay.getOrderFlag();
					int accountType = smsApplay.getAccountType();
					Timestamp appointmentTime = smsApplay.getAppointmentTime();
					List<SmsApplayDetail> ctList = smsApplayDetailDao.loadApprovedByBatchNo(batchNo, 100, 0, 25);
					if(ctList.isEmpty() || ctList.size() == 0) return;
					
					int penddingRow = smsApplayDetailDao.batchUpdatePendding(ctList);
					if(penddingRow == 0) return;
					
					String ctReqJson = PackageRequestUtil.packageNormalSmsJsonStr(accountNo, 100, batchNo, smsContent, signTip, ctList, orderFlag, appointmentTime, accountType);
					logger.info("SmsApplayService.sendCTData-ctReqJson{}", ctReqJson);
					tradeForDispatchService.sendSmsBatchNormal(ctReqJson);
				} catch (Exception e) {
					logger.error("SmsApplayService.sendCTData-Exception{}", e);
				}
			}
		});
		
		
		/*new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					Long batchNo = smsApplay.getBatchNo();
					String smsContent = smsApplay.getSmsContent();
					Long accountNo = smsApplay.getAccountNo();
					String signTip = smsApplay.getSignTip();
					int orderFlag = smsApplay.getOrderFlag();
					int accountType = smsApplay.getAccountType();
					Timestamp appointmentTime = smsApplay.getAppointmentTime();
					List<SmsApplayDetail> ctList = smsApplayDetailDao.loadApprovedByBatchNo(batchNo, 100, 0, 25);
					if(ctList.isEmpty() || ctList.size() == 0) return;
					
					int penddingRow = smsApplayDetailDao.batchUpdatePendding(ctList);
					if(penddingRow == 0) return;
					
					String ctReqJson = PackageRequestUtil.packageNormalSmsJsonStr(accountNo, 100, batchNo, smsContent, signTip, ctList, orderFlag, appointmentTime, accountType);
					tradeForDispatchService.sendSmsBatchNormal(ctReqJson);
				} catch (Exception e) {
					logger.error("SmsApplayService.sendCTData-Exception{}", e);
				}
			}
		}).start();*/
	}
}