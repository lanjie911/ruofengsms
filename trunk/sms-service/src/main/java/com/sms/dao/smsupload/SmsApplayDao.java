package com.sms.dao.smsupload;

import org.apache.ibatis.annotations.Param;

import com.sms.entity.smsupload.SmsApplay;

public interface SmsApplayDao {

	public int insert(SmsApplay smsApplay);

	public SmsApplay getById(@Param("smsApplayId") Integer smsApplayId);

	public int uploadFinish(SmsApplay smsApplay);

	public int updateStatusByFrontStatus(@Param("smsApplayId") Integer smsApplayId,
			@Param("newStatus") Integer newStatus, @Param("oldStatus") Integer oldStatus);

	public int refreshMobileNum(@Param("batchNo") Long batchNo, @Param("succCount") Integer succCount,
			@Param("outlierCount") Integer outlierCount);
}