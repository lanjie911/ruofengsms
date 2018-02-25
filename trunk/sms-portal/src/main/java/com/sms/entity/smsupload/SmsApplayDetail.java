package com.sms.entity.smsupload;

import java.io.Serializable;
import java.sql.Timestamp;

public class SmsApplayDetail implements Serializable {

	private static final long serialVersionUID = 5965017345059372934L;
	private Integer applayDetailId;			// 主键ID
	private Long batchNo;					// 批次号
	private Integer batchType;				// 短信发送类型	100-普通批量发送	200-高级批量发送	300-接口发送
	private Long accountNo;					// 商户号
	private Integer accountType;			// 账户类型	100-行业账户  200-营销账户 300-全类型商户
	private String accountName;				// 商户名称
	private String mobilesData;				// 手机号信息
	private Integer mobileOperator;			// 移动运营商	100-中国联通		200-中国移动		300-中国电信
	private Integer mobilesCount;			// 手机号数量
	private Integer detailStatus;			// 状态 100-已上传待处理 101-上传成功待审核 200-审核通过 300-审核拒绝 400-处理中 403-处理失败 600-处理成功
	private Timestamp createDatetime;		// 创建时间
	
	public Integer getApplayDetailId() {
		return applayDetailId;
	}
	public Long getBatchNo() {
		return batchNo;
	}
	public Integer getBatchType() {
		return batchType;
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
	public String getMobilesData() {
		return mobilesData;
	}
	public Integer getMobileOperator() {
		return mobileOperator;
	}
	public Integer getMobilesCount() {
		return mobilesCount;
	}
	public Integer getDetailStatus() {
		return detailStatus;
	}
	public Timestamp getCreateDatetime() {
		return createDatetime;
	}
	public void setApplayDetailId(Integer applayDetailId) {
		this.applayDetailId = applayDetailId;
	}
	public void setBatchNo(Long batchNo) {
		this.batchNo = batchNo;
	}
	public void setBatchType(Integer batchType) {
		this.batchType = batchType;
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
	public void setMobilesData(String mobilesData) {
		this.mobilesData = mobilesData;
	}
	public void setMobileOperator(Integer mobileOperator) {
		this.mobileOperator = mobileOperator;
	}
	public void setMobilesCount(Integer mobilesCount) {
		this.mobilesCount = mobilesCount;
	}
	public void setDetailStatus(Integer detailStatus) {
		this.detailStatus = detailStatus;
	}
	public void setCreateDatetime(Timestamp createDatetime) {
		this.createDatetime = createDatetime;
	}
	@Override
	public String toString() {
		return "SmsApplayDetail [applayDetailId=" + applayDetailId + ", batchNo=" + batchNo + ", batchType=" + batchType
				+ ", accountNo=" + accountNo + ", accountType=" + accountType + ", accountName=" + accountName
				+ ", mobilesData=" + mobilesData + ", mobileOperator=" + mobileOperator + ", mobilesCount="
				+ mobilesCount + ", detailStatus=" + detailStatus + ", createDatetime=" + createDatetime + "]";
	}
}