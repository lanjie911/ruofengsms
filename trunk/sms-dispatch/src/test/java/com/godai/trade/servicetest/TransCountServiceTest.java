package com.godai.trade.servicetest;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.godai.trade.dao.merchant.MercAccountDao;
import com.godai.trade.entity.merchant.MercAccount;
import com.godai.trade.service.dashboard.TransCountService;

public class TransCountServiceTest extends BaseTest {
	
	@Autowired
	private MercAccountDao mercAccountDao;
	
	@Autowired
	private TransCountService transCountService;

	@Test
	public void genTransCount(){
		List<MercAccount> list = mercAccountDao.loadMerAccount();
		if(list.isEmpty() || list.size() == 0)
			return;
		for(MercAccount mercAccount : list){
			try {
				transCountService.genTransCount(mercAccount);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}