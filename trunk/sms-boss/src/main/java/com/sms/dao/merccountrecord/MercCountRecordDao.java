package com.sms.dao.merccountrecord;

import java.util.List;

import com.sms.annotation.DataSource;
import com.sms.criteria.countrecord.MercCountRecordCriteria;
import com.sms.entity.merccountrecord.MercCountRecord;

public interface MercCountRecordDao { 
	@DataSource("trade")
	public List<MercCountRecord> query(MercCountRecordCriteria criteria);
	/*@DataSource("trade")
	public Integer insert(MercAccount mercAccount);
	@DataSource("trade")
	public MercAccount getMercAccountByAccountNo(Long mercAccountNo);
	@DataSource("trade")
	public Integer update(MercAccount mercAccount);
	@DataSource("trade")
	public Integer updateRrecharge(Recharge recharge);*/

}