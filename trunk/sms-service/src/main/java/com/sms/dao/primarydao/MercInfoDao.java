package com.sms.dao.primarydao;

import java.util.List;

import com.sms.criteria.MercInfoCriteria;
import com.sms.entity.MercInfo;

public interface MercInfoDao {
	public List<MercInfo> query(MercInfoCriteria criteria);
	public Integer insert(MercInfo mercInfo);
	public Integer update(MercInfo mercInfo);
	public MercInfo getMercInfoByMercId(Long merchantId);
	public Integer getMercInfoCount();
}