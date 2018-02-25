package com.sms.service.merccountrecord;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sms.criteria.countrecord.MercCountRecordCriteria;
import com.sms.dao.merccountrecord.MercCountRecordDao;
import com.sms.entity.merccountrecord.MercCountRecord;

@Service
public class MercCountRecordService {

	@Autowired
	private MercCountRecordDao mercCountRecordDao;

	public List<MercCountRecord> query(MercCountRecordCriteria criteria) {
		return mercCountRecordDao.query(criteria);
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
