package com.sms.service.smsmanager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sms.criteria.smsmanager.SmsAuditCriteria;
import com.sms.dao.mercmanager.MercAccountDao;
import com.sms.dao.smsmanager.SmsAuditDao;
import com.sms.entity.mercmanager.MercAccount;
import com.sms.entity.smsmanager.SmsAudit;



@Service
public class SmsAuditService {

	@Autowired 
	private SmsAuditDao smsAuditDao;
	
	@Autowired 
	private MercAccountDao mercAccountDao;
	
	public List<SmsAudit> query(SmsAuditCriteria criteria) {
		return smsAuditDao.query(criteria);
	}

	@Transactional
	public Map<String, Object> smsaudit(String batchNo, Integer auditStatus,Integer beforeStatus,String operator) {
		Map<String, Object> result = new HashMap<>();
		Integer i = 0;
		i = smsAuditDao.updteStatus(batchNo,auditStatus,beforeStatus,operator);
		if(auditStatus ==200){
			List<SmsAudit> list =smsAuditDao.getByBatchNo(batchNo);
			if(list.size() >0){
				Long  accountNo  =list.get(0).getAccountNo();
				MercAccount mercAccount = mercAccountDao.getMercAccountByAccountNo(accountNo);
				Integer sumCost = 0;
				if(mercAccount.getCostQuantity()>0){
					sumCost =  (int) Math.floor(list.size() * mercAccount.getCostQuantity());
				}else{
					sumCost =  (int) Math.floor(list.size());
				}
				
				Integer j  = mercAccountDao.doFrozenAmt(sumCost,accountNo);
				if(j ==1){
					result.put("success", true);
					result.put("message", "审核成功");
				}else{
					result.put("success", false);
					result.put("message", "审核失败");
				}
			}
		}
		if(i==0){
			result.put("success", false);
			result.put("message", "审核失败");
		}else{
			result.put("success", true);
			result.put("message", "审核成功");
		}
		return result;
	}

}
