package com.sms.dao;

import java.util.List;

import com.sms.entity.CountChannel;

public interface CountChannelDao {

	public List<CountChannel> getCountChannel();

	public Long getPlatformSumData();
	
	public Long getPlatformSucData();
	
	public Long getPlatformFailData();
	
}
