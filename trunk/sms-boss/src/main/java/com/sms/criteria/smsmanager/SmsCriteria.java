package com.sms.criteria.smsmanager;

import com.sms.criteria.AbstractCriteria;

public class SmsCriteria extends AbstractCriteria {

	private Long batchNo;				// 批次号
	private Long mercAccountNo; 		// 商户账号
	private Integer mercAccountType; 	// 商户类型
	private Integer smsGroupId; 		// 短息通道组
	private Integer smsStatus; 			// 状态
	private String mobile; 				// 手机号
	
	public Long getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(Long batchNo) {
		this.batchNo = batchNo;
	}
	public Long getMercAccountNo() {
		return mercAccountNo;
	}
	public void setMercAccountNo(Long mercAccountNo) {
		this.mercAccountNo = mercAccountNo;
	}
	public Integer getMercAccountType() {
		return mercAccountType;
	}
	public void setMercAccountType(Integer mercAccountType) {
		this.mercAccountType = mercAccountType;
	}
	public Integer getSmsGroupId() {
		return smsGroupId;
	}
	public void setSmsGroupId(Integer smsGroupId) {
		this.smsGroupId = smsGroupId;
	}
	public Integer getSmsStatus() {
		return smsStatus;
	}
	public void setSmsStatus(Integer smsStatus) {
		this.smsStatus = smsStatus;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
}