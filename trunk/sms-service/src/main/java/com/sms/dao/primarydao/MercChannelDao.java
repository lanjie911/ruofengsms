package com.sms.dao.primarydao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sms.entity.MercChannel;

public interface MercChannelDao{
	
	public List<MercChannel> qrybyAccountNo(@Param("accountNo") Long accountNo);
}
