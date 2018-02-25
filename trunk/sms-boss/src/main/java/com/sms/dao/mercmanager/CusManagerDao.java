package com.sms.dao.mercmanager;

import java.util.List;

import com.sms.annotation.DataSource;
import com.sms.criteria.mercmanager.CusManagerCriteria;
import com.sms.entity.mercmanager.CusManager;

public interface CusManagerDao { 
	@DataSource("trade")
	public List<CusManager> query(CusManagerCriteria criteria);
	@DataSource("trade")
	public Integer insert(CusManager cusManager);
	@DataSource("trade")
	public CusManager getCusmanagerById(Long cusManagerId);
	@DataSource("trade")
	public Integer update(CusManager cusManager);
	@DataSource("trade")
	public Integer delete(Long cusManagerId);
	@DataSource("trade")
	public List<CusManager> getCusmanagers();
	@DataSource("trade")
	public List<CusManager> querymerc(CusManagerCriteria criteria);

}