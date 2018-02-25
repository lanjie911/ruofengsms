package com.sms.dao;

import org.apache.ibatis.annotations.Param;

import com.sms.entity.Phone;

public interface PhoneDao extends IGenericDao<Phone, String>{

	public Phone getByMobile(@Param("mobileSeven")String mobileSeven);

}
