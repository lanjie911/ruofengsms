package com.sms.criteria.dashboard;

import com.sms.criteria.AbstractCriteria;

public class TransCountCritreia extends AbstractCriteria {
	private Long merchantId;				// 商户编号
	private Long accountNo;					// 商户账户编号
	private Long statisticalTimeStart;		// 统计时间
	private Long statisticalTimeEnd;		// 统计时间
	
	public Long getMerchantId() {
		return merchantId;
	}
	public Long getAccountNo() {
		return accountNo;
	}
	public Long getStatisticalTimeStart() {
		return statisticalTimeStart;
	}
	public Long getStatisticalTimeEnd() {
		return statisticalTimeEnd;
	}
	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}
	public void setAccountNo(Long accountNo) {
		this.accountNo = accountNo;
	}
	public void setStatisticalTimeStart(Long statisticalTimeStart) {
		this.statisticalTimeStart = statisticalTimeStart;
	}
	public void setStatisticalTimeEnd(Long statisticalTimeEnd) {
		this.statisticalTimeEnd = statisticalTimeEnd;
	}
}