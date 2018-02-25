package com.sms.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sms.dao.IGenericDao;
import com.sms.entity.Auditing;

public interface AuditingDao extends IGenericDao<Auditing, Long>{

	public Integer insertBatch(@Param("strs")String[] strs,@Param("auditing")Auditing auditing);

	public List<Auditing> loadAuditSms();
	
	public Integer updateInitToNew(@Param("auditingId")Long auditingId,@Param("init")String init,@Param("newS")String newS);
}
