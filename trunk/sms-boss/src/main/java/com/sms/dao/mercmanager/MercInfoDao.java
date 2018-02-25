package com.sms.dao.mercmanager;

import java.util.List;

import com.sms.annotation.DataSource;
import com.sms.criteria.mercmanager.MercInfoCriteria;
import com.sms.entity.mercmanager.MercInfo;

public interface MercInfoDao {
	@DataSource("trade")
	public List<MercInfo> query(MercInfoCriteria criteria);
	@DataSource("trade")
	public Integer insert(MercInfo mercInfo);
	@DataSource("trade")
	public Integer update(MercInfo mercInfo);
	@DataSource("trade")
	public MercInfo getMercInfoByMercId(Long merchantId);
	@DataSource("trade")
	public Integer getMercInfoCount();
	@DataSource("trade")
	public Integer insertCusManager(MercInfo mercInfo);
	@DataSource("trade")
	public Integer updateCusManager(MercInfo mercInfo);
	@DataSource("trade")
	public void deleteCusManager(Long merchantId);
}