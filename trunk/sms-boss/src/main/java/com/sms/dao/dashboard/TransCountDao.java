package com.sms.dao.dashboard;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sms.annotation.DataSource;
import com.sms.criteria.dashboard.TransCountCritreia;
import com.sms.entity.dashboard.TransCount;

public interface TransCountDao {
	@DataSource("trade")
	public TransCount getById(@Param("transCountId")Integer transCountId);
	@DataSource("trade")
	public TransCount getByAccountNo(@Param("accountNo")Long accountNo);
	@DataSource("trade")
	public List<TransCount> query(TransCountCritreia criteria);
	@DataSource("trade")
	public TransCount countLoadData(TransCountCritreia criteria);
}