package com.sms.service.smsmanager;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sms.criteria.smsmanager.SmsApplayCriteria;
import com.sms.dao.mercmanager.MercAccountDao;
import com.sms.dao.smsmanager.SmsApplayDao;
import com.sms.entity.smsmanager.SmsApplay;

@Service
public class SmsApplayService {
	
	private Logger logger = LoggerFactory.getLogger(SmsApplayService.class);

	@Autowired
	private SmsApplayDao smsApplayDao;
	
	@Autowired
	private MercAccountDao mercAccountDao;
	
	public List<SmsApplay> query(SmsApplayCriteria criteria){
		return smsApplayDao.query(criteria);
	}
	
	public SmsApplay getById(Integer smsApplayId){
		return smsApplayDao.getById(smsApplayId);
	}
	
	public void approve(Integer smsApplayId) throws Exception {
		SmsApplay newSmsApplay = smsApplayDao.getById(smsApplayId);
		if(null == newSmsApplay)
			throw new Exception("加载短信批量数据失败");
		frozenAmt(smsApplayId, newSmsApplay);
		int detailRow = smsApplayDao.updateSmsApplayDetailByBatchNo(newSmsApplay.getBatchNo(), 200, 101);
		logger.info("Approve-BatchNo:{},DetailRows:{}", newSmsApplay.getBatchNo(), detailRow);
	}
	
	@Transactional
	private void frozenAmt(Integer smsApplayId, SmsApplay newSmsApplay) throws Exception {
		int modifyRow = smsApplayDao.updateStatusByFrontCase(smsApplayId, 200, 101);
		if(modifyRow != 1)
			throw new RuntimeException("数据状态错误");
		int frozenCount = mercAccountDao.doFrozenAmt(newSmsApplay.getSuccCount(), newSmsApplay.getAccountNo());
		if(frozenCount == 0)
			throw new RuntimeException("商户账户可用余额不足");
	}
	
	public void reject(Integer smsApplayId){
		int modifyRow = smsApplayDao.updateStatusByFrontCase(smsApplayId, 300, 101);
		if(modifyRow != 1)
			throw new RuntimeException("数据状态错误");
		
		SmsApplay newSmsApplay = smsApplayDao.getById(smsApplayId);
		int detailRow = smsApplayDao.updateSmsApplayDetailByBatchNo(newSmsApplay.getBatchNo(), 300, 101);
		logger.info("Reject-BatchNo:{},DetailRowsL{}", newSmsApplay.getBatchNo(), detailRow);
		
	}
}