package com.sms.service.send;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sms.dao.MercAccountLsDao;
import com.sms.entity.MercAccountLs;

/**
 * @author Cao
 * 账户流水
 */
@Service
public class MercAccountLsService {

	@Autowired
	private MercAccountLsDao mercAccountLsDao;
	
	public Integer insert(MercAccountLs mercAccountLs){
		return mercAccountLsDao.insert(mercAccountLs);
	}

	public Integer insertBatch(MercAccountLs mercAccountLs, String[] mobiles) {
		return mercAccountLsDao.insertBatch(mercAccountLs,mobiles);
	}
}
