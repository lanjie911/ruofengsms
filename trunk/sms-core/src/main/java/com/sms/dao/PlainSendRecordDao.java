package com.sms.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sms.entity.PlainSendRecord;

public interface PlainSendRecordDao extends IGenericDao<PlainSendRecord, Long>{

	public int insertBatch(@Param("strs")String[] strs,@Param("plainSendRecord")PlainSendRecord plainSendRecord);
	
	public int updateInitStatusToNew(@Param("recordId")Long recordId, @Param("init")Integer init,@Param("status")Integer status);

	public int updateStatusByReport(@Param("reqMsgId")String reqMsgId,@Param("status")String status,@Param("respMsg")String respMsg,@Param("respTime")String respTime);
	
	public int insertBatchList(List<PlainSendRecord> list);
}
