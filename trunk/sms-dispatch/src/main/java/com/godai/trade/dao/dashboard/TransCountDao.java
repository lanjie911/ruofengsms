package com.godai.trade.dao.dashboard;

import org.apache.ibatis.annotations.Param;

import com.godai.trade.entity.dashboard.TransCount;

public interface TransCountDao {
	public int insert(TransCount transCount);
	public TransCount getById(@Param("transCountId")Integer transCountId);
	public TransCount getByAccountNo(@Param("accountNo")Long accountNo);
	public int update(TransCount transCount);
	public TransCount getByAccountNoAndStatisticalTime(@Param("accountNo")Long accountNo,@Param("statisticalTime") Long statisticalTime);
}