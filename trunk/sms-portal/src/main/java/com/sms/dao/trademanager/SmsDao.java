package com.sms.dao.trademanager;

import java.util.List;

import com.sms.annotation.DataSource;
import com.sms.criteria.trademanager.SmsCriteria;
import com.sms.entity.trademanager.Sms;

public interface SmsDao{
	@DataSource("trade")
	List<Sms> query(SmsCriteria criteria);

}
