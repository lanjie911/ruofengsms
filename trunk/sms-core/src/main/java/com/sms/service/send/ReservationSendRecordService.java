package com.sms.service.send;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.sms.criteria.ReservationSendRecordCriteria;
import com.sms.dao.PlainSendRecordDao;
import com.sms.dao.ReservationSendRecordDao;
import com.sms.entity.MercAccount;
import com.sms.entity.PlainSendRecord;
import com.sms.entity.ReservationSendRecord;
/**
 * @author Cao
 * 预约短信
 */
@Service
public class ReservationSendRecordService {
	
	@Autowired
	private ReservationSendRecordDao reservationSendRecordDao;
	
	@Autowired
	private PlainSendRecordDao plainSendRecordDao;

	@Async
	public void insertReservationMsg(String content,String messageId,String reservationDateTime, MercAccount mercAccount, String[] mobiles,String signTip){
		ReservationSendRecord reservationSendRecord = new ReservationSendRecord(mercAccount.getSmsGroupId(),mercAccount.getSmsGroupDesc(),mercAccount.getAccountNo(), mercAccount.getMerchantNameAbbreviation(), mercAccount.getAccountType(), 
				mercAccount.getFailToReissueFlag(), null, content, reservationDateTime, 100, messageId,signTip);
		reservationSendRecordDao.insertBatch(mobiles, reservationSendRecord);
	}
	
	@Async
	public void insertReservationMsgOne(String content,String messageId,String reservationDateTime, MercAccount mercAccount, String mobile,String signTip){
		ReservationSendRecord reservationSendRecord = new ReservationSendRecord(mercAccount.getSmsGroupId(),mercAccount.getSmsGroupDesc(),mercAccount.getAccountNo(), mercAccount.getMerchantNameAbbreviation(), mercAccount.getAccountType(), 
				mercAccount.getFailToReissueFlag(), mobile, content, reservationDateTime, 100, messageId,signTip);
		reservationSendRecordDao.insert(reservationSendRecord);
	}
	
	@Async
	public void insertCost(String content,String messageId,String reservationDateTime, MercAccount mercAccount, String mobile,String signTip){
		ReservationSendRecord reservationSendRecord = new ReservationSendRecord(mercAccount.getSmsGroupId(),mercAccount.getSmsGroupDesc(),mercAccount.getAccountNo(), mercAccount.getMerchantNameAbbreviation(), mercAccount.getAccountType(), 
				mercAccount.getFailToReissueFlag(), mobile, content, reservationDateTime, 200, messageId, signTip);
		reservationSendRecordDao.insert(reservationSendRecord);
	}
	
	public void insertCostMsg(String content,String messageId,String reservationDateTime, MercAccount mercAccount, String mobile,String signTip){
		PlainSendRecord plainSendRecord = new PlainSendRecord(-1l, "f", mercAccount.getAccountNo(), mercAccount.getMerchantNameAbbreviation(), mercAccount.getAccountType(), mercAccount.getFailToReissueFlag(), mobile, content, 500, "发送成功", null, null,messageId, reservationDateTime, signTip);
		plainSendRecordDao.insert(plainSendRecord);
	}

	public List<Map<String, Object>> queryResult(ReservationSendRecordCriteria reservationSendRecordCriteria) {
		return reservationSendRecordDao.queryResult(reservationSendRecordCriteria);
	}
	
	public Integer updateInitStatusToNew(Integer recordId,Integer init,Integer status){
		return reservationSendRecordDao.updateInitStatusToNew(recordId,init,status);
	}
}
