package com.sms.dao.primarydao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sms.dao.IGenericDao;
import com.sms.entity.MercAccount;

public interface MercAccountDao extends IGenericDao<MercAccount, Long>{ 
	
	public MercAccount getMercAccountByMercId(@Param("merchantId")Long merchantId);

	
	public Integer frozenBalance(@Param("accountNo")Long accountNo, @Param("sendNum")Integer sendNum);

	
	public Integer unFrozenBalance(@Param("accountNo")Long accountNo, @Param("sendNum")Integer sendNum);
	
	
	public Integer doCorrect(@Param("accountNo")Long accountNo, @Param("sendNum")Integer sendNum);
	
	public List<MercAccount> qrymercAccountList();

}