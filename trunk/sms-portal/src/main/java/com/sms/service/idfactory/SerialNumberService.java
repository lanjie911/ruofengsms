package com.sms.service.idfactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sms.dao.serialnumber.SerialNumberDao;



@Service
public class SerialNumberService {

	@Autowired 
	private SerialNumberDao serialNumberDao;
	
	@Transactional
	public Long generateAccountNo(String tableaName) {
		Long accountNo = serialNumberDao.getBytableName(tableaName);
		if(serialNumberDao.updateSerialNumber(accountNo,tableaName)==1)
			return accountNo;
		return null;
	}
	
}
