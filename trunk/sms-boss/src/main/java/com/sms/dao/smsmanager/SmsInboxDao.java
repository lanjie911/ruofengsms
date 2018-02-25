package com.sms.dao.smsmanager;

import java.util.List;

import com.sms.annotation.DataSource;
import com.sms.criteria.smsmanager.SmsInboxCriteria;
import com.sms.entity.smsmanager.SmsInbox;

public interface SmsInboxDao{
	@DataSource("trade")
	List<SmsInbox> query(SmsInboxCriteria criteria);

}
