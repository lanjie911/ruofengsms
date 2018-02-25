package com.sms.dao.mercmanager;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sms.annotation.DataSource;
import com.sms.criteria.mercmanager.MercAccountCriteria;
import com.sms.entity.mercmanager.MercAccount;

public interface MercAccountDao {
	@DataSource("trade")
	public List<MercAccount> query(MercAccountCriteria criteria);

	@DataSource("trade")
	public MercAccount getMercAccountByAccountNo(Long mercAccountNo);

	@DataSource("trade")
	public Integer update(MercAccount mercAccount);

	@DataSource("trade")
	public MercAccount getMarketAccount(@Param("merchantId") long merchantId, @Param("accountType") long accountType);

	@DataSource("trade")
	public MercAccount getMerchantById(@Param("merchantId") long merchantId);

}