package com.sms.service.send;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sms.dao.MercAccountDao;
import com.sms.entity.MercAccount;
import com.sms.entity.PlainSendRecord;
import com.sms.util.ResultCommon;
import com.sms.util.TradeException;

/**
 * @author Cao
 * 账户
 */
@Service
public class MercAccountService {
	
	@Autowired
	private MercAccountDao mercAccountDao;
	@Autowired
	private ThreadService threadService;
	
	public MercAccount getById(Long id){
		return mercAccountDao.getById(id);
	}
	
	public MercAccount getMercAccountByMercId(Long mercId){
		return mercAccountDao.getMercAccountByMercId(mercId);
	}
	
	public void frozenBalance (Long accountNo,Integer sendNum, String[] mobiles,String messageId) throws TradeException{
		Integer count = mercAccountDao.frozenBalance(accountNo,sendNum * mobiles.length);
		if(1 != count)
			throw new TradeException(ResultCommon.ErrorCodeTypeEnum.NO_BALANCE.getValue(), ResultCommon.ErrorCodeTypeEnum.NO_BALANCE.getText());
		threadService.genFrozenLs(100,accountNo,sendNum * mobiles.length,messageId);				//异步insert流水
	}
	
	public void frozenBalanceOne (Long accountNo,Integer sendNum, String messageId) throws TradeException{
		Integer count = mercAccountDao.frozenBalance(accountNo,sendNum);
		if(1 != count)
			throw new TradeException(ResultCommon.ErrorCodeTypeEnum.NO_BALANCE.getValue(), ResultCommon.ErrorCodeTypeEnum.NO_BALANCE.getText());
		threadService.genFrozenLs(100,accountNo,sendNum,messageId);				//异步insert流水
	}
	
	public Integer unFrozenBalance(Long accountNo,Integer num,String OrderNo) throws TradeException{
		Integer count = mercAccountDao.unFrozenBalance(accountNo,num);
		if(1 != count)
			throw new TradeException(ResultCommon.ErrorCodeTypeEnum.NO_BALANCE.getValue(), "解冻失败");
		threadService.genFrozenLs(200,accountNo,num,OrderNo);
		return count;
	}
	
	public Integer doCorect(Long accountNo,Integer num,String OrderNo) throws TradeException{
		Integer count = mercAccountDao.doCorrect(accountNo,num);
		if(1 != count)
			throw new TradeException(ResultCommon.ErrorCodeTypeEnum.NO_BALANCE.getValue(), "冲正失败");
		threadService.genFrozenLs(300,accountNo,num,OrderNo);
		return count;
	}
	
	public Integer costQuante(MercAccount mercAccount, String[] mobiles, Integer sendNum, Double costQuantity) throws TradeException {
		Integer costQuanteNum = (int)Math.floor((double)mobiles.length * costQuantity);
		if(1 > costQuanteNum)
			return 0;
		PlainSendRecord plainSendRecord = new PlainSendRecord(null, "cost", mercAccount.getAccountNo(), mercAccount.getMerchantNameAbbreviation(), mercAccount.getAccountType(), mercAccount.getFailToReissueFlag(), null, "cost", 200, "发送成功", null,null, "cost",null,null);
		String[] strs = Arrays.copyOfRange(mobiles, 0, costQuanteNum); 
		threadService.genPlanrecord(strs, plainSendRecord);								//异步生成伪发送成功记录
		unFrozenBalance(mercAccount.getAccountNo(), costQuanteNum * sendNum,"cost");			//解冻并扣减
		return costQuanteNum;
	}
	
}
