package com.sms.dao.primarydao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sms.criteria.AbstractCriteria;
import com.sms.criteria.PageCriteria;
import com.sms.dao.IGenericDao;
import com.sms.entity.PlainSendRecord;

public interface PlainSendRecordDao extends IGenericDao<PlainSendRecord, Long>{

	public Integer insertBatch(@Param("strs")String[] strs,@Param("plainSendRecord")PlainSendRecord plainSendRecord);
	
	public PlainSendRecord getByreqMsgId(@Param("reqMsgId")String reqMsgId);
	
	public PlainSendRecord getByreqMsgIdAndMobile(@Param("reqMsgId")String reqMsgId,@Param("mobile")String mobile);
	
	public List<PlainSendRecord> query(AbstractCriteria criteria);
//	public List<PlainSendRecord> queryList(@Param("offset")int offset,@Param("limit")int limit);
	
}