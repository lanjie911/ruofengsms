package com.sms.dao.merccountrecord;

import java.util.List;

import com.sms.annotation.DataSource;
import com.sms.criteria.countrecord.ChannelCountRecordCriteria;
import com.sms.entity.merccountrecord.ChannelCountRecord;

public interface ChannelCountRecordDao { 
	@DataSource("trade")
	public List<ChannelCountRecord> query(ChannelCountRecordCriteria criteria);
	/*@DataSource("trade")
	public Integer insert(MercAccount mercAccount);
	@DataSource("trade")
	public MercAccount getMercAccountByAccountNo(Long mercAccountNo);
	@DataSource("trade")
	public Integer update(MercAccount mercAccount);
	@DataSource("trade")
	public Integer updateRrecharge(Recharge recharge);*/

}