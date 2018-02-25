package com.sms.service.send;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sms.dao.primarydao.MercAccountDao;
import com.sms.entity.MercAccount;
import com.sms.util.DatetimeUtil;
import com.sms.util.TradeException;

/**
 * @author Cao 账户
 */
@Service
public class MercAccountService {

	@Autowired
	private MercAccountDao mercAccountDao;
	@Autowired
	private ThreadService threadService;

	public MercAccount getById(Long id) {
		return mercAccountDao.getById(id);
	}

	public MercAccount getMercAccountByMercId(Long mercId) {
		return mercAccountDao.getMercAccountByMercId(mercId);
	}

	public void frozenBalance(Long accountNo, Integer sendNum, String[] mobiles, String messageId)
			throws TradeException {
		Integer count = mercAccountDao.frozenBalance(accountNo, sendNum * mobiles.length);
		if (1 != count)
			throw new TradeException("2001", "{\"msg\":\"可用余额不足\",\"code\":\"2001\"}");
		threadService.genFrozenLs(100, accountNo, sendNum * mobiles.length, messageId); // 异步insert流水
	}

	public void frozenBalanceOne(Long accountNo, Integer sendNum, String messageId) throws TradeException {
		Integer count = mercAccountDao.frozenBalance(accountNo, sendNum);
		if (1 != count)
			throw new TradeException("2001", "{\"msg\":\"可用余额不足\",\"code\":\"2001\"}");
		threadService.genFrozenLs(100, accountNo, sendNum, messageId); // 异步insert流水
	}

	public Integer unFrozenBalance(Long accountNo, Integer num, String OrderNo) throws TradeException {
		Integer count = mercAccountDao.unFrozenBalance(accountNo, num);
		if (1 != count)
			throw new TradeException("2001", "{\"msg\":\"解冻失败\",\"code\":\"2001\"}");
		threadService.genFrozenLs(200, accountNo, num, OrderNo);
		return count;
	}

	public Integer doCorect(Long accountNo, Integer num, String OrderNo) throws TradeException {
		Integer count = mercAccountDao.doCorrect(accountNo, num);
		if (1 != count)
			throw new TradeException("2001", "{\"msg\":\"解冻失败\",\"code\":\"2001\"}");
		threadService.genFrozenLs(300, accountNo, num, OrderNo);
		return count;
	}

	public Integer batchUnFrozenBalance(Long accountNo, int num) throws TradeException {
		Integer count = mercAccountDao.unFrozenBalance(accountNo, num);
		if (1 != count)
			throw new TradeException("2001", "{\"msg\":\"解冻失败\",\"code\":\"2001\"}");
		String currentDateTime = DatetimeUtil.getCurrentDateTime("yyyyMMddHHmmss");
		int randNum = 1 + (int) (Math.random() * ((9999999 - 1) + 1));
		String OrderNo = currentDateTime + randNum;
		threadService.genFrozenLs(200, accountNo, num, OrderNo);
		return count;

	}

	public Integer batchDoCorect(Long accountNo, int num) throws TradeException {
		Integer count = mercAccountDao.doCorrect(accountNo, num);
		if (1 != count)
			throw new TradeException("2001", "{\"msg\":\"解冻失败\",\"code\":\"2001\"}");
		String currentDateTime = DatetimeUtil.getCurrentDateTime("yyyyMMddHHmmss");
		int randNum = 1 + (int) (Math.random() * ((9999999 - 1) + 1));
		String OrderNo = currentDateTime + randNum;
		threadService.genFrozenLs(300, accountNo, num, OrderNo);
		return count;

	}

	public static void main(String[] args) {
		String currentDateTime = DatetimeUtil.getCurrentDateTime("yyyyMMddHHmmss");
		int randNum = 1 + (int) (Math.random() * ((9999999 - 1) + 1));
		String OrderNo = currentDateTime + randNum;
		System.out.println(randNum);
		System.out.println(OrderNo);
	}

}