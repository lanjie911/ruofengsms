package com.sms.entity.smsupload;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 文件上传-短信发送明细
 * 临时数据表
 */
public class SmsDetailUpload implements Serializable {
	private static final long serialVersionUID = 6390725981211930393L;
	private Integer detailUploadId;
	private Long batchNo;				// 批次号
	private Long accountNo;				// 商户账号
	private Integer accountType;		// 账户类型	100-行业账户  200-营销账户 300-全类型商户
	private String accountName;			// 商户名称
	private String mobile;				// 手机号
	private Integer mobileOperator;		// 移动运营商
	private String uploadContent;		// 文件上传内容
	private String smsContent;			// 短信内容
	private Timestamp createDatetime;	// 创建时间
	private Integer detailStatus;		// 状态100-已上传待处理101-上传成功待审核200-审核通过300-审核拒绝400-处理中	403-处理失败600-处理成功
	private Integer smsContentLength;	// 短信内容长度
	private Integer chargingCount;		// 计费条数
	
	public Integer getDetailUploadId() {
		return detailUploadId;
	}
	public Long getBatchNo() {
		return batchNo;
	}
	public Long getAccountNo() {
		return accountNo;
	}
	public Integer getAccountType() {
		return accountType;
	}
	public String getAccountName() {
		return accountName;
	}
	public String getMobile() {
		return mobile;
	}
	public Integer getMobileOperator() {
		return mobileOperator;
	}
	public String getUploadContent() {
		return uploadContent;
	}
	public String getSmsContent() {
		return smsContent;
	}
	public Timestamp getCreateDatetime() {
		return createDatetime;
	}
	public Integer getDetailStatus() {
		return detailStatus;
	}
	public Integer getSmsContentLength() {
		return smsContentLength;
	}
	public Integer getChargingCount() {
		return chargingCount;
	}
	public void setDetailUploadId(Integer detailUploadId) {
		this.detailUploadId = detailUploadId;
	}
	public void setBatchNo(Long batchNo) {
		this.batchNo = batchNo;
	}
	public void setAccountNo(Long accountNo) {
		this.accountNo = accountNo;
	}
	public void setAccountType(Integer accountType) {
		this.accountType = accountType;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public void setMobileOperator(Integer mobileOperator) {
		this.mobileOperator = mobileOperator;
	}
	public void setUploadContent(String uploadContent) {
		this.uploadContent = uploadContent;
	}
	public void setSmsContent(String smsContent) {
		this.smsContent = smsContent;
	}
	public void setCreateDatetime(Timestamp createDatetime) {
		this.createDatetime = createDatetime;
	}
	public void setDetailStatus(Integer detailStatus) {
		this.detailStatus = detailStatus;
	}
	public void setSmsContentLength(Integer smsContentLength) {
		this.smsContentLength = smsContentLength;
	}
	public void setChargingCount(Integer chargingCount) {
		this.chargingCount = chargingCount;
	}
	@Override
	public String toString() {
		return "SmsDetailUpload [detailUploadId=" + detailUploadId + ", batchNo=" + batchNo + ", accountNo=" + accountNo
				+ ", accountType=" + accountType + ", accountName=" + accountName + ", mobile=" + mobile
				+ ", mobileOperator=" + mobileOperator + ", uploadContent=" + uploadContent + ", smsContent="
				+ smsContent + ", createDatetime=" + createDatetime + ", detailStatus=" + detailStatus
				+ ", smsContentLength=" + smsContentLength + ", chargingCount=" + chargingCount + "]";
	}
}