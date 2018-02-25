package com.sms.trade;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sms.criteria.ReservationSendRecordCriteria;
import com.sms.dao.UtilDao;
import com.sms.dao.primarydao.MercAccountLsDao;
import com.sms.dao.primarydao.PlainSendRecordDao;
import com.sms.dao.primarydao.ReservationSendRecordDao;
import com.sms.dao.primarydao.TemplateDao;
import com.sms.entity.MercAccountLs;
import com.sms.entity.PlainSendRecord;
import com.sms.entity.Template;
import com.sms.service.send.SwichToSendService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/spring-config.xml"})
@ActiveProfiles("dev")
public class SmsTest {
	
	@Autowired
	private TemplateDao templateDao;
	
	@Autowired
	private UtilDao utilDao;
	
	@Autowired
	private MercAccountLsDao mercAccountLsDao;
	
	@Autowired
	private PlainSendRecordDao plainSendRecordDao;
	
	@Autowired
	private ReservationSendRecordDao reservationSendRecordDao;
	
	@Autowired
	private SwichToSendService swichToSendService;
	
	@Test
	public void send(){
		try {
			Map<String, Object> b = swichToSendService.send("【第一课堂】", "17310577707", "验证码为123591，提示您保管好自己的验证码，请勿泄露!", "fenghuoCN", 100, 100);
			System.out.println(b);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void dealTest(){
		try {
			Template template = templateDao.getById(123l);
			System.out.println(template.getAccountNo());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void addRepayBal(){
		try {
			utilDao.getCurrentDate();
			utilDao.getCurrentTimestamp();
			utilDao.getBeforeDate();
			utilDao.getAfterDate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void queryres(){
		try {
			ReservationSendRecordCriteria criteria = new ReservationSendRecordCriteria();
			criteria.setMessageId("qwe");
			List<Map<String, Object>> list = reservationSendRecordDao.queryResult(criteria);
			System.out.println(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void ls(){
		try {
			MercAccountLs mercAccountLs = new MercAccountLs(100, 15001l, 2, "13838443414");
			mercAccountLsDao.insert(mercAccountLs);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void plain(){
		try {
			//PlainSendRecord plainSendRecord = new PlainSendRecord(1l, "测试", 10000018l, "什么啊", 100, 200, "QWEIUYSFHASDDTRASNMDAS", "妈卖批啊妈卖批", 200, "成功",null,null,"200000123",null,"【啊】"); 
			//plainSendRecordDao.insert(plainSendRecord);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}