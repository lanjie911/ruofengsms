package com.sms.dao.smsupload;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sms.entity.smsupload.SmsApplayDetail;

public interface SmsApplayDetailDao {

	public int insert(SmsApplayDetail smsApplayDetail);
	
	public SmsApplayDetail getById(@Param("applayDetailId")Integer applayDetailId);
	
	public int updateStatusByFrontStatus(@Param("applayDetailId")Integer applayDetailId,
			@Param("newStatus")Integer newStatus,
			@Param("oldStatus")Integer oldStatus);
	
	public int insertBatch(List<SmsApplayDetail> list);
	
	public List<SmsApplayDetail> loadLimitByBatchNo(@Param("batchNo")Long batchNo, 
			@Param("limit")Integer limit, @Param("offerSet")Integer offerSet);
	
	public int uploadFinish(@Param("batchNo")Long batchNo);
}