package com.sms.entity.mercmanager;

import java.io.Serializable;

public class MercAccountChannel implements Serializable{

	private static final long serialVersionUID = -8404937874518642392L;
	private Long recordId;  //记录编号
	private Long accountNo;  //商户账号
	private Long channelId;  //通道编号
	private Long priority;  //优先级
	
	public Long getRecordId() {
		return recordId;
	}
	public void setRecordId(Long recordId) {
		this.recordId = recordId;
	}
	public Long getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(Long accountNo) {
		this.accountNo = accountNo;
	}
	public Long getChannelId() {
		return channelId;
	}
	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}
	public Long getPriority() {
		return priority;
	}
	public void setPriority(Long priority) {
		this.priority = priority;
	}
	
}
