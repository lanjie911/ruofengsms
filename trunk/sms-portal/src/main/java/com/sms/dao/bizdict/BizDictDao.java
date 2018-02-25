package com.sms.dao.bizdict;

import java.util.List;

import com.sms.annotation.DataSource;
import com.sms.entity.bizdict.Bizdict;


public interface BizDictDao{
	
	@DataSource("portal")
	public List<Bizdict> getDirs(String dirType);
	@DataSource("portal")
	public List<Bizdict> getArea(String cityName);
}