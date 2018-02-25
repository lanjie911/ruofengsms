package com.sms.service.fundsandaccount;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sms.criteria.fundsandaccount.RechargeCriteria;
import com.sms.dao.fundsandaccount.RechargeDao;
import com.sms.dao.mercmanager.MercAccountDao;
import com.sms.entity.fundsandaccount.Recharge;



@Service
public class RechargeService {

	@Autowired 
	private RechargeDao rechargeDao;
	@Autowired 
	private MercAccountDao mercAccountDao;
	
	public List<Recharge> query(RechargeCriteria criteria) {
		return rechargeDao.query(criteria);
	}

	public Recharge getRechargeById(Long depositId) {
		return rechargeDao.getById(depositId);
	}


}
