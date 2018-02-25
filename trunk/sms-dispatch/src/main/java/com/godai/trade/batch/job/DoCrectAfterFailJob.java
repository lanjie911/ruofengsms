package com.godai.trade.batch.job;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.godai.trade.common.exception.TradeException;
import com.godai.trade.dao.SendResevationDao;
import com.godai.trade.entity.Sms;
import com.sms.tradeservice.api.service.TradeForDispatchService;

@Component("doCrectAfterFailJob")
public class DoCrectAfterFailJob extends SimpleJobService {

	private static final Logger logger = LoggerFactory.getLogger(DoCrectAfterFailJob.class);
	
	@Value("${tk0005.batchSize}")
	private int batchSize; // 一批次多少条记录
	
	@Value("${tk0005.dispatchPoolSize}")
	private int dispatchPoolSize;

	@Autowired
	private TradeForDispatchService tradeForDispatchService;
	
	@Autowired
	private SendResevationDao sendResevationDao;
	
	@Override
	protected void handleTask() throws Exception{ 
		List<Sms> list = sendResevationDao.getNeedCredit();
		
		if (null == list || list.size() == 0) return;
		
		Map<String, Object> request = new HashMap<>();
		for (Sms sms : list) {
			request.put(sms.getAccountNo().toString(), sms.getRecordIdList());
		}
		// 循环分批次处理
		String response =  tradeForDispatchService.doCrectAfterFail(JSON.toJSONString(request));
		JSONObject context = JSON.parseObject(response);
		String respCode = context.getString("code"); // 响应码
		if (null == respCode) {
			logger.error("romote doCrectAfterFailJob.handleTask fail, response: {} ", context);
			throw new TradeException("unkown error", "未知异常", context);
		} else if (!"0000".equals(respCode)) {
			logger.error("romote doCrectAfterFailJob.handleTask fail, response: {} ", context);
			throw new TradeException(respCode, context.getString("message"), context);
		} else {
			logger.info("handle doCrectAfterFailJob.handleTask [{}] successfully.", context);
		}
	}

	@Override
	@Autowired
	protected void registerTaskName(@Value(value = "冲正任务") String taskName) {
		//logger.info("***doCrectAfterFailJob register taskName:{}", taskName);
		super.setTaskName(taskName);
	}

}