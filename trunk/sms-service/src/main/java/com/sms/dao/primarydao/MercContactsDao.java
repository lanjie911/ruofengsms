package com.sms.dao.primarydao;

import com.sms.entity.MercContacts;

public interface MercContactsDao {
	public Integer insert(MercContacts mercContacts);
	public Integer update(MercContacts mercContacts);
}