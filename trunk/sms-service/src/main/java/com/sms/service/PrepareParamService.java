package com.sms.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sms.dao.primarydao.ConvinceIpDao;
import com.sms.dao.primarydao.MercAccountDao;
import com.sms.dao.primarydao.MercChannelDao;
import com.sms.dao.primarydao.SysParamsDao;
import com.sms.dao.primarydao.UnsubscribeDao;
import com.sms.dao.primarydao.WhiteDao;
import com.sms.entity.ConvinceIp;
import com.sms.entity.MercAccount;
import com.sms.entity.MercChannel;
import com.sms.entity.SysParams;
import com.sms.entity.Unsubscribe;
import com.sms.entity.White;

@Service
public class PrepareParamService {

	private Map<Long, MercAccount> mercAccountMap = new HashMap<Long, MercAccount>();

	private Map<String, White> whiteListMap = new HashMap<String, White>();

	private Map<String, Unsubscribe> blackListMap = new HashMap<String, Unsubscribe>();

	private Map<String, ConvinceIp> ipListMap = new HashMap<String, ConvinceIp>();

	private Map<String, SysParams> sysParamsMap = new HashMap<String, SysParams>();

	private Map<Long, List<MercChannel>> mercChannelMap = new HashMap<Long, List<MercChannel>>();

	@Autowired
	private MercAccountDao mercAccountDao;

	@Autowired
	private UnsubscribeDao unsubscribeDao;

	@Autowired
	private WhiteDao whiteDao;

	@Autowired
	private ConvinceIpDao convinceIpDao;

	@Autowired
	private SysParamsDao sysParamsDao;

	@Autowired
	private MercChannelDao mercChannelDao;

	public MercAccount getMercAccount(Long accountNo) {
		return mercAccountMap.get(accountNo);
	}

	public Unsubscribe getUnsubscribe(String unsubscribeMobile) {
		return blackListMap.get(unsubscribeMobile);
	}

	public White getWhite(String whiteMobile) {
		return whiteListMap.get(whiteMobile);
	}

	public ConvinceIp getConvinceIp(String ipAddr) {
		return ipListMap.get(ipAddr);
	}

	public SysParams getParam(String str) {
		return sysParamsMap.get(str);
	}

	public List<MercChannel> getMercChannel(Long accountNo) {
		return mercChannelMap.get(accountNo);
	}

	@PostConstruct
	private void initParams() {
		List<MercAccount> mercAccounts = mercAccountDao.qrymercAccountList();
		if (mercAccounts != null && !mercAccounts.isEmpty()) {
			for (MercAccount mercAccount : mercAccounts) {
				mercAccountMap.put(mercAccount.getAccountNo(), mercAccount);
				List<MercChannel> mercChannellist = mercChannelDao.qrybyAccountNo(mercAccount.getAccountNo());
				if (!mercChannellist.isEmpty())
					mercChannelMap.put(mercAccount.getAccountNo(), mercChannellist);
			}
		}

		List<Unsubscribe> unsubscribes = unsubscribeDao.qryUnsubscribeList();
		if (unsubscribes != null && !unsubscribes.isEmpty()) {
			for (Unsubscribe unsubscribe : unsubscribes) {
				blackListMap.put(unsubscribe.getUnsubscribeMobile(), unsubscribe);
			}
		}

		List<White> whites = whiteDao.qryWhiteList();
		if (whites != null && !whites.isEmpty()) {
			for (White white : whites) {
				whiteListMap.put(white.getMobile(), white);
			}
		}

		List<ConvinceIp> convinceIps = convinceIpDao.qryIp();
		if (convinceIps != null && !convinceIps.isEmpty()) {
			for (ConvinceIp convince : convinceIps) {
				ipListMap.put(convince.getIpAddr(), convince);
			}
		}

		List<SysParams> sysParamsList = sysParamsDao.qryParams();
		if (sysParamsList != null && !sysParamsList.isEmpty()) {
			for (SysParams sysParams : sysParamsList) {
				sysParamsMap.put(sysParams.getParamCode(), sysParams);
			}
		}
	}

	public void reloadAccount(Long accountNo) {
		MercAccount mercAccount = mercAccountDao.getById(accountNo);
		mercAccountMap.put(accountNo, mercAccount);
		List<MercChannel> mercChannellist = mercChannelDao.qrybyAccountNo(mercAccount.getAccountNo());
		if (!mercChannellist.isEmpty())
			mercChannelMap.put(mercAccount.getAccountNo(), mercChannellist);
	}

	public void reloadBlack(String mobile, String type) {
		if ("100".equals(type)) {
			blackListMap.put(mobile, new Unsubscribe(mobile));
		} else {
			blackListMap.remove(mobile);
		}
	}

	public void reloadWhite(String mobile, String type) {
		if ("100".equals(type)) {
			whiteListMap.put(mobile, new White(mobile));
		} else {
			whiteListMap.remove(mobile);
		}
	}

	public void reloadIp(String ip, String type) {
		if ("100".equals(type)) {
			ipListMap.put(ip, new ConvinceIp(ip));
		} else {
			ipListMap.remove(ip);
		}
	}

	public void reloadParam() {
		List<SysParams> sysParamsList = sysParamsDao.qryParams();
		if (sysParamsList != null && !sysParamsList.isEmpty()) {
			for (SysParams sysParams : sysParamsList) {
				sysParamsMap.put(sysParams.getParamCode(), sysParams);
			}
		}
	}
}