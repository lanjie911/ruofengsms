package com.sms.dao.smsupload;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sms.annotation.DataSource;
import com.sms.criteria.smsupload.SmsBatchUploadCriteria;
import com.sms.entity.smsupload.SmsBatchUpload;

public interface SmsBatchUploadDao {

	@DataSource("trade")
	public int insert(SmsBatchUpload SmsBatchUpload);
	
	@DataSource("trade")
	public SmsBatchUpload getById(@Param("smsUploadId")Integer smsUploadId);
	
	@DataSource("trade")
	public int uploadFinish(SmsBatchUpload SmsBatchUpload);

	@DataSource("trade")
	public int delete(@Param("smsUploadId")Integer smsUploadId);
	
	@DataSource("trade")
	public List<SmsBatchUpload> query(SmsBatchUploadCriteria criteria);
}