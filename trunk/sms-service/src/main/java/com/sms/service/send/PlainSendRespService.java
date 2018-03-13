package com.sms.service.send;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.sms.criteria.ReservationSendRecordCriteria;
import com.sms.dao.primarydao.PlainSendRecordDao;
import com.sms.dao.primarydao.PlainSendRespDao;
import com.sms.dao.primarydao.ReservationSendRecordDao;
import com.sms.entity.MercAccount;
import com.sms.entity.PlainSendRecord;
import com.sms.entity.PlainSendResp;
import com.sms.entity.ReservationSendRecord;
/**
 * @author Cao
 * 短信回调
 */
@Service
public class PlainSendRespService {
	
	@Autowired
	private PlainSendRespDao plainSendRespDao;
	
	public Integer insert(PlainSendResp plainSendResp){
		return plainSendRespDao.insert(plainSendResp);
	}


}