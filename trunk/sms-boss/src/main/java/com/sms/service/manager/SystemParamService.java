package com.sms.service.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sms.criteria.systemParam.SystemParamCriteria;
import com.sms.dao.systemParam.SystemParamDao;
import com.sms.entity.systemParam.SystemParam;

@Service
public class SystemParamService {

	@Autowired
	private SystemParamDao systemParamDao;
	
	public SystemParam getParamById(Long paramid) {
		return systemParamDao.getById(paramid);
	}

	public void addParam(SystemParam param) {
		SystemParam temp = systemParamDao.getSystemParamByCode(param.getParamCode());
		if(null != temp)
			throw new RuntimeException("参数编码已被使用");
		systemParamDao.insert(param);	
	}

	public void editParam(SystemParam param) {
		int existisNum = systemParamDao.volidataParamCode(param.getParamCode(), param.getParamId());
		if(existisNum > 0)
			throw new RuntimeException("参数编码已被使用");
		systemParamDao.update(param);
	}

	public List<SystemParam> query(SystemParamCriteria criteria) {
		return systemParamDao.query(criteria);
	}

	public SystemParam getSystemParamByCode(String paramCode) {
		return systemParamDao.getSystemParamByCode(paramCode);
	}
}