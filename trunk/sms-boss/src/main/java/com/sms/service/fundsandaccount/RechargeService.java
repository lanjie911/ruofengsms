package com.sms.service.fundsandaccount;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

	@Transactional
	public Map<String, Object> addRecharge(Recharge recharge, Map<String, Object> result) {
		Integer i =mercAccountDao.updateRrecharge(recharge);
		if(i ==0){
			result.put("success", false);
			result.put("message", "更新商户账户余额失败");
			return result;
		}else{
			Integer j =rechargeDao.insert(recharge);
			if(j==0){
				result.put("success", false);
				result.put("message", "添加商户充值记录失败");
				return result;
			}
		}
		result.put("success", true);
		result.put("message", "添加成功");
		return result;
	}
	
	public Recharge getRechargeById(Long depositId) {
		return rechargeDao.getById(depositId);
	}


}
