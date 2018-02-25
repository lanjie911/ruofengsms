package com.sms.criteria.smsupload;

import com.sms.criteria.AbstractCriteria;

public class SmsBatchUploadCriteria extends AbstractCriteria {
	private Integer uploadStatus;			// 批量上传状态	100-待处理	200-上传成功	300-数据解析完毕 400-数据同步完毕

	public Integer getUploadStatus() {
		return uploadStatus;
	}

	public void setUploadStatus(Integer uploadStatus) {
		this.uploadStatus = uploadStatus;
	}
}