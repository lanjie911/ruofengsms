package com.godai.trade.service.dashboard;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.godai.trade.dao.dashboard.TransCountDao;
import com.godai.trade.dao.trans.PlainSendRecordDao;
import com.godai.trade.entity.dashboard.TransCount;
import com.godai.trade.entity.merchant.MercAccount;

@Service
public class TransCountService {
	private static final Logger logger = LoggerFactory.getLogger(TransCountService.class);

	@Autowired
	private TransCountDao transCountDao;

	@Autowired
	private PlainSendRecordDao plainSendRecordDao;

	/*
	 * public void genTransCount(MercAccount mercAccount) { Long accountNo =
	 * mercAccount.getAccountNo(); PlainSendRecord plainSendRecord =
	 * plainSendRecordDao.countTranNumByAccountNo(accountNo); if (null ==
	 * plainSendRecord) { logger.
	 * error("TransCountService.genTransCount-Error, Gen PlainSendRecord Is Null AccountNo:{}"
	 * , accountNo); return; } TransCount transCount = new TransCount();
	 * transCount.setMerchantNameAbbreviation(mercAccount.
	 * getMerchantNameAbbreviation()); transCount.setAccountNo(accountNo);
	 * transCount.setMerchantId(mercAccount.getMerchantId());
	 * transCount.setSendNum(plainSendRecord.getTotalNum());
	 * transCount.setSuccessNum(plainSendRecord.getSuccNum());
	 * transCount.setFailureNum(plainSendRecord.getFailureNum());
	 * transCount.setUnknownNum(plainSendRecord.getUnknowNum());
	 * transCount.setMissionSuccessRate(plainSendRecord.getSuccAvg());
	 * transCountDao.insert(transCount); }
	 */

	public void genTransCount(MercAccount mercAccount) {
		//查询一天前的数据
		TransCountYesterday(mercAccount);
		//查询两天前的数据
		TransCountBeforeYesterday(mercAccount);
		//查询三天前的数据
		TransCountThreeDaysAgo(mercAccount);
	}

	//查询一天前的数据并插入统计表
	private void TransCountYesterday(MercAccount mercAccount) {
		String yesterdayStart = getYesterdayStart();
		String yesterdayEnd = getYesterdayEnd();
		String yesterday = getYesterday();
		Long accountNo = mercAccount.getAccountNo();
		Integer totalNum = plainSendRecordDao.queryTotalNum(accountNo, yesterdayStart, yesterdayEnd);
		Integer succNum = plainSendRecordDao.querySuccNum(accountNo, yesterdayStart, yesterdayEnd);
		Integer failNum = plainSendRecordDao.queryFailNum(accountNo, yesterdayStart, yesterdayEnd);
		Integer unknowNum = plainSendRecordDao.queryUnknowNum(accountNo, yesterdayStart, yesterdayEnd);

		if (0 == totalNum) {
			logger.error("TransCountService.genTransCount-Error, Gen PlainSendRecord Is Null AccountNo:{}", accountNo);
			return;
		}
		Double successRate = getSuccessRate(succNum, totalNum);
		TransCount transCount = new TransCount();
		transCount.setMerchantNameAbbreviation(mercAccount.getMerchantNameAbbreviation());
		transCount.setAccountNo(accountNo);
		transCount.setMerchantId(mercAccount.getMerchantId());
		transCount.setSendNum(totalNum);
		transCount.setSuccessNum(succNum);
		transCount.setFailureNum(failNum);
		transCount.setUnknownNum(unknowNum);
		transCount.setMissionSuccessRate(successRate);
		transCount.setStatisticalTime(Long.parseLong(yesterday));
		transCountDao.insert(transCount);
	}
	
	public String getYesterday() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		String yesterday = new SimpleDateFormat("yyyyMMdd").format(cal.getTime());
		return yesterday;
		
	}
	
	public String getYesterdayStart() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		String yesterdayStart = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ").format(cal.getTime());
		return yesterdayStart;
		
	}
	
	public String getYesterdayEnd() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		String yesterdayEnd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ").format(cal.getTime());
		return yesterdayEnd;
	}

	//查询两天前的数据并更新统计表
	private void TransCountBeforeYesterday(MercAccount mercAccount) {
		String beforeyesterdayStart = getBeforeYesterdayStart();
		String beforeyesterdayEnd = getBeforeYesterdayEnd();
		String beforeyesterday = getBeforeYesterday();
		Long accountNo = mercAccount.getAccountNo();
		TransCount transCount = transCountDao.getByAccountNoAndStatisticalTime(accountNo,Long.parseLong(beforeyesterday));
		if(transCount !=null){
			logger.info("TransCountService.genTransCount-Error, Gen TransCount Is Null AccountNo:{},StatisticalTime:{}", accountNo+","+Long.parseLong(beforeyesterday));
		
		Integer totalNum = plainSendRecordDao.queryTotalNum(accountNo, beforeyesterdayStart, beforeyesterdayEnd);
		Integer succNum = plainSendRecordDao.querySuccNum(accountNo, beforeyesterdayStart, beforeyesterdayEnd);
		Integer failNum = plainSendRecordDao.queryFailNum(accountNo, beforeyesterdayStart, beforeyesterdayEnd);
		Integer unknowNum = plainSendRecordDao.queryUnknowNum(accountNo, beforeyesterdayStart, beforeyesterdayEnd);
		
		if (0 == totalNum) {
			logger.error("TransCountService.genTransCount-Error, Gen PlainSendRecord Is Null AccountNo:{}", accountNo);
			return;
		}
		Double successRate = getSuccessRate(succNum, totalNum);
		
		transCount.setMerchantNameAbbreviation(mercAccount.getMerchantNameAbbreviation());
		transCount.setAccountNo(accountNo);
		transCount.setMerchantId(mercAccount.getMerchantId());
		transCount.setSendNum(totalNum);
		transCount.setSuccessNum(succNum);
		transCount.setFailureNum(failNum);
		transCount.setUnknownNum(unknowNum);
		transCount.setMissionSuccessRate(successRate);
		transCount.setStatisticalTime(Long.parseLong(beforeyesterday));
		transCountDao.update(transCount);
		}
	}

	public String getBeforeYesterday() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -2);
		String yesterday = new SimpleDateFormat("yyyyMMdd").format(cal.getTime());
		return yesterday;

	}

	public String getBeforeYesterdayStart() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -2);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		String beforeYesterdayStart = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ").format(cal.getTime());
		return beforeYesterdayStart;

	}

	public String getBeforeYesterdayEnd() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -2);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		String beforeyesterdayEnd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ").format(cal.getTime());
		return beforeyesterdayEnd;
	}
	
	//查询三天前的数据并更新统计表
	private void TransCountThreeDaysAgo(MercAccount mercAccount) {
		String threeDaysAgoStart = getThreeDaysAgoStart();
		String threeDaysAgoEnd = getThreeDaysAgoEnd();
		String threeDaysAgo = getThreeDaysAgo();
		Long accountNo = mercAccount.getAccountNo();
		TransCount transCount = transCountDao.getByAccountNoAndStatisticalTime(accountNo,Long.parseLong(threeDaysAgo));
		if(transCount !=null){
			logger.info("TransCountService.genTransCount-Error, Gen TransCount Is Null AccountNo:{},StatisticalTime:{}", accountNo+","+Long.parseLong(threeDaysAgo));
		
		Integer totalNum = plainSendRecordDao.queryTotalNum(accountNo, threeDaysAgoStart, threeDaysAgoEnd);
		Integer succNum = plainSendRecordDao.querySuccNum(accountNo, threeDaysAgoStart, threeDaysAgoEnd);
		Integer failNum = plainSendRecordDao.queryFailNum(accountNo, threeDaysAgoStart, threeDaysAgoEnd);
		Integer unknowNum = plainSendRecordDao.queryUnknowNum(accountNo, threeDaysAgoStart, threeDaysAgoEnd);
		
		if (0 == totalNum) {
			logger.error("TransCountService.genTransCount-Error, Gen PlainSendRecord Is Null AccountNo:{}", accountNo);
			return;
		}
		Double successRate = getSuccessRate(succNum, totalNum);
		
		transCount.setMerchantNameAbbreviation(mercAccount.getMerchantNameAbbreviation());
		transCount.setAccountNo(accountNo);
		transCount.setMerchantId(mercAccount.getMerchantId());
		transCount.setSendNum(totalNum);
		transCount.setSuccessNum(succNum);
		transCount.setFailureNum(failNum);
		transCount.setUnknownNum(unknowNum);
		transCount.setMissionSuccessRate(successRate);
		transCount.setStatisticalTime(Long.parseLong(threeDaysAgo));
		transCountDao.update(transCount);
		}
	}
	
	public String getThreeDaysAgo() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -3);
		String threeDaysAgo = new SimpleDateFormat("yyyyMMdd").format(cal.getTime());
		return threeDaysAgo;
		
	}
	
	public String getThreeDaysAgoStart() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -3);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		String threeDaysAgoStart = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ").format(cal.getTime());
		return threeDaysAgoStart;
		
	}
	
	public String getThreeDaysAgoEnd() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -3);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		String threeDaysAgoEnd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ").format(cal.getTime());
		return threeDaysAgoEnd;
	}

	public Double getSuccessRate(Integer succNum, Integer totalNUM) {
		Double c = succNum * 1.0 / totalNUM;
		DecimalFormat df1 = new DecimalFormat("0.0000");
		String rate = df1.format(c);
		Double successRate = Double.parseDouble(rate);
		return successRate;
	}

}