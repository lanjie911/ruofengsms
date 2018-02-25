package com.godai.trade.dao.smsupload;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.godai.trade.entity.smsupload.SmsApplay;

public interface SmsApplayDao {
	
	public SmsApplay getById(@Param("smsApplayId")Integer smsApplayId);
	
	public List<SmsApplay> loadNormalApprove();
	
	public int setDealed(@Param("smsApplayId")Integer smsApplayId);
}