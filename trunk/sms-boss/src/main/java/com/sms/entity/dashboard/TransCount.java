package com.sms.entity.dashboard;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 交易数据统计
 */
public class TransCount implements Serializable {

	private static final long serialVersionUID = -1951691897931642647L;
	private Integer transCountId;
	private Long merchantId;					// 商户编号
	private Long accountNo;						// 商户账户编号
	private String merchantNameAbbreviation;	// 商户简称
	private Integer sendNum;					// 短信发送总数
	private Integer successNum;					// 短信发送成功数
	private Integer failureNum;					// 短信发送失败数量
	private Integer unknownNum;					// 短信发送未知数量
	private Double missionSuccessRate;			// 成功率
	private String missionSuccessRateDes; 
	private Long statisticalTime;				// 统计时间
	private Timestamp createDatetime;			// 创建时间
	private Integer dataStatus;					// 状态	100-使用		200-禁用
	
	private Integer sumTotal;
	private Integer sumSucc;
	private Integer sumFailure;
	private Integer sumUnknow;
	private String sumRateDes;
	
	public Integer getTransCountId() {
		return transCountId;
	}
	public Long getMerchantId() {
		return merchantId;
	}
	public Long getAccountNo() {
		return accountNo;
	}
	public String getMerchantNameAbbreviation() {
		return merchantNameAbbreviation;
	}
	public Integer getSendNum() {
		return sendNum;
	}
	public Integer getSuccessNum() {
		return successNum;
	}
	public Integer getFailureNum() {
		return failureNum;
	}
	public Integer getUnknownNum() {
		return unknownNum;
	}
	public Double getMissionSuccessRate() {
		return missionSuccessRate;
	}
	public String getMissionSuccessRateDes() {
		return missionSuccessRateDes;
	}
	public Long getStatisticalTime() {
		return statisticalTime;
	}
	public Timestamp getCreateDatetime() {
		return createDatetime;
	}
	public Integer getDataStatus() {
		return dataStatus;
	}
	public Integer getSumTotal() {
		return sumTotal;
	}
	public Integer getSumSucc() {
		return sumSucc;
	}
	public Integer getSumFailure() {
		return sumFailure;
	}
	public Integer getSumUnknow() {
		return sumUnknow;
	}
	public String getSumRateDes() {
		return sumRateDes;
	}
	public void setTransCountId(Integer transCountId) {
		this.transCountId = transCountId;
	}
	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}
	public void setAccountNo(Long accountNo) {
		this.accountNo = accountNo;
	}
	public void setMerchantNameAbbreviation(String merchantNameAbbreviation) {
		this.merchantNameAbbreviation = merchantNameAbbreviation;
	}
	public void setSendNum(Integer sendNum) {
		this.sendNum = sendNum;
	}
	public void setSuccessNum(Integer successNum) {
		this.successNum = successNum;
	}
	public void setFailureNum(Integer failureNum) {
		this.failureNum = failureNum;
	}
	public void setUnknownNum(Integer unknownNum) {
		this.unknownNum = unknownNum;
	}
	public void setMissionSuccessRate(Double missionSuccessRate) {
		this.missionSuccessRate = missionSuccessRate;
	}
	public void setMissionSuccessRateDes(String missionSuccessRateDes) {
		this.missionSuccessRateDes = missionSuccessRateDes;
	}
	public void setStatisticalTime(Long statisticalTime) {
		this.statisticalTime = statisticalTime;
	}
	public void setCreateDatetime(Timestamp createDatetime) {
		this.createDatetime = createDatetime;
	}
	public void setDataStatus(Integer dataStatus) {
		this.dataStatus = dataStatus;
	}
	public void setSumTotal(Integer sumTotal) {
		this.sumTotal = sumTotal;
	}
	public void setSumSucc(Integer sumSucc) {
		this.sumSucc = sumSucc;
	}
	public void setSumFailure(Integer sumFailure) {
		this.sumFailure = sumFailure;
	}
	public void setSumUnknow(Integer sumUnknow) {
		this.sumUnknow = sumUnknow;
	}
	public void setSumRateDes(String sumRateDes) {
		this.sumRateDes = sumRateDes;
	}
	@Override
	public String toString() {
		return "TransCount [transCountId=" + transCountId + ", merchantId=" + merchantId + ", accountNo=" + accountNo
				+ ", merchantNameAbbreviation=" + merchantNameAbbreviation + ", sendNum=" + sendNum + ", successNum="
				+ successNum + ", failureNum=" + failureNum + ", unknownNum=" + unknownNum + ", missionSuccessRate="
				+ missionSuccessRate + ", missionSuccessRateDes=" + missionSuccessRateDes + ", statisticalTime="
				+ statisticalTime + ", createDatetime=" + createDatetime + ", dataStatus=" + dataStatus + ", sumTotal="
				+ sumTotal + ", sumSucc=" + sumSucc + ", sumFailure=" + sumFailure + ", sumUnknow=" + sumUnknow
				+ ", sumRateDes=" + sumRateDes + "]";
	}
}