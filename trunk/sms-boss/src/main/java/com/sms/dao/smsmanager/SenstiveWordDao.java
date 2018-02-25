package com.sms.dao.smsmanager;

import java.util.List;

import com.sms.annotation.DataSource;
import com.sms.criteria.smsmanager.SenstiveWordCriteria;
import com.sms.entity.smsmanager.SenstiveWord;

public interface SenstiveWordDao{
	@DataSource("trade")
	List<SenstiveWord> query(SenstiveWordCriteria criteria);
	@DataSource("trade")
	Integer insert(SenstiveWord senstiveWord);
	@DataSource("trade")
	Integer delete(Long wordId);

}
