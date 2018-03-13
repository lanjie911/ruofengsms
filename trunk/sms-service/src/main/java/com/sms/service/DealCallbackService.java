package com.sms.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sms.entity.MercAccount;
import com.sms.entity.PlainSendRecord;
import com.sms.entity.PlainSendResp;
import com.sms.service.executor.MyExecutor;
import com.sms.service.send.MercAccountService;
import com.sms.service.send.PlainSendRespService;
import com.sms.service.send.ReservationSendRecordService;
import com.sms.util.TradeException;

@Service
public class DealCallbackService {
	private static Logger logger = LoggerFactory.getLogger(DealCallbackService.class);
	@Autowired
	private ReservationSendRecordService reservationSendRecordService;
	@Autowired
	private PlainSendRespService plainSendRespService;

	@Autowired
	private PrepareParamService prepareParamService;

	@Autowired
	private MercAccountService mercAccountService;

	/*
	 * private Executor dealRespExec = Executors.newFixedThreadPool(10);
	 * 
	 * public void prepareJuxinRespData(String reponseStr){ dealRespExec.execute(new
	 * Runnable() {
	 * 
	 * @Override public void run() { try { dealJuxinSms(reponseStr); } catch
	 * (Exception e) { e.printStackTrace();
	 * logger.error("DealCallbackService.prepareJuxinRespData-Exception:{}", e); } }
	 * }); }
	 */
	@Autowired
	private MyExecutor myExecutor;

	private ExecutorService dealRespExec = null;

	@SuppressWarnings("static-access")
	@PostConstruct
	private void init() {
		this.logger = LoggerFactory.getLogger(this.getClass());
		dealRespExec = myExecutor.getExecutors();
	}

	@SuppressWarnings({ "rawtypes", "unused" })
	private Class getEntityClass() {
		return null;
	}

	public void prepareJuxinRespData(String reponseStr, String respDatetime) {
		dealRespExec.submit(new Runnable() {
			@Override
			public void run() {
				try {
					dealJuxinSms(reponseStr, respDatetime);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}
		});
	}

	private void dealJuxinSms(String reponseStr, String respDatetime) {
		String[] reports = reponseStr.substring(6, reponseStr.length() - 2).split("\",\"");
		String[] one = reports[0].split(",");
		PlainSendRecord plainSendRecord = reservationSendRecordService.getByreqMsgId(one[0]);
		if (plainSendRecord != null) {
			MercAccount mercAccount = prepareParamService.getMercAccount(plainSendRecord.getAccountNo());
			if (mercAccount != null) {
				int count = 0;
				// 发送成功后需要解冻的数量
				int successCount = 0;
				// 发送失败后提交量扣费方式需要解冻的数量
				int submitCharingCount = 0;
				// 发送失败后成功量扣费方式需要冲正的数量
				int successCharingCount = 0;
//				List<PlainSendRecord> planList = new ArrayList<PlainSendRecord>();
				List<PlainSendResp> respList = new ArrayList<PlainSendResp>();
				PlainSendResp tmpBean = null;
				for (int i = 0; i < reports.length; i++) {
					
					try {
						String[] reportOne = reports[i].split(",");
						String reqid = reportOne[0];

						if ("DELIVRD".equals(reportOne[2])) {
							tmpBean = new PlainSendResp(reportOne[0], reportOne[1], 500, "发送成功", respDatetime);
							 count = plainSendRespService.insert(tmpBean);
							respList.add(tmpBean);
							if (count > 0) {
								count++;
								successCount++;
							}
						} else {
							tmpBean = new PlainSendResp(reportOne[0], reportOne[1], 300, "发送失败",  respDatetime);
							 count = plainSendRespService.insert(tmpBean);
							respList.add(tmpBean);
							if (count > 0) {
								if (100 == mercAccount.getChargingMethods()) {
									count++;
									submitCharingCount++;
									logger.info("mercAccount.getChargingMethods():" + mercAccount.getChargingMethods()
											+ "submitCharingCount:" + submitCharingCount);
								} else if (200 == mercAccount.getChargingMethods()) {
									count++;
									successCharingCount++;
									logger.info("mercAccount.getChargingMethods():" + mercAccount.getChargingMethods()
											+ "successCharingCount:" + successCharingCount);
								}
							}
						}
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
						continue;
					}
				}

				logger.info("count:" + count);
				if (count > 0) {
					try {
						if (successCount > 0) {
							mercAccountService.batchUnFrozenBalance(mercAccount.getAccountNo(), successCount);
							logger.info("successCount:" + successCount);
						}
				
						if (submitCharingCount > 0) {
							mercAccountService.batchUnFrozenBalance(mercAccount.getAccountNo(), submitCharingCount);
							logger.info("submitCharingCount:" + submitCharingCount);
						}
					
						if (successCharingCount > 0) {
							mercAccountService.batchDoCorect(mercAccount.getAccountNo(), successCharingCount);
							logger.info("successCharingCount:" + successCharingCount);
						}
					} catch (TradeException e) {
						logger.error(e.getMessage(), e);
					}
				}

			}
		}
	}
}