package com.sms.service.mercmanager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sms.criteria.mercmanager.CusManagerCriteria;
import com.sms.dao.mercmanager.CusManagerDao;
import com.sms.entity.mercmanager.CusManager;



@Service
public class CusManagerService {

	@Autowired 
	private CusManagerDao cusManagerDao;
	
	public List<CusManager> query(CusManagerCriteria criteria) {
		return cusManagerDao.query(criteria);
	}
	
	public Map<String, Object> addCusManager(CusManager cusManager, Map<String, Object> result) {
		Integer i =cusManagerDao.insert(cusManager);
		if(i ==0){
			result.put("success", false);
			result.put("message", "插入客户经理信息失败");
			return result;
		}
		result.put("success", true);
		result.put("message", "添加成功");
		return result;
	}
	
	public CusManager getCusmanagerById(Long cusManagerId) {
		return cusManagerDao.getCusmanagerById(cusManagerId);
	}
	public Map<String, Object> editCusmanager(CusManager cusManager, Map<String, Object> result) {
		Integer i =cusManagerDao.update(cusManager);
		if(i ==0){
			result.put("success", false);
			result.put("message", "更新商户基本信息失败");
			return result;
		}
		result.put("success", true);
		result.put("message", "添加成功");
		return result;
	}
	public  Map<String, Object> delete(Long cusManagerId) {
		Map<String, Object> result = new HashMap<>();
		Integer i =  cusManagerDao.delete(cusManagerId);
		if(i==0){
			result.put("success", false);
			result.put("message", "删除客户经理失败");
		}else{
			result.put("success", true);
			result.put("message", "删除客户经理失成功");
		}
		return result;
	}

	public List<CusManager> getCusmanagers() {
		return cusManagerDao.getCusmanagers();
	}

	public List<CusManager> querymerc(CusManagerCriteria criteria) {
		return cusManagerDao.querymerc(criteria);
	}


}
