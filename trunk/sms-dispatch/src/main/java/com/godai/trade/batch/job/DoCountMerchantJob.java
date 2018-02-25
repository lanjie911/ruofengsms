package com.godai.trade.batch.job;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.godai.trade.dao.merchant.MercAccountDao;
import com.godai.trade.entity.merchant.MercAccount;
import com.godai.trade.service.dashboard.TransCountService;

@Component("doCountMerchantJob")
public class DoCountMerchantJob extends SimpleJobService {

	private static final Logger logger = LoggerFactory.getLogger(DoCountMerchantJob.class);
	
	@Autowired
	private MercAccountDao mercAccountDao;
	
	@Autowired
	private TransCountService transCountService;
	
	@Override
	protected void handleTask() throws Exception { 
		List<MercAccount> list = mercAccountDao.loadMerAccount();
		if(list.isEmpty() || list.size() == 0)
			return;
		for(MercAccount mercAccount : list){
			try {
				transCountService.genTransCount(mercAccount);
			} catch (Exception e) {
				logger.error("DoCountMerchantJob.handleTask-Foreach Exception:{}", e);
			}
		}
	}

	@Override
	protected void registerTaskName(@Value(value = "商户发送量统计") String taskName) {
		super.setTaskName(taskName);
	}
	
}