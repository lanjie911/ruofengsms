package com.sms.dao.mercmanager;

import com.sms.annotation.DataSource;
import com.sms.entity.mercmanager.MercContacts;

public interface MercContactsDao {
	@DataSource("trade")
	public Integer insert(MercContacts mercContacts);
	@DataSource("trade")
	public Integer update(MercContacts mercContacts);
}