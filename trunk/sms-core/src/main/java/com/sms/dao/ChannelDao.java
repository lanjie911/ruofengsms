package com.sms.dao;

import com.sms.dao.IGenericDao;
import com.sms.entity.Channel;

public interface ChannelDao extends IGenericDao<Channel, Long>{
	
	public Channel getMaxLevelChannel();

}
