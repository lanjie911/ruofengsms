package com.sms.dao.serialnumber;

import org.apache.ibatis.annotations.Param;

import com.sms.annotation.DataSource;

public interface SerialNumberDao { 
	
	@DataSource("trade")
	public Long getBytableName(String tableaName);
	@DataSource("trade")
	public Integer updateSerialNumber(@Param("accountNo")Long accountNo, @Param("tableaName")String tableaName);
}