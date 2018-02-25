package com.godai.trade.dao.smsupload;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.godai.trade.entity.smsupload.SmsApplayDetail;

public interface SmsApplayDetailDao {
	
	public SmsApplayDetail getById(@Param("applayDetailId")Integer applayDetailId);
	
	public List<SmsApplayDetail> loadApprovedByBatchNo(@Param("batchNo")Long batchNo,
			@Param("mobileOperator")Integer mobileOperator,
			@Param("limit")Integer limit, @Param("offerSet")Integer offerSet);
	
	public int batchUpdatePendding(List<SmsApplayDetail> list);
	
	public int loadApprovedNum(@Param("batchNo")Long batchNo);
}