package com.sms.dao.mercmanager;

import java.util.List;

import com.sms.annotation.DataSource;
import com.sms.criteria.mercmanager.MercUserCriteria;
import com.sms.entity.mercmanager.MercUser;

public interface MercUserDao {
	@DataSource("protal")
	public List<MercUser> query(MercUserCriteria criteria);
	@DataSource("protal")
	public Integer insert(MercUser mercUser);
	@DataSource("protal")
	public MercUser getMercUserByMobile(Long mobile);
	@DataSource("protal")
	public Integer update(MercUser mercUser);
	@DataSource("protal")
	public MercUser getMercUserById(Long operatorId);
	@DataSource("protal")
	public Integer resetPassword(MercUser user);
}