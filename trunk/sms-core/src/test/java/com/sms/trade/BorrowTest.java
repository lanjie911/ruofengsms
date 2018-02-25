package com.sms.trade;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.sms.dao.AuditingDao;
import com.sms.entity.Auditing;
import com.sms.service.send.AuditingService;
import com.sms.tradeservice.api.service.TradeForDispatchService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/spring-config.xml"})
@ActiveProfiles("dev")
public class BorrowTest {
	@Autowired
	private TradeForDispatchService tradeForDispatchService;
	
	@Autowired
	private AuditingService auditingService;
	
	@Test
	public void test(){
		tradeForDispatchService.doCrectAfterFail("{'10000002':'21249,21250','10000003':'21242,21243,21244'}");
	}
	
	@Test
	public void testaudit(){
		Auditing auditing = new Auditing(10000010l, "123123", 200, 100, null, "20171029142701", "15010477761", "您好", 15, 1, 1, 200, "【若峰伟业】");
		auditing.setCostTip("100");
		auditing.setAuditingId(600209l);
		Map<String, Object> request = new HashMap<String, Object>();
		request.put("auditing", auditing);
		tradeForDispatchService.doSendAfterAudit(JSON.toJSONString(request));
	}
}