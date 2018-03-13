package com.sms.service;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;

import javax.annotation.PostConstruct;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sms.dto.IReturnSMS;
import com.sms.dto.MsgBox;
import com.sms.entity.MercAccount;
import com.sms.entity.PlainSendRecord;
import com.sms.entity.PlainSendResp;
import com.sms.service.executor.MyExecutor;
import com.sms.service.send.MercAccountService;
import com.sms.service.send.PlainSendRespService;
import com.sms.service.send.ReservationSendRecordService;
import com.sms.util.TradeException;

@Service
public class AbstractCallbackService {

	protected Logger logger = null;

	@Autowired
	protected ReservationSendRecordService reservationSendRecordService;
	
	@Autowired
	protected PlainSendRespService plainSendRespService;

	@Autowired
	protected PrepareParamService prepareParamService;

	@Autowired
	protected MercAccountService mercAccountService;

	@Autowired
	protected MyExecutor myExecutor;

	protected ExecutorService dealRespExec = null;

	@PostConstruct
	protected void init() {
		this.logger = LoggerFactory.getLogger(this.getClass());
		dealRespExec = myExecutor.getExecutors();
	}

	@SuppressWarnings({ "rawtypes" })
	protected Class getEntityClass() {
		return null;
	}

	public void prepareRespData(String reponseStr) {
		dealRespExec.submit(new Runnable() {
			@Override
			public void run() {
				try {
					dealSms(reponseStr);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}
		});
	}

	// 子类做的事情
	protected void dealSms(final String responseStr) {
		try {

			logger.info("=----->,{}", this.getClass().getCanonicalName());

			JAXBContext ctx = JAXBContext.newInstance(this.getEntityClass());

			Unmarshaller marchaller = ctx.createUnmarshaller();
			IReturnSMS returnsms = (IReturnSMS) marchaller.unmarshal(new StringReader(responseStr));

			List<MsgBox> list = returnsms.getStatusbox();
			if (list.isEmpty() || list.size() == 0) {
				return;
			}

			PlainSendRecord plainSendRecord = reservationSendRecordService.getByreqMsgId(list.get(0).getTaskid());
			if (null != plainSendRecord) {
				MercAccount mercAccount = prepareParamService.getMercAccount(plainSendRecord.getAccountNo());
				if (null != mercAccount) {

					// 发送成功后需要解冻的数量
					int successCount = 0;
					// 发送失败后提交量扣费方式需要解冻的数量
					int submitCharingCount = 0;
					// 发送失败后成功量扣费方式需要冲正的数量
					int successCharingCount = 0;
					int count = 0;
//					List<PlainSendRecord> planList = new ArrayList<PlainSendRecord>();
					List<PlainSendResp> respList = new ArrayList<PlainSendResp>();
					int sendStatus, failedNum;
					PlainSendResp tmpBean = null;
					for (MsgBox statusbox : list) {
						sendStatus = statusbox.getStatus().equals("10") ? 500 : 300;
						failedNum = statusbox.getStatus().equals("10") ? 0 : 1;
						tmpBean = new PlainSendResp(statusbox.getTaskid(),statusbox.getMobile(), sendStatus, statusbox.getErrorcode(),
								 statusbox.getReceivetime());
						count = plainSendRespService.insert(tmpBean);
						if (count > 0) {
							if ("10".equals(statusbox.getStatus())) {
								count ++;
								successCount++;

							} else {
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
					}
//					int count = reservationSendRecordService.batchUpdate(planList);
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
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
	}
}
