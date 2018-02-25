package com.sms.dao;

import java.util.List;

import com.sms.entity.CountRecord;

public interface CountRecordDao { 
	
	public List<CountRecord> getRecord(String columnType,String dataType);
	
	public void insert(CountRecord countRecord);

}