package com.sms.service.merccountrecord;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sms.criteria.countrecord.ChannelCountRecordCriteria;
import com.sms.dao.merccountrecord.ChannelCountRecordDao;
import com.sms.entity.merccountrecord.ChannelCountRecord;

@Service
public class ChannelCountRecordService {

	@Autowired
	private ChannelCountRecordDao channelCountRecordDao;

	public List<ChannelCountRecord> query(ChannelCountRecordCriteria criteria) {
		return channelCountRecordDao.query(criteria);
	}

	/*@Transactional
	public Map<String, Object> addMercAccount(MercAccount mercAccount, Map<String, Object> result) {
		Integer i = mercAccountDao.insert(mercAccount);
		if (i == 0) {
			result.put("success", false);
			result.put("message", "插入商户账户失败");
			return result;
		}
		result.put("success", true);
		result.put("message", "添加成功");
		return result;
	}

	public MercAccount getMercAccountByAccountNo(Long mercAccountNo) {
		return mercAccountDao.getMercAccountByAccountNo(mercAccountNo);
	}

	public Map<String, Object> editMercAccount(MercAccount mercAccount, Map<String, Object> result) {
		Integer i = mercAccountDao.update(mercAccount);
		if (i == 0) {
			result.put("success", false);
			result.put("message", "更新商户基本信息失败");
			return result;
		}
		result.put("success", true);
		result.put("message", "添加成功");
		return result;
	}
*/
}
