package com.sms.dao.smsmanager;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sms.annotation.DataSource;
import com.sms.criteria.smsmanager.WhiteCriteria;
import com.sms.entity.smsmanager.White;

public interface WhiteDao{
	@DataSource("trade")
	List<White> query(WhiteCriteria criteria);
	@DataSource("trade")
	Integer updte(White white);
	@DataSource("trade")
	White getByMobile(@Param("mobile") String mobile);
	@DataSource("trade")
	Integer insert(White white);
	@DataSource("trade")
	Integer update(White white);
	@DataSource("trade")
	Integer deleteByMobile(@Param("mobile") String mobile);

}
