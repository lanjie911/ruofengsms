package com.sms.service.smsmanager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sms.criteria.smsmanager.SmsInboxCriteria;
import com.sms.dao.smsmanager.SmsInboxDao;
import com.sms.entity.smsmanager.SmsInbox;



@Service
public class SmsInboxService {

	@Autowired 
	private SmsInboxDao smsInboxDao;
	
	
	public List<SmsInbox> query(SmsInboxCriteria criteria) {
		return smsInboxDao.query(criteria);
	}


}
