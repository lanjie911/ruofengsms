package com.sms.service.mercmanager;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sms.criteria.mercmanager.MercUserCriteria;
import com.sms.dao.mercmanager.MercUserDao;
import com.sms.entity.mercmanager.MercUser;
import com.sms.util.SecurityUtil;



@Service
public class MercUserService {

	@Autowired
	private MercUserDao mercUserDao;
	
	public List<MercUser> query(MercUserCriteria criteria) {
		return mercUserDao.query(criteria);
	}
	
	@Transactional
	public Map<String, Object> addMercUser(MercUser mercUser, Map<String, Object> result) {
		setMaskedPassword(mercUser);
		MercUser mercUserTemp = mercUserDao.getMercUserByMobile(mercUser.getMobile());
		if(mercUserTemp != null){
			result.put("success", false);
			result.put("message", "该手机号已存在。");
			return result;
		}
		
		Integer i =mercUserDao.insert(mercUser);
		if(i ==0){
			result.put("success", false);
			result.put("message", "插入商户基本信息失败");
			return result;
		}else{
			result.put("success", true);
			result.put("message", "添加成功");
		}
		return result;
	}
	
	public String setMaskedPassword(MercUser mercUser) {
		
		String selkey = mercUser.getOperatorLoginName();
		String sel = SecurityUtil.genSalt(selkey);
		String maskedPassword = SecurityUtil.calcMaskedPassword(sel, mercUser.getOperatorLoginName());
		mercUser.setOperatorLoginPassword(maskedPassword);
		return maskedPassword;
	}

	public MercUser getMercUserByMobile(Long mobile) {
		return mercUserDao.getMercUserByMobile(mobile);
	}

	public Map<String, Object> editMercUser(MercUser mercUser, Map<String, Object> result) {
		setMaskedPassword(mercUser);
		Integer i =mercUserDao.update(mercUser);
		if(i ==0){
			result.put("success", false);
			result.put("message", "更新商户用户信息失败");
			return result;
		}else{
			result.put("success", true);
			result.put("message", "更新成功");
		}
		return result;
	}

	public MercUser getMercUserById(Long operatorId) {
		return mercUserDao.getMercUserById(operatorId);
	}

	public Map<String, Object> resetPassword(Long operatorId, Map<String, Object> result) {
		MercUser user = getMercUserById(operatorId);
		setMaskedPassword(user);
		Integer i =mercUserDao.resetPassword(user);
		if(i ==0){
			result.put("success", false);
			result.put("message", "更新商户用户信息失败");
			return result;
		}else{
			result.put("success", true);
			result.put("message", "更新成功");
		}
		return result;
	}

	
}
