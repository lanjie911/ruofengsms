package com.sms.service.mercmanager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sms.criteria.mercmanager.MercAccountCriteria;
import com.sms.dao.mercmanager.MercAccountChannelDao;
import com.sms.dao.mercmanager.MercAccountDao;
import com.sms.entity.mercmanager.MercAccount;
import com.sms.entity.mercmanager.MercAccountChannel;
import com.sms.tradeservice.api.service.TradeForDispatchService;
import com.sms.util.HttpUtil;
import com.sms.util.SignUtil;

@Service
public class MercAccountService {

	private Logger logger = Logger.getLogger(MercAccountService.class);

	@Autowired
	private MercAccountDao mercAccountDao;

	@Value("${notifyTradeChangeA}")
	private String notifyTradeChangeA;

	@Value("${notifyTradeChangeB}")
	private String notifyTradeChangeB;

	@Autowired
	private MercAccountChannelDao mercAccountChannelDao;

	@Autowired
	private TradeForDispatchService tradeForDispatchService;

	public List<MercAccount> query(MercAccountCriteria criteria) {
		return mercAccountDao.query(criteria);
	}

	@Transactional
	public Map<String, Object> addMercAccount(MercAccount mercAccount, Map<String, Object> result) {
		mercAccount.setPassSalt(getRandNum(1, 999999));
		mercAccount.setAccountPwdClearText(mercAccount.getAccountNoPass());
		mercAccount.setAccountNoPass(SignUtil.getSign(mercAccount.getAccountNoPass(), mercAccount.getPassSalt().toString()));

		List<MercAccountChannel> list = new ArrayList<>();
		for (int i = 0; i < mercAccount.getSmsGroupId().length; i++) {
			MercAccountChannel m = new MercAccountChannel();
			m.setAccountNo(mercAccount.getAccountNo());
			m.setChannelId(Long.valueOf(mercAccount.getSmsGroupId()[i]));
			m.setPriority(Long.valueOf(mercAccount.getPriority()[i]));
			list.add(m);
		}

		Integer i = mercAccountDao.insert(mercAccount);
		i += mercAccountChannelDao.insertBatch(list);
		if (i == 0) {
			result.put("success", false);
			result.put("message", "插入商户账户失败");
			return result;
		}
		result.put("success", true);
		result.put("message", "添加成功");
		notifyApiServer(mercAccount.getAccountNo());
		return result;
	}

	public MercAccount getMercAccountByAccountNo(Long mercAccountNo) {
		MercAccount merc = mercAccountDao.getMercAccountByAccountNo(mercAccountNo);

		List<MercAccountChannel> list = mercAccountChannelDao.getByAccountNo(mercAccountNo);
		String[] temp = new String[list.size()];
		String[] tempPriority = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			temp[i] = list.get(i).getChannelId().toString();
			if (null != list.get(i).getPriority()) {
				tempPriority[i] = list.get(i).getPriority().toString();
			}
		}
		if (tempPriority.length > 0) {
			merc.setPriority(tempPriority);
		}
		merc.setSmsGroupId(temp);
		return merc;
	}

	public Map<String, Object> editMercAccount(MercAccount mercAccount, Map<String, Object> result) {
		mercAccount.setPassSalt(getRandNum(1, 999999));
		mercAccount.setAccountNoPass(SignUtil.getSign(mercAccount.getAccountNoPass(), mercAccount.getPassSalt().toString()));
		mercAccount.setAccountPwdClearText(mercAccount.getAccountNoPass());

		List<MercAccountChannel> list = new ArrayList<>();
		for (int i = 0; i < mercAccount.getSmsGroupId().length; i++) {
			MercAccountChannel m = new MercAccountChannel();
			m.setAccountNo(mercAccount.getAccountNo());
			m.setChannelId(Long.valueOf(mercAccount.getSmsGroupId()[i]));
			m.setPriority(Long.valueOf(mercAccount.getPriority()[i]));
			list.add(m);
		}
		mercAccountChannelDao.deleteByAccountNo(mercAccount.getAccountNo());
		Integer i = mercAccountDao.update(mercAccount);
		i += mercAccountChannelDao.insertBatch(list);
		if (i == 0) {
			result.put("success", false);
			result.put("message", "更新商户基本信息失败");
			return result;
		}
		result.put("success", true);
		result.put("message", "添加成功");
		notifyApiServer(mercAccount.getAccountNo());
		return result;
	}
	
	public Map<String, Object> resetPassword(Long accountNo, String password, Map<String, Object> result) {
		Integer salt = getRandNum(1, 999999);
		Integer i = mercAccountDao.resetPassword(accountNo, SignUtil.getSign(password, salt.toString()), salt, password);
		if (i == 0) {
			result.put("success", false);
			result.put("message", "更新商户基本信息失败");
			return result;
		}
		result.put("success", true);
		result.put("message", "添加成功");
		notifyApiServer(accountNo);
		return result;
	}
	
	private void notifyApiServer(Long accountNo) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					toNotifyTradeChange(accountNo, notifyTradeChangeA);
				} catch (Exception e) {
					logger.error("通知A服务同步失败");
				}
				
				try {
					toNotifyTradeChange(accountNo, notifyTradeChangeB);
				} catch (Exception e) {
					logger.error("通知B服务同步失败");
				}
			}
		}).start();
	}

	private void toNotifyTradeChange(Long accountNo, String url) {
		String apiResult = HttpUtil.notifyTradeChange("100", null, null, null, accountNo, url);
		JSONObject apiJson = JSON.parseObject(apiResult);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("accountNo", accountNo);
		jsonObject.put("transCode", "100");
		String resp = tradeForDispatchService.doSendMarketMsgAfterAudit(jsonObject.toJSONString());
		String code = JSONObject.parseObject(resp).getString("code");
		logger.info("MercAccountService.toNotifyTradeChange-Reponse:{"+ resp + "}");
	}

	private static int getRandNum(int min, int max) {
		int randNum = min + (int) (Math.random() * ((max - min) + 1));
		return randNum;
	}
}