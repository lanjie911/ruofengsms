package com.sms.service.mercmanager;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sms.criteria.mercmanager.MercInfoCriteria;
import com.sms.dao.mercmanager.MercContactsDao;
import com.sms.dao.mercmanager.MercInfoDao;
import com.sms.entity.mercmanager.MercInfo;



@Service
public class MercInfoService {

	@Autowired
	private MercInfoDao mercInfoDao;
	@Autowired
	private MercContactsDao mercContactsDao;
	
	public List<MercInfo> query(MercInfoCriteria criteria) {
		return mercInfoDao.query(criteria);
	}
	
	@Transactional
	public Map<String, Object> addMerc(MercInfo mercInfo, Map<String, Object> result) {
		Integer i =mercInfoDao.insert(mercInfo);
		if(i ==0){
			result.put("success", false);
			result.put("message", "插入商户基本信息失败");
			return result;
		}else{
			mercInfo.getMercContacts().setMercInfoId(mercInfo.getMerchantId());
			Integer j=mercContactsDao.insert(mercInfo.getMercContacts());
			j += mercInfoDao.insertCusManager(mercInfo);
			if(j ==0){
				result.put("success", false);
				result.put("message", "插入商户联系人信息失败");
				return result;
			}
			result.put("success", true);
			result.put("message", "添加成功");
		}
		return result;
	}
	@Transactional
	public Map<String, Object> editMerc(MercInfo mercInfo, Map<String, Object> result) {
		Integer i =mercInfoDao.update(mercInfo);
		if(i ==0){
			result.put("success", false);
			result.put("message", "更新商户基本信息失败");
			return result;
		}else{
			mercInfo.getMercContacts().setMercInfoId(mercInfo.getMerchantId());
			Integer j=mercContactsDao.update(mercInfo.getMercContacts());
			mercInfoDao.deleteCusManager(mercInfo.getMerchantId());
			j += mercInfoDao.insertCusManager(mercInfo);
			if(j ==0){
				result.put("success", false);
				result.put("message", "更新商户联系人信息失败");
				return result;
			}
			result.put("success", true);
			result.put("message", "添加成功");
		}
		return result;
	}

	public MercInfo getMercInfoByMercId(Long merchantId) {
		return mercInfoDao.getMercInfoByMercId(merchantId);
	}

	public Integer getMercInfoCount() {
		return mercInfoDao.getMercInfoCount();
	}
	

	
}
