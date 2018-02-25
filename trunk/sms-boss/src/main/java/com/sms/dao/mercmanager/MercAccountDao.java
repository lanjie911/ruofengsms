package com.sms.dao.mercmanager;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sms.annotation.DataSource;
import com.sms.criteria.mercmanager.MercAccountCriteria;
import com.sms.entity.fundsandaccount.Recharge;
import com.sms.entity.mercmanager.MercAccount;

public interface MercAccountDao { 
	@DataSource("trade")
	public List<MercAccount> query(MercAccountCriteria criteria);
	@DataSource("trade")
	public Integer insert(MercAccount mercAccount);
	@DataSource("trade")
	public MercAccount getMercAccountByAccountNo(Long mercAccountNo);
	@DataSource("trade")
	public Integer update(MercAccount mercAccount);
	@DataSource("trade")
	public Integer updateRrecharge(Recharge recharge);
	@DataSource("trade")
	public Integer doFrozenAmt(@Param("sumCost") Integer sumCost, @Param("accountNo") Long accountNo);
	
	@DataSource("trade")
	public Integer resetPassword(@Param("accountNo") Long accountNo, 
			@Param("password") String password, @Param("salt")  Integer salt,
			@Param("accountPwdClearText") String accountPwdClearText);

}