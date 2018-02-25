package com.sms.criteria;

import com.sms.criteria.AbstractCriteria;

public class ReservationSendRecordCriteria extends AbstractCriteria {
	
	private String messageId; //通道名称
	private String orderNo; //通道名称
	private Long accountNo; //通道名称

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public Long getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(Long accountNo) {
		this.accountNo = accountNo;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
}