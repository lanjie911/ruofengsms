package com.sms.service.bizdict;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sms.dao.bizdict.BizDictDao;
import com.sms.entity.bizdict.Bizdict;

@Service
public class BizDictService {
	
	@Autowired
	private BizDictDao bizDictDao;
	
	public List<Bizdict> getDir(String dirType) {
		return bizDictDao.getDirs(dirType);
	}
	public List<Bizdict> getDirWithAll(String dirType) {
		List<Bizdict> dirs = bizDictDao.getDirs(dirType);
		List<Bizdict> lists = new ArrayList<>(); 
		Bizdict dir = new Bizdict();
		dir.setDirCode("");
		dir.setDirCodeDesc("全部");
		lists.add(dir);
		lists.addAll(dirs);
		return lists;
	}
	public List<Bizdict> getArea(String cityName) {
		return bizDictDao.getArea(cityName);
	}
	
}