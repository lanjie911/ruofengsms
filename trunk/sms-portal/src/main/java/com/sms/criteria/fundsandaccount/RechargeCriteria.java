package com.sms.criteria.fundsandaccount;

import com.sms.criteria.AbstractCriteria;

public class RechargeCriteria extends AbstractCriteria {
	private Long mercAccountNo;                   //商户账号
	private Long rechargeNo;              // 充值订单号
	private Long outRechargeNo;               //外部订单号
	private String rechargeDateBegin;            //充值日期开始
	private String rechargeDateEnd;            //充值日期结束
	public Long getMercAccountNo() {
		return mercAccountNo;
	}
	public void setMercAccountNo(Long mercAccountNo) {
		this.mercAccountNo = mercAccountNo;
	}
	public Long getRechargeNo() {
		return rechargeNo;
	}
	public void setRechargeNo(Long rechargeNo) {
		this.rechargeNo = rechargeNo;
	}
	public Long getOutRechargeNo() {
		return outRechargeNo;
	}
	public void setOutRechargeNo(Long outRechargeNo) {
		this.outRechargeNo = outRechargeNo;
	}
	public String getRechargeDateBegin() {
		return rechargeDateBegin;
	}
	public void setRechargeDateBegin(String rechargeDateBegin) {
		this.rechargeDateBegin = rechargeDateBegin;
	}
	public String getRechargeDateEnd() {
		return rechargeDateEnd;
	}
	public void setRechargeDateEnd(String rechargeDateEnd) {
		this.rechargeDateEnd = rechargeDateEnd;
	}
	
	
}