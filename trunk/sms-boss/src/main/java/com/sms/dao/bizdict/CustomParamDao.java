package com.sms.dao.bizdict;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sms.annotation.DataSource;
import com.sms.criteria.CustomParamCriteria;
import com.sms.entity.bizdict.CustomParam;

public interface CustomParamDao {
	@DataSource("boss")
	public CustomParam getCustomParamByCode(@Param("paramCode")String paramCode );
	@DataSource("boss")
	public CustomParam getById(Long paramid);
	@DataSource("boss")
	public void insert(CustomParam param);
	@DataSource("boss")
	public void update(CustomParam param);
	@DataSource("boss")
	public List<CustomParam> query(CustomParamCriteria criteria);
}