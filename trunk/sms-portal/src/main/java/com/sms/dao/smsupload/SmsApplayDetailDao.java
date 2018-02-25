package com.sms.dao.smsupload;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sms.annotation.DataSource;
import com.sms.entity.smsupload.SmsApplayDetail;

public interface SmsApplayDetailDao {

	@DataSource("trade")
	public int insert(SmsApplayDetail smsApplayDetail);
	
	@DataSource("trade")
	public SmsApplayDetail getById(@Param("applayDetailId")Integer applayDetailId);
	
	@DataSource("trade")
	public int updateStatusByFrontStatus(@Param("applayDetailId")Integer applayDetailId,
			@Param("newStatus")Integer newStatus,
			@Param("oldStatus")Integer oldStatus);
	
	@DataSource("trade")
	public int insertBatch(List<SmsApplayDetail> list);
	
	@DataSource("trade")
	public List<SmsApplayDetail> loadLimitByBatchNo(@Param("batchNo")Long batchNo, 
			@Param("limit")Integer limit, @Param("offerSet")Integer offerSet);
	
	@DataSource("trade")
	public int uploadFinish(@Param("batchNo")Long batchNo);
}