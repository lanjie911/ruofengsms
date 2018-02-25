package com.sms.entity.smsupload;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 批量发送-文件上传解析
 * 临时数据表
 */
public class SmsBatchUpload implements Serializable {
	private static final long serialVersionUID = -9144849278266273330L;
	
	private Integer smsUploadId;
	private Integer batchType;				// 短信发送类型	100-普通批量发送	200-高级发送
	private String smsContent;				// 短信内容
	private Integer mobileCount;			// 手机号总数
	private Long accountNo;					// 商户账户编号
	private String merchantNameAbbreviation;	// 商户简称
	private Integer accountType;			// 账户类型	100-行业账户	200-普通账户	300-全类型商户
	private Integer orderFlag;				// 是否预约	100-是	200-否
	private Timestamp reservationDatetime;	// 预约发送时间
	private Integer uploadStatus;			// 批量上传状态	100-待处理	200-上传成功	300-数据解析完毕 400-数据同步完毕
	private Timestamp createDatetime;		// 创建时间
	private String operator;				// 上传操作员
	private String signTip;					// 签名
	private String batchNo;					// 短信发送批次号
	
	private String mobileColumn;			// 手机号所在列

	public Integer getSmsUploadId() {
		return smsUploadId;
	}

	public Integer getBatchType() {
		return batchType;
	}

	public String getSmsContent() {
		return smsContent;
	}

	public Integer getMobileCount() {
		return mobileCount;
	}

	public Long getAccountNo() {
		return accountNo;
	}

	public String getMerchantNameAbbreviation() {
		return merchantNameAbbreviation;
	}

	public Integer getAccountType() {
		return accountType;
	}

	public Integer getOrderFlag() {
		return orderFlag;
	}

	public Timestamp getReservationDatetime() {
		return reservationDatetime;
	}

	public Integer getUploadStatus() {
		return uploadStatus;
	}

	public Timestamp getCreateDatetime() {
		return createDatetime;
	}

	public String getOperator() {
		return operator;
	}

	public String getSignTip() {
		return signTip;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public String getMobileColumn() {
		return mobileColumn;
	}

	public void setSmsUploadId(Integer smsUploadId) {
		this.smsUploadId = smsUploadId;
	}

	public void setBatchType(Integer batchType) {
		this.batchType = batchType;
	}

	public void setSmsContent(String smsContent) {
		this.smsContent = smsContent;
	}

	public void setMobileCount(Integer mobileCount) {
		this.mobileCount = mobileCount;
	}

	public void setAccountNo(Long accountNo) {
		this.accountNo = accountNo;
	}

	public void setMerchantNameAbbreviation(String merchantNameAbbreviation) {
		this.merchantNameAbbreviation = merchantNameAbbreviation;
	}

	public void setAccountType(Integer accountType) {
		this.accountType = accountType;
	}

	public void setOrderFlag(Integer orderFlag) {
		this.orderFlag = orderFlag;
	}

	public void setReservationDatetime(Timestamp reservationDatetime) {
		this.reservationDatetime = reservationDatetime;
	}

	public void setUploadStatus(Integer uploadStatus) {
		this.uploadStatus = uploadStatus;
	}

	public void setCreateDatetime(Timestamp createDatetime) {
		this.createDatetime = createDatetime;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public void setSignTip(String signTip) {
		this.signTip = signTip;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public void setMobileColumn(String mobileColumn) {
		this.mobileColumn = mobileColumn;
	}
}