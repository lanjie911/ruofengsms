package com.godai.trade.batch.task;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.godai.trade.entity.Sms;
import com.sms.tradeservice.api.service.TradeForDispatchService;

public class DoSendReservationMsgTask implements Callable<String> {

	private static final Logger logger = LoggerFactory.getLogger(DoSendReservationMsgTask.class);

	private TradeForDispatchService tradeForDispatchService;

	private Sms sms;


	public DoSendReservationMsgTask(TradeForDispatchService tradeForDispatchService, Sms sms) {
		this.sms = sms;
		this.tradeForDispatchService = tradeForDispatchService;
	}

	@Override
	public String call() throws Exception {
		Map<String, Object> request = new HashMap<String, Object>();
		request.put("reservationSendRecord", sms);
		String requestStr = JSON.toJSONString(request);
		logger.info("#####before do remoteService:{}", requestStr);
		String response = tradeForDispatchService.doSendReservationMsg(requestStr);
		logger.info("#####after do remoteService:{}", response);
		return response;
	}

}
