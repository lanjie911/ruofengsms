package com.sms.service.bizdict;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sms.criteria.CustomParamCriteria;
import com.sms.dao.bizdict.CustomParamDao;
import com.sms.entity.bizdict.CustomParam;



@Service
public class CustomParamService {

	@Autowired
	CustomParamDao customParamDao;

	public CustomParam getParamById(Long paramid) {
		return customParamDao.getById(paramid);
	}
	public Map<String, Object> addParam(CustomParam param, Map<String, Object> result) {
		CustomParam temp =customParamDao.getCustomParamByCode(param.getParamCode()) ;
		if(null != temp){
			result.put("success", false);
			result.put("message", "the record has been exists");
		}else{
			customParamDao.insert(param);
			result.put("success", true);
			result.put("message", "Add param successfully.");
		}
		return result;
	}

	public Map<String, Object> editParam(CustomParam param, Map<String, Object> result) {
		
		CustomParam temp =customParamDao.getCustomParamByCode(param.getParamCode()) ;
		if(null != temp){
			result.put("success", false);
			result.put("message", "the record has been exists");
		}else{
			customParamDao.update(param);
			result.put("success", true);
			result.put("message", "edit param successfully.");
		}
		return result;
		
	}

	public List<CustomParam> query(CustomParamCriteria criteria) {
		return customParamDao.query(criteria);
	}
	
	public CustomParam getCustomParamByCode(String paramCode ){
		return customParamDao.getCustomParamByCode(paramCode);
	}

}
