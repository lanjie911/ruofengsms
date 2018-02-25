package com.sms.service.send;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.sms.dao.PlainSendRecordDao;
import com.sms.entity.MercAccount;
import com.sms.entity.PlainSendRecord;
/**
 * @author Cao
 * 预约短信
 */
@Service
public class PlainSendRecordService {
	
	@Autowired
	private PlainSendRecordDao plainSendRecordDao;

	@Async
	public void insertCost(String content,String messageId, MercAccount mercAccount, String mobile){
		PlainSendRecord plainSendRecord = new PlainSendRecord(mercAccount.getSmsGroupId(),mercAccount.getSmsGroupDesc(),mercAccount.getAccountNo(), mercAccount.getMerchantNameAbbreviation(), mercAccount.getAccountType(), 
				mercAccount.getFailToReissueFlag(), mobile, content, 200, null, null, null,messageId,null,null);
		plainSendRecordDao.insert(plainSendRecord);
	}
	
	public Integer updateInitStatusToNew(Long recordId,Integer init,Integer status){
		return plainSendRecordDao.updateInitStatusToNew(recordId,init,status);
	}

}
