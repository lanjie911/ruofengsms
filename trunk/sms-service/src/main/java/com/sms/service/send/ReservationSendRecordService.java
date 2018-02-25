package com.sms.service.send;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.sms.criteria.ReservationSendRecordCriteria;
import com.sms.dao.primarydao.PlainSendRecordDao;
import com.sms.dao.primarydao.ReservationSendRecordDao;
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

	public List<Map<String, Object>> queryResult(ReservationSendRecordCriteria reservationSendRecordCriteria) {
		return reservationSendRecordDao.queryResult(reservationSendRecordCriteria);
	}
	
	public Integer updateStatusByReport(String reqMsgId,String status,String respMsg,String respTime){
		return plainSendRecordDao.updateStatusByReport(reqMsgId, status, respMsg, respTime);
	}
	
	public PlainSendRecord getByreqMsgId(String reqMsgId){
		return plainSendRecordDao.getByreqMsgId(reqMsgId);
	}
	
	public int batchUpdaetSmsSucc(List<String> reqMsgIdArray) {
		return plainSendRecordDao.batchUpdaetSmsSucc(reqMsgIdArray);
	}
	
	public int batchUpdaetSmsFailure(List<String> reqMsgIdArray) {
		return plainSendRecordDao.batchUpdaetSmsFailure(reqMsgIdArray);
	}
	public int batchUpdate(List<PlainSendRecord> planList) {
		return plainSendRecordDao.batchUpdateByJuMeng(planList);
	}
}