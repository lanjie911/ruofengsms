package com.sms.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sms.criteria.WhiteCriteria;
import com.sms.dao.primarydao.WhiteDao;
import com.sms.entity.White;



@Service
public class WhiteService {

	@Autowired 
	private WhiteDao whiteDao;
	
	public List<White> query(WhiteCriteria criteria) {
		return whiteDao.query(criteria);
	}

	public White getWhiteByMobile(Long mobile) {
		return whiteDao.getByMobile(mobile);
	}
	
	public Map<String, Object> editWhite(White white, Map<String, Object> result) {
		Integer i =  whiteDao.update(white);
		if(i==0){
			result.put("success", false);
			result.put("message", "修改失败");
		}else{
			result.put("success", true);
			result.put("message", "修改成功");
		}
		return result;
	}


	public Map<String, Object> delete(Long whiteId, Map<String, Object> result) {
		
		Integer i =  whiteDao.delete(whiteId);
		if(i==0){
			result.put("success", false);
			result.put("message", "修改失败");
		}else{
			result.put("success", true);
			result.put("message", "修改成功");
		}
		return result;
	}
}
