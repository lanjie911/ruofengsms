package com.sms.dao.trademanager;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sms.annotation.DataSource;
import com.sms.entity.trademanager.SmsAudit;

public interface SmsAuditDao {
	@DataSource("trade")
	public Integer insert(SmsAudit smsAudit);
	
	@DataSource("trade")
	public Integer updteStatus(@Param("auditingId") Long auditingId,@Param("auditStatus") Integer auditStatus
				,@Param("beforeStatus") Integer beforeStatus,@Param("operator") String operator);
	
	@DataSource("trade")
	public Integer insertBatch(@Param("strs")String[] strs,@Param("smsAudit")SmsAudit smsAudit);
	
	@DataSource("trade")
	public Integer insertBatchList(@Param("smsAuditList")List<SmsAudit> smsAuditList);
	
	@DataSource("trade")
	public Integer insertSmsDetailUploadBatch(List<SmsAudit> smsAuditList);
	
	@DataSource("trade")
	public Integer countBySmsUploadBatchNo(@Param("batchNo")String batchNo);
	
	@DataSource("trade")
	public List<SmsAudit> loadFinishUploadLessThan10000(@Param("batchNo")String batchNo);
	
	@DataSource("trade")
	public List<SmsAudit> loadFinishUploadALL(@Param("batchNo")String batchNo);
	
	@DataSource("trade")
	public int batchUpdate(List<SmsAudit> list);
	
	@DataSource("trade")
	public int updateBySmsBatchUpload(@Param("batchNo")String batchNo, @Param("orderFlag")Integer orderFlag,
			@Param("reservationDatetime")String reservationDatetime, @Param("smsContent")String smsContent,
			@Param("smsCount")int smsCount, @Param("smsAccountNum")int smsAccountNum, @Param("signTip")String signTip);
}