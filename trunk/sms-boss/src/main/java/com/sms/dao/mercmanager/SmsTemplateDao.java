package com.sms.dao.mercmanager;

import java.util.List;

import com.sms.annotation.DataSource;
import com.sms.criteria.mercmanager.SmsTemplateCriteria;
import com.sms.entity.mercmanager.SmsTemplate;

public interface SmsTemplateDao {
	@DataSource("trade")
	List<SmsTemplate> query(SmsTemplateCriteria criteria);
	@DataSource("trade")
	Integer insert(SmsTemplate smsTemplate);
	@DataSource("trade")
	SmsTemplate getById(Long templateId);
	@DataSource("trade")
	Integer update(SmsTemplate smsTemplate);

}
