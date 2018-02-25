package com.sms.dao.smsupload;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sms.annotation.DataSource;
import com.sms.entity.smsupload.SmsDetailUpload;

public interface SmsDetailUploadDao {
	
	@DataSource("trade")
	public int insert(SmsDetailUpload SmsDetailUpload);
	
	@DataSource("trade")
	public int insertSmsDetailUploadBatch(List<SmsDetailUpload> list);

	@DataSource("trade")
	public SmsDetailUpload getById(@Param("detailUploadId")Integer detailUploadId);
	
	@DataSource("trade")
	public List<SmsDetailUpload> loadPenddingByBatchNo(@Param("batchNo")Long batchNo);
	
	@DataSource("trade")
	public int updateAnalysis(SmsDetailUpload SmsDetailUpload);
	
	@DataSource("trade")
	public int delete(@Param("detailUploadId")Integer detailUploadId);
	
	@DataSource("trade")
	public int count101Num(@Param("batchNo")Long batchNo);
	
	
	
	/*@DataSource("trade")
	public List<SmsDetailUpload> query(SmsDetailUploadCriteria criteria);
	
	@DataSource("trade")
	public int batchUpdate(List<SmsDetailUpload> list);
	
	@DataSource("trade")
	public int countBySmsUploadId(@Param("smsUploadId")Integer smsUploadId);

	@DataSource("trade")
	public List<SmsDetailUpload> loadFinishUpload(@Param("smsUploadId")Integer smsUploadId);

	@DataSource("trade")
	public int countNotifyBySmsUploadId(Integer smsUploadId);

	@DataSource("trade")
	public List<SmsDetailUpload> loadNotifyData(Integer smsUploadId);

	@DataSource("trade")
	public int updateNotifyedById(Integer detailUploadId);*/
}