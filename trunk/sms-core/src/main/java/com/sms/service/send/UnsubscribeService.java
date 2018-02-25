package com.sms.service.send;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sms.dao.UnsubscribeDao;
import com.sms.entity.Unsubscribe;
import com.sms.entity.White;
import com.sms.service.PrepareParamService;

/**
 * @author Cao
 * 黑名单与退订
 */
@Service
public class UnsubscribeService {
	
	@Autowired
	private UnsubscribeDao unsubscribeDao;
	
	@Autowired
	private PrepareParamService prepareParamService;

	public boolean checkIfInBlackList(String mobile){
		try {
			Unsubscribe unsubscribe = prepareParamService.getUnsubscribe(mobile);
			if(null == unsubscribe)
				return false;
			White white = prepareParamService.getWhite(mobile);
			if(null == white)
				return true;
		} catch (Exception e) {
		}
		return false;
	}

	public String insert(Unsubscribe unsubscribe) {
		Integer count = unsubscribeDao.insert(unsubscribe);
		return count.toString();
	}

	public String update(Unsubscribe unsubscribe) {
		Integer count = unsubscribeDao.update(unsubscribe);
		return count.toString();
	}
	
	public Unsubscribe getByMobile(String mobile) {
		return unsubscribeDao.getByMobile(mobile);
	}
}
