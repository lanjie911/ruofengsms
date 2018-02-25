package com.sms.service.merccountrecord;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sms.dao.merccountrecord.CountRecordDao;
import com.sms.entity.merccountrecord.CountRecord;

@Service
public class CountRecordService {

	@Autowired
	private CountRecordDao countRecordDao;

	public List<CountRecord> getChannelRecord() {
		return countRecordDao.getChannelRecord();
	}
	
	public List<CountRecord> getPlatformRecord() {
		return countRecordDao.getPlatformRecord();
	}
}
