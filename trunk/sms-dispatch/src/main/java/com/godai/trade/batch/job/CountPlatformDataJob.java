package com.godai.trade.batch.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sms.tradeservice.api.service.TradeForDispatchService;

@Component("doCountPlatformDataJob")
public class CountPlatformDataJob extends SimpleJobService {

	private static final Logger logger = LoggerFactory.getLogger(CountPlatformDataJob.class);

	@Autowired
	private TradeForDispatchService tradeForDispatchService;

	@Override
	protected void handleTask() throws Exception{ 
		StringBuffer sb =new StringBuffer();
		// 循环分批次处理
		String response =  tradeForDispatchService.countPlatformData(null);
		JSONObject context = JSON.parseObject(response);
		if(context.getBooleanValue("suc")){
			sb.append("处理成功");
		}else{
			sb.append("处理失败");
		}
	}

	@Override
	@Autowired
	protected void registerTaskName(@Value(value = "更新平台统计信息") String taskName) {
		logger.info("***doCountPlatformDataJob register taskName:{}", taskName);
		super.setTaskName(taskName);
	}

}