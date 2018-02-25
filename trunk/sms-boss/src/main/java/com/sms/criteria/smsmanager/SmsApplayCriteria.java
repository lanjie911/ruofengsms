package com.sms.criteria.smsmanager;

import com.sms.criteria.AbstractCriteria;

public class SmsApplayCriteria extends AbstractCriteria {
	
	private Long batchNo;				// 批次号
	private Integer batchType;			// 短信发送类型	100-普通批量发送	200-高级批量发送	300-接口发送
	private Long accountNo;				// 商户号
	private Integer accountType;		// 账户类型	100-行业账户	200-营销账户 300-全类型商户
	private Integer orderFlag;			// 是否预约	100-是	200-否
	private Integer applayStatus;		// 批次状态100-已上传待处理	101-上传成功待审核 200-审核通过 300-审核拒绝 400-数据处理超时
	
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
	public Integer getOrderFlag() {
		return orderFlag;
	}
	public Integer getApplayStatus() {
		return applayStatus;
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
	public void setOrderFlag(Integer orderFlag) {
		this.orderFlag = orderFlag;
	}
	public void setApplayStatus(Integer applayStatus) {
		this.applayStatus = applayStatus;
	}
}