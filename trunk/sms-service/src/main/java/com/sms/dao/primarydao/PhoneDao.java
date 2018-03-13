package com.sms.dao.primarydao;

import com.sms.dao.IGenericDao;
import com.sms.entity.Phone;

public interface PhoneDao extends IGenericDao<Phone, String>{

	public Phone getByMobile(String phone);

}