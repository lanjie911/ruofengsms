package com.sms.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sms.dao.MercAccountDao;
import com.sms.dao.MercChannelDao;
import com.sms.dao.SysParamsDao;
import com.sms.dao.UnsubscribeDao;
import com.sms.dao.WhiteDao;
import com.sms.entity.MercAccount;
import com.sms.entity.MercChannel;
import com.sms.entity.SysParams;
import com.sms.entity.Unsubscribe;
import com.sms.entity.White;

@Service
public class PrepareParamService {

	
	private Map<Long,MercAccount> mercAccountMap= new HashMap<Long,MercAccount>();
	
	private Map<String,White> whiteListMap= new HashMap<String,White>();
	
	private Map<String,Unsubscribe> blackListMap= new HashMap<String,Unsubscribe>();
	
	private Map<String,SysParams> sysParamsMap= new HashMap<String,SysParams>();
	
	private Map<Long,List<MercChannel>> mercChannelMap= new HashMap<Long,List<MercChannel>>();
	
	@Autowired
	private MercAccountDao mercAccountDao;
	
	@Autowired
	private UnsubscribeDao unsubscribeDao;
	
	@Autowired
	private WhiteDao whiteDao;
	
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
	
	public SysParams getParam(String str) {
		return sysParamsMap.get(str);
	}
	
	public List<MercChannel> getMercChannel(Long accountNo) {
		return mercChannelMap.get(accountNo);
	}
	

	@PostConstruct
	private void initParams() {
		List<MercAccount> mercAccounts =mercAccountDao.qrymercAccountList();
		if(mercAccounts!=null&&!mercAccounts.isEmpty()){
			for (MercAccount mercAccount : mercAccounts) {
				mercAccountMap.put(mercAccount.getAccountNo(), mercAccount);
				List<MercChannel> mercChannellist =  mercChannelDao.qrybyAccountNo(mercAccount.getAccountNo());
				if(!mercChannellist.isEmpty())
					mercChannelMap.put(mercAccount.getAccountNo(), mercChannellist);
			}
		}
		
		List<Unsubscribe> unsubscribes =unsubscribeDao.qryUnsubscribeList();
		if(unsubscribes!=null&&!unsubscribes.isEmpty()){
			for (Unsubscribe unsubscribe : unsubscribes) {
				blackListMap.put(unsubscribe.getUnsubscribeMobile(),unsubscribe);
			}
		}
		
		List<White> whites =whiteDao.qryWhiteList();
		if(whites!=null&&!whites.isEmpty()){
			for (White white : whites) {
				whiteListMap.put(white.getMobile(),white);
			}
		}
		
		List<SysParams> sysParamsList =sysParamsDao.qryParams();
		if(sysParamsList!=null&&!sysParamsList.isEmpty()){
			for (SysParams sysParams : sysParamsList) {
				sysParamsMap.put(sysParams.getParamCode(),sysParams);
			}
		}
	}
	
	public void reloadAccount(Long accountNo){
		MercAccount mercAccount = mercAccountDao.getById(accountNo);
		mercAccountMap.put(accountNo, mercAccount);
		List<MercChannel> mercChannellist =  mercChannelDao.qrybyAccountNo(mercAccount.getAccountNo());
		if(!mercChannellist.isEmpty())
			mercChannelMap.put(mercAccount.getAccountNo(), mercChannellist);
	}
	
	public void reloadBlack(String mobile,String type){
		if("100".equals(type)){
			blackListMap.put(mobile, new Unsubscribe(mobile));
		}else{
			blackListMap.remove(mobile);
		}
	}

	public void reloadWhite(String mobile,String type){
		if("100".equals(type)){
			whiteListMap.put(mobile,new White(mobile));
		}else{
			whiteListMap.remove(mobile);
		}
	}
	
	public void reloadParam(){
		List<SysParams> sysParamsList =sysParamsDao.qryParams();
		if(sysParamsList!=null&&!sysParamsList.isEmpty()){
			for (SysParams sysParams : sysParamsList) {
				sysParamsMap.put(sysParams.getParamCode(),sysParams);
			}
		}
	}
	
}
