package com.sms.criteria.smsmanager;

import com.sms.criteria.AbstractCriteria;

public class SmsAuditCriteria extends AbstractCriteria {
	
	private Long mercAccountNo; //商户账号
	private Integer mercAccountType; //商户类型
	private Integer smsGroupId; //短息通道组
	private Integer smsAuditStatus; //状态
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
	public Integer getSmsAuditStatus() {
		return smsAuditStatus;
	}
	public void setSmsAuditStatus(Integer smsAuditStatus) {
		this.smsAuditStatus = smsAuditStatus;
	}
}