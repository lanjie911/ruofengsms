package com.sms.dao.systemParam;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sms.annotation.DataSource;
import com.sms.criteria.systemParam.MobileOperatorCriteria;
import com.sms.entity.systemParam.MobileOperator;

public interface MobileOperatorDao {
	
	@DataSource("trade")
	public MobileOperator getById(Integer phoneOperatorId);
	
	@DataSource("trade")
	public void insert(MobileOperator mobileOperator);
	
	@DataSource("trade")
	public int update(MobileOperator mobileOperator);
	
	@DataSource("trade")
	public List<MobileOperator> query(MobileOperatorCriteria criteria);
	
	@DataSource("trade")
	public int countPrefNumByID(@Param("pref")String pref, @Param("phoneOperatorId")Integer phoneOperatorId);
	
	@DataSource("trade")
	public int countPrefNum(@Param("pref")String pref);
	
}