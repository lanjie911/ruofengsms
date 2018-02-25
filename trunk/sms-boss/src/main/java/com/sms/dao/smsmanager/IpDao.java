package com.sms.dao.smsmanager;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sms.annotation.DataSource;
import com.sms.criteria.smsmanager.IpCriteria;
import com.sms.entity.smsmanager.Ip;

public interface IpDao{
	@DataSource("trade")
	List<Ip> query(IpCriteria criteria);
	@DataSource("trade")
	Ip getByIpAddr(@Param("ipAddress") String ipAddress);
	@DataSource("trade")
	Integer insert(Ip ip);
	@DataSource("trade")
	Integer deleteByIpAddr(@Param("ipAddress") String ipAddress);

}
