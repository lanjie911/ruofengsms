package com.sms.dao.merccountrecord;

import java.util.List;

import com.sms.annotation.DataSource;
import com.sms.entity.merccountrecord.CountRecord;

public interface CountRecordDao { 
	@DataSource("trade")
	public List<CountRecord> getChannelRecord();
	@DataSource("trade")
	public List<CountRecord> getPlatformRecord();

}