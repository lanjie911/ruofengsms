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
		System.out.println("successRate:" + successRate);
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

	public Double getSuccessRate(Integer succNum, Integer totalNUM) {
		Double c = succNum * 1.0 / totalNUM;
		DecimalFormat df1 = new DecimalFormat("0.0000");
		String rate = df1.format(c);
		Double successRate = Double.parseDouble(rate);
		return successRate;
	}

}