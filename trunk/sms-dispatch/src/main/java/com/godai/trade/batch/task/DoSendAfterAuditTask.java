package com.godai.trade.batch.task;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.godai.trade.entity.Auditing;
import com.sms.tradeservice.api.service.TradeForDispatchService;

public class DoSendAfterAuditTask implements Callable<String> {

	private static final Logger logger = LoggerFactory.getLogger(DoSendAfterAuditTask.class);

	private TradeForDispatchService tradeForDispatchService;

	private Auditing auditing;

	public DoSendAfterAuditTask(TradeForDispatchService tradeForDispatchService, Auditing auditing) {
		this.auditing = auditing;
		this.tradeForDispatchService = tradeForDispatchService;
	}

	@Override
	public String call() throws Exception {
		Map<String, Object> request = new HashMap<String, Object>();
		request.put("auditing", auditing);
		String requestStr = JSON.toJSONString(request);
		logger.info("#####before do remoteService:{}", requestStr);
		String response = tradeForDispatchService.doSendAfterAudit(requestStr);
		logger.info("#####after do remoteService:{}", response);
		return response;
	}

}