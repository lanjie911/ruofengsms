package com.sms.dao.smsmanager;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sms.annotation.DataSource;
import com.sms.criteria.smsmanager.BlackCriteria;
import com.sms.entity.smsmanager.Black;

public interface BlackDao{
	@DataSource("trade")
	List<Black> query(BlackCriteria criteria);
	@DataSource("trade")
	Integer updteStatus(@Param("unsubscribeId") Long unsubscribeId,@Param("beforeStatus") Integer beforeStatus,
			@Param("afterStatus") Integer afterStatus,@Param("operator") String operator);
	@DataSource("trade")
	Black getByMobile(String unsubscribeMobile);
	@DataSource("trade")
	Integer insert(Black black);
	@DataSource("trade")
	Black getById(Long unsubscribeId);
	@DataSource("trade")
	Integer update(Black black);

}
