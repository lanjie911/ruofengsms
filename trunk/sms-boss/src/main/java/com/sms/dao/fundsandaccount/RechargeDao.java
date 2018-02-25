package com.sms.dao.fundsandaccount;

import java.util.List;

import com.sms.annotation.DataSource;
import com.sms.criteria.fundsandaccount.RechargeCriteria;
import com.sms.entity.fundsandaccount.Recharge;

public interface RechargeDao{
	@DataSource("trade")
	List<Recharge> query(RechargeCriteria criteria);
	@DataSource("trade")
	Integer insert(Recharge recharge);
	@DataSource("trade")
	Recharge getById(Long depositId);


}
