package com.sms.dao.smsupload;

import org.apache.ibatis.annotations.Param;

import com.sms.annotation.DataSource;
import com.sms.entity.smsupload.SmsApplay;

public interface SmsApplayDao {

	@DataSource("trade")
	public int insert(SmsApplay smsApplay);
	
	@DataSource("trade")
	public SmsApplay getById(@Param("smsApplayId")Integer smsApplayId);
	
	@DataSource("trade")
	public int uploadFinish(SmsApplay smsApplay);
	
	@DataSource("trade")
	public int updateStatusByFrontStatus(@Param("smsApplayId")Integer smsApplayId, 
			@Param("newStatus")Integer newStatus, @Param("oldStatus")Integer oldStatus);
	
	@DataSource("trade")
	public int refreshMobileNum(@Param("batchNo")Long batchNo, 
			@Param("succCount")Integer succCount, @Param("outlierCount")Integer outlierCount);
}