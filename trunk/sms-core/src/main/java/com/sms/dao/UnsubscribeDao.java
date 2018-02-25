package com.sms.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sms.entity.Unsubscribe;

public interface UnsubscribeDao extends IGenericDao<Unsubscribe, Long>{

	public Integer checkIfInBlackList(@Param("mobile")String mobile);

	public Unsubscribe getByMobile(@Param("mobile")String mobile);

	public List<Unsubscribe> qryUnsubscribeList();
}
