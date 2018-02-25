package com.sms.dao.smsmanager;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sms.annotation.DataSource;
import com.sms.criteria.smsmanager.SmsAuditCriteria;
import com.sms.entity.smsmanager.SmsAudit;

public interface SmsAuditDao{
	@DataSource("trade")
	Integer insert(SmsAudit smsAudit);
	@DataSource("trade")
	List<SmsAudit> query(SmsAuditCriteria criteria);
	@DataSource("trade")
	Integer updteStatus(@Param("batchNo") String batchNo,@Param("auditStatus") Integer auditStatus
				,@Param("beforeStatus") Integer beforeStatus,@Param("operator") String operator);
	@DataSource("trade")
	Integer insertBatch(@Param("strs")String[] strs,@Param("smsAudit")SmsAudit smsAudit);
	@DataSource("trade")
	List<SmsAudit> getByBatchNo(@Param("batchNo") String batchNo);

}
