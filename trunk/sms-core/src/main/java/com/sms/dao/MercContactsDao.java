package com.sms.dao;

import com.sms.entity.MercContacts;

public interface MercContactsDao {
	
	public Integer insert(MercContacts mercContacts);
	
	public Integer update(MercContacts mercContacts);
}