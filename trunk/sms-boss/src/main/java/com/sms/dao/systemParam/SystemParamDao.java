package com.sms.dao.systemParam;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sms.annotation.DataSource;
import com.sms.criteria.systemParam.SystemParamCriteria;
import com.sms.entity.systemParam.SystemParam;

public interface SystemParamDao {
	@DataSource("trade")
	public SystemParam getSystemParamByCode(@Param("paramCode")String paramCode);
	
	@DataSource("trade")
	public SystemParam getById(Long paramid);
	
	@DataSource("trade")
	public void insert(SystemParam param);
	
	@DataSource("trade")
	public int update(SystemParam param);
	
	@DataSource("trade")
	public List<SystemParam> query(SystemParamCriteria criteria);
	
	@DataSource("trade")
	public int volidataParamCode(@Param("paramCode")String paramCode,@Param("currentParamId")Long currentParamId);
}