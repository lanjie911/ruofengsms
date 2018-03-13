package com.sms.dao.primarydao;

import com.sms.dao.IGenericDao;
import com.sms.entity.PlainSendRecord;
import com.sms.entity.PlainSendResp;

public interface PlainSendRespDao extends IGenericDao<PlainSendRecord, Long>{

	public Integer insert(PlainSendResp PlainSendResp);
	
}