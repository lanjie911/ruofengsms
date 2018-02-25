package com.sms.service.send;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.sms.dao.AuditingDao;
import com.sms.entity.Auditing;
import com.sms.entity.MercAccount;
/**
 * @author Cao
 * 审核
 */
@Service
public class AuditingService {
	@Autowired
	private AuditingDao auditingDao;
	
	@Async
	public void insertToAudit(Integer orderFlag, String reservationDatetime,String messageId,String content,MercAccount mercAccount, Integer sendNum,String[] mobiles,String signTip){
		Auditing auditing = new Auditing(mercAccount.getAccountNo(), mercAccount.getMerchantNameAbbreviation(), mercAccount.getAccountType(), 
				orderFlag, reservationDatetime, messageId, null, content,content.length(), 1, sendNum, 100,signTip);
		auditingDao.insertBatch(mobiles, auditing);
	}
	
	@Async
	public void insertToAuditOne(Integer orderFlag, String reservationDatetime,String messageId,String content,MercAccount mercAccount, Integer sendNum,String mobile,String signTip){
		Auditing auditing = new Auditing(mercAccount.getAccountNo(), mercAccount.getMerchantNameAbbreviation(), mercAccount.getAccountType(), 
				orderFlag, reservationDatetime, messageId, mobile, content,content.length(), 1, sendNum, 100,signTip);
		auditingDao.insert(auditing);
	}
}
