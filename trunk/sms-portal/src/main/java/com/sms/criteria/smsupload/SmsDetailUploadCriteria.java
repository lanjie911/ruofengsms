package com.sms.criteria.smsupload;

import com.sms.criteria.AbstractCriteria;

public class SmsDetailUploadCriteria extends AbstractCriteria {
	private Integer smsUploadId;		// 批次号
	private Integer detailStatus;		// 状态	100-待处理	200-短信内容解析完毕	300-短信同步成功
	
	public Integer getSmsUploadId() {
		return smsUploadId;
	}
	public Integer getDetailStatus() {
		return detailStatus;
	}
	public void setSmsUploadId(Integer smsUploadId) {
		this.smsUploadId = smsUploadId;
	}
	public void setDetailStatus(Integer detailStatus) {
		this.detailStatus = detailStatus;
	}
}