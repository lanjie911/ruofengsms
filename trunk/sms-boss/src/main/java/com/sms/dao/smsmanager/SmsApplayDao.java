package com.sms.dao.smsmanager;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sms.annotation.DataSource;
import com.sms.criteria.smsmanager.SmsApplayCriteria;
import com.sms.entity.smsmanager.SmsApplay;

public interface SmsApplayDao {
	
	@DataSource("trade")
	public List<SmsApplay> query(SmsApplayCriteria criteria);
	
	@DataSource("trade")
	public SmsApplay getById(@Param("smsApplayId")Integer smsApplayId);
	
	@DataSource("trade")
	public int updateStatusByFrontCase(@Param("smsApplayId")Integer smsApplayId, 
			@Param("newStatus")Integer newStatus, @Param("oldStatus")Integer oldStatus);
	
	@DataSource("trade")
	public int updateSmsApplayDetailByBatchNo(@Param("batchNo")Long batchNo, 
			@Param("detailStatus")Integer detailStatus, @Param("oldDetailStatus")Integer oldDetailStatus);
}