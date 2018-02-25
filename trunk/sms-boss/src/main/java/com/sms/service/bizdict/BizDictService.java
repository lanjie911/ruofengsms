package com.sms.service.bizdict;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sms.dao.bizdict.BizDictDao;
import com.sms.entity.bizdict.AccountType;
import com.sms.entity.bizdict.ApplayStatus;
import com.sms.entity.bizdict.BatchType;
import com.sms.entity.bizdict.Bizdict;
import com.sms.entity.bizdict.MobileOperatorType;
import com.sms.entity.bizdict.OrderFlag;

@Service
public class BizDictService {
	
	@Autowired
	private BizDictDao bizDictDao;
	
	public List<Bizdict> getDir(String dirType) {
		return bizDictDao.getDirs(dirType);
	}
	
	public List<Bizdict> getDirWithAll(String dirType) {
		List<Bizdict> dirs = bizDictDao.getDirs(dirType);
		List<Bizdict> lists = new ArrayList<>(); 
		Bizdict dir = new Bizdict();
		dir.setDirCode("");
		dir.setDirCodeDesc("全部");
		lists.add(dir);
		lists.addAll(dirs);
		return lists;
	}
	
	public List<Bizdict> getArea(String cityName) {
		return bizDictDao.getArea(cityName);
	}
	public List<BatchType> getBatchType(){
		return bizDictDao.getBatchType();
	}
	public List<AccountType> getAccountType(){
		return bizDictDao.getAccountType();
	}
	public List<OrderFlag> getOrderFlag(){
		return bizDictDao.getOrderFlag();
	}
	public List<ApplayStatus> getApplayStatus(){
		return bizDictDao.getApplayStatus();
	}
	public List<MobileOperatorType> getMobileOperatorType(){
		return bizDictDao.getMobileOperatorType();
	}
}