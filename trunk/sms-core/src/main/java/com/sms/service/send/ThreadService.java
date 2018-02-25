package com.sms.service.send;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sms.dao.PlainSendRecordDao;
import com.sms.entity.MercAccountLs;
import com.sms.entity.PlainSendRecord;

/**
 * @author Cao
 * 线程
 */
@Service
public class ThreadService{
	
	private static final Logger logger = LoggerFactory.getLogger(ThreadService.class);

	@Autowired
	private MercAccountLsService mercAccountLsService;
	@Autowired
	private ThreadJobExecutor threadJobExecutor;
	@Autowired
	private PlainSendRecordDao plainSendRecordDao;
	
	public void genFrozenLs(final Integer transType,final Long accountNo,final Integer sendNum,final String mobile) {
		
		Runnable r = new Runnable() {
			@Override
			public void run() {
				try {
					MercAccountLs mercAccountLs = new MercAccountLs(transType, accountNo, sendNum, mobile);
					mercAccountLsService.insert(mercAccountLs);
				} catch (Exception e) {
					logger.error("genReportDate in Runnable,Thread Id :" + Thread.currentThread().getId());
					e.printStackTrace();
				}
			}
		};
		threadJobExecutor.execute(r);
	}
	
	public void genPlanrecord(final String[] strs, final PlainSendRecord plainSendRecord) {
			
		Runnable r = new Runnable() {
			@Override
			public void run() {
				try {
					plainSendRecordDao.insertBatch(strs, plainSendRecord);							//生成伪发送成功记录
				} catch (Exception e) {
					logger.error("genReportDate in Runnable,Thread Id :" + Thread.currentThread().getId());
					e.printStackTrace();
				}
			}
		};
		threadJobExecutor.execute(r);
	}
}
