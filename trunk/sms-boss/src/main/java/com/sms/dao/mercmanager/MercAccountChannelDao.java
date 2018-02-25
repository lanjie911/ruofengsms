package com.sms.dao.mercmanager;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sms.annotation.DataSource;
import com.sms.entity.mercmanager.MercAccountChannel;

public interface MercAccountChannelDao { 
	@DataSource("trade")
	public Integer insertBatch(List<MercAccountChannel> list);
	@DataSource("trade")
	public List<MercAccountChannel> getByAccountNo(@Param("mercAccountNo") Long mercAccountNo);
	@DataSource("trade")
	public void deleteByAccountNo(@Param("accountNo")Long accountNo);

}