package com.sms.dao.channelmanager;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sms.annotation.DataSource;
import com.sms.criteria.channelmanager.ChannelCriteria;
import com.sms.entity.channelmanager.Channel;

public interface ChannelDao {
	@DataSource("trade")
	public List<Channel> query(ChannelCriteria criteria);
	
	@DataSource("trade")
	public Integer insert(Channel channel);
	
	@DataSource("trade")
	public Channel getById(Long channelId);
	
	@DataSource("trade")
	public Integer update(Channel channel);
	
	@DataSource("trade")
	public List<Channel> getChannelByAttr(Long attr);
	
	@DataSource("trade")
	public List<Channel> getAllChannel();

	@DataSource("trade")
	public List<Channel> getChannelByAttrs(@Param("accountType")String accountType,@Param("supportOperators") String supportOperators);
}