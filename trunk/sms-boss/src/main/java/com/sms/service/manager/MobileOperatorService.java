package com.sms.service.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sms.criteria.systemParam.MobileOperatorCriteria;
import com.sms.dao.systemParam.MobileOperatorDao;
import com.sms.entity.systemParam.MobileOperator;

@Service
public class MobileOperatorService {

	@Autowired
	private MobileOperatorDao mobileOperatorDao;
	
	public MobileOperator getMobileOperatorById(Integer phoneOperatorId) {
		return mobileOperatorDao.getById(phoneOperatorId);
	}

	public void addMobileOperator(MobileOperator mobileOperator) {
		int prefNum = mobileOperatorDao.countPrefNum(mobileOperator.getPref());
		if(0 < prefNum)
			throw new RuntimeException("该手机号段已存在，请勿重复添加");
		mobileOperatorDao.insert(mobileOperator);	
	}

	public void editMobileOperator(MobileOperator mobileOperator) {
		int prefNum = mobileOperatorDao.countPrefNumByID(mobileOperator.getPref(), mobileOperator.getPhoneOperatorId());
		if(0 < prefNum)
			throw new RuntimeException("该手机号段已存在，请勿重复添加");
		mobileOperatorDao.update(mobileOperator);
	}

	public List<MobileOperator> query(MobileOperatorCriteria criteria) {
		return mobileOperatorDao.query(criteria);
	}
}