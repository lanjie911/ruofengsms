package com.sms.dao.primarydao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sms.dao.IGenericDao;
import com.sms.entity.PlainSendRecord;

public interface PlainSendRecordDao extends IGenericDao<PlainSendRecord, Long>{

	public Integer insertBatch(@Param("strs")String[] strs,@Param("plainSendRecord")PlainSendRecord plainSendRecord);
	
	public PlainSendRecord getByreqMsgId(@Param("reqMsgId")String reqMsgId);
	
	public Integer updateStatusByReport(@Param("reqMsgId")String reqMsgId, @Param("status")String status, 
			@Param("respMsg")String respMsg,@Param("respTime")String respTime);
	
	public int batchUpdaetSmsSucc(@Param("reqMsgIdArray")List<String> reqMsgIdArray);
	
	public int batchUpdaetSmsFailure(@Param("reqMsgIdArray")List<String> reqMsgIdArray);
	
	public int batchUpdateByJuMeng(@Param("planList")List<PlainSendRecord> planList);
}