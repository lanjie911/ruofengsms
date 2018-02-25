package com.sms.service.mercmanager;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sms.criteria.mercmanager.MercAccountCriteria;
import com.sms.dao.mercmanager.MercAccountDao;
import com.sms.entity.mercmanager.MercAccount;

@Service
public class MercAccountService {

	@Autowired
	private MercAccountDao mercAccountDao;

	public List<MercAccount> query(MercAccountCriteria criteria) {
		return mercAccountDao.query(criteria);
	}

	public MercAccount getMercAccountByAccountNo(Long mercAccountNo) {
		return mercAccountDao.getMercAccountByAccountNo(mercAccountNo);
	}

	public Map<String, Object> editMercAccount(MercAccount mercAccount, Map<String, Object> result) {
		Integer i = mercAccountDao.update(mercAccount);
		if (i == 0) {
			result.put("success", false);
			result.put("message", "更新商户基本信息失败");
			return result;
		}
		result.put("success", true);
		result.put("message", "添加成功");
		return result;
	}

	public MercAccount getMarketAccount(long merchantId, long accountType) {
		return mercAccountDao.getMarketAccount(merchantId, accountType);
	}

	public MercAccount getMerchantById(long merchantId) {
		return mercAccountDao.getMerchantById(merchantId);

	}

}
