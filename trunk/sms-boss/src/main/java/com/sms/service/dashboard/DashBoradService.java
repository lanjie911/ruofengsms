package com.sms.service.dashboard;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sms.criteria.dashboard.TransCountCritreia;
import com.sms.dao.dashboard.TransCountDao;
import com.sms.entity.dashboard.TransCount;

@Service
public class DashBoradService {
	@Autowired
	private TransCountDao transCountDao;
	
	public List<TransCount> queryTransCount(TransCountCritreia criteria){
		return transCountDao.query(criteria);
	}
	
	public TransCount countLoadData(TransCountCritreia criteria){
		return transCountDao.countLoadData(criteria);
	}
}