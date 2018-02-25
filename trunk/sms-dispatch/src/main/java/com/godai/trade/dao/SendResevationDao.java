package com.godai.trade.dao;

import java.util.List;
import java.util.Map;

import com.godai.trade.entity.Sms;


public interface SendResevationDao{
	
	public List<Sms> getNeedResevation(Map<String, Object> parmas);
	
	public List<Sms> getNeedCredit();

}
