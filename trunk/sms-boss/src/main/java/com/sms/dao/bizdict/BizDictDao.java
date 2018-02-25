package com.sms.dao.bizdict;

import java.util.List;

import com.sms.annotation.DataSource;
import com.sms.entity.bizdict.AccountType;
import com.sms.entity.bizdict.ApplayStatus;
import com.sms.entity.bizdict.BatchType;
import com.sms.entity.bizdict.Bizdict;
import com.sms.entity.bizdict.MobileOperatorType;
import com.sms.entity.bizdict.OrderFlag;

public interface BizDictDao {
	@DataSource("boss")
	public List<Bizdict> getDirs(String dirType);
	@DataSource("boss")
	public List<Bizdict> getArea(String cityName);
	@DataSource("trade")
	public List<BatchType> getBatchType();
	@DataSource("trade")
	public List<AccountType> getAccountType();
	@DataSource("trade")
	public List<OrderFlag> getOrderFlag();
	@DataSource("trade")
	public List<ApplayStatus> getApplayStatus();
	@DataSource("trade")
	public List<MobileOperatorType> getMobileOperatorType();
}