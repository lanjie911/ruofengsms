package com.sms.dao.primarydao;

import org.apache.ibatis.annotations.Param;

import com.sms.dao.IGenericDao;
import com.sms.entity.Phone;

public interface PhoneDao extends IGenericDao<Phone, String>{

	public Phone getByMobile(@Param("mobileSeven")String mobileSeven);

}