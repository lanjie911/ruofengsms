package com.sms.criteria;

import com.sms.criteria.AbstractCriteria;

public class ReservationSendRecordCriteria extends AbstractCriteria {
	
	private String messageId; //通道名称

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
	
}