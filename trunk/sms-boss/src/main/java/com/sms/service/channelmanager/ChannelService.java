package com.sms.service.channelmanager;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sms.criteria.channelmanager.ChannelCriteria;
import com.sms.dao.channelmanager.ChannelDao;
import com.sms.entity.channelmanager.Channel;

@Service
public class ChannelService {

	@Autowired 
	private ChannelDao channelDao;
	
	public List<Channel> query(ChannelCriteria criteria) {
		return channelDao.query(criteria);
	}

	@Transactional
	public Map<String, Object>addChannel(Channel channel, Map<String, Object> result) {
		Integer i =channelDao.insert(channel);
		if(i ==0){
			result.put("success", false);
			result.put("message", "插入通道信息失败");
			return result;
		}
		result.put("success", true);
		result.put("message", "添加成功");
		return result;
	}

	public  Channel getChannelByChannelId(Long channelId) {
		return channelDao.getById(channelId);
	}
	public Map<String, Object> editChannel(Channel channel, Map<String, Object> result) {
		Integer i =channelDao.update(channel);
		if(i ==0){
			result.put("success", false);
			result.put("message", "更新通道失败");
			return result;
		}
		result.put("success", true);
		result.put("message", "添加成功");
		return result;
	}

	public List<Channel> getChannelByAttr(Long attr) {
		return channelDao.getChannelByAttr(attr);
	}

	public List<Channel> getAllChannel() {
		return channelDao.getAllChannel();
	}

	public List<Channel> getChannelByAttrs(String accountType, String supportOperators) {
		return channelDao.getChannelByAttrs(accountType,supportOperators);
	}
}