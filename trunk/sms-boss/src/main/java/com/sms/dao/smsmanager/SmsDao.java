package com.sms.dao.smsmanager;

import java.util.List;

import com.sms.annotation.DataSource;
import com.sms.criteria.smsmanager.SmsCriteria;
import com.sms.entity.smsmanager.Sms;

public interface SmsDao {
	
	@DataSource("trade")
	public List<Sms> query(SmsCriteria criteria);
	/*@DataSource("trade")
	public Channel getById(Long channelId);
	@DataSource("trade")
	public Integer update(Channel channel);
	@DataSource("trade")
	public List<Channel> getAllChannel();*/
}