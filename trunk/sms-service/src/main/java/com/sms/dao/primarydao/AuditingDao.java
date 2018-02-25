package com.sms.dao.primarydao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sms.dao.IGenericDao;
import com.sms.entity.Auditing;

public interface AuditingDao extends IGenericDao<Auditing, Long>{

	public Integer insertBatch(@Param("strs")String[] strs,@Param("auditing")Auditing auditing);

	public List<Auditing> loadAuditSms();
}
