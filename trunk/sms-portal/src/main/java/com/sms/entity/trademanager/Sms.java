package com.sms.entity.trademanager;

import java.io.Serializable;
/**
 * @author Cao
 * 通道
 */
public class Sms implements Serializable{

	private static final long serialVersionUID = -1L;
	
	private Long accountNo; 					// 商户账户编号
	private Long merchantId; 					// 商户编号
	private String merchantNameAbbreviation; 	// 商户简称
	private String mobile; 						// 手机号
	private String content; 					// 短信内容
	private String reservationDatetime; 		// 预约发送时间
	private Integer sendStatus; 				// 发送状态 100 - 待处理101 - 处理中200 - 发送成功300 - 发送失败
	private String sendStatusDes; 				// 发送状态描述
	private String sendMsg; 					// 发送结果说明
	private Integer failedNum; 					// 失败计数
	private String createDatetime;				// 创建时间
	private String respDatetime; 				// 响应时间
	private Integer sumNum; 					// 发送总数
	private Integer successNum	; 				// 成功计数
	private Integer blacklistNum; 				// 黑名单基数
	private String sumSuccess; 					// 计数
	private String orderFlag; 					// 是否预约发送
	private String sendSmsType; 				// 是否批量发送
	private Integer sendAuditFlag; 				// 是否发送审核
	private Integer billingWordsize; 			// 计费个数
	private String operator; 					// 操作员
	private Integer contentSize; 				// 短信字数
	private String messageId; 					// 批次号
	private String signTip; 					// 签名
	
	private String province; 					// 手机号归属省
	private String city; 						// 手机号归属市
	private String isp; 						// 手机号所属运营商
	private Double costQuantity ;   			// 扣量比
	private String smsConetntHidden;
	
	private String mercReqTime; 				// 手机号所属运营商
	
	public Integer getBillingWordsize() {
		return billingWordsize;
	}
	public void setBillingWordsize(Integer billingWordsize) {
		this.billingWordsize = billingWordsize;
	}
	public Integer getSendAuditFlag() {
		return sendAuditFlag;
	}
	public void setSendAuditFlag(Integer sendAuditFlag) {
		this.sendAuditFlag = sendAuditFlag;
	}
	public Long getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(Long accountNo) {
		this.accountNo = accountNo;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getReservationDatetime() {
		return reservationDatetime;
	}
	public void setReservationDatetime(String reservationDatetime) {
		this.reservationDatetime = reservationDatetime;
	}
	public Integer getSendStatus() {
		return sendStatus;
	}
	public void setSendStatus(Integer sendStatus) {
		this.sendStatus = sendStatus;
	}
	public String getSendStatusDes() {
		return sendStatusDes;
	}
	public void setSendStatusDes(String sendStatusDes) {
		this.sendStatusDes = sendStatusDes;
	}
	public String getSendMsg() {
		return sendMsg;
	}
	public void setSendMsg(String sendMsg) {
		this.sendMsg = sendMsg;
	}
	public Integer getFailedNum() {
		return failedNum;
	}
	public void setFailedNum(Integer failedNum) {
		this.failedNum = failedNum;
	}
	public String getCreateDatetime() {
		return createDatetime;
	}
	public void setCreateDatetime(String createDatetime) {
		this.createDatetime = createDatetime;
	}
	public String getRespDatetime() {
		return respDatetime;
	}
	public void setRespDatetime(String respDatetime) {
		this.respDatetime = respDatetime;
	}
	public Integer getSumNum() {
		return sumNum;
	}
	public void setSumNum(Integer sumNum) {
		this.sumNum = sumNum;
	}
	public Integer getSuccessNum() {
		return successNum;
	}
	public void setSuccessNum(Integer successNum) {
		this.successNum = successNum;
	}
	public Integer getBlacklistNum() {
		return blacklistNum;
	}
	public void setBlacklistNum(Integer blacklistNum) {
		this.blacklistNum = blacklistNum;
	}
	public String getSumSuccess() {
		return sumSuccess;
	}
	public void setSumSuccess(String sumSuccess) {
		this.sumSuccess = sumSuccess;
	}
	public String getOrderFlag() {
		return orderFlag;
	}
	public void setOrderFlag(String orderFlag) {
		this.orderFlag = orderFlag;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public Integer getContentSize() {
		return contentSize;
	}
	public void setContentSize(Integer contentSize) {
		this.contentSize = contentSize;
	}
	public Long getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}
	public String getSendSmsType() {
		return sendSmsType;
	}
	public void setSendSmsType(String sendSmsType) {
		this.sendSmsType = sendSmsType;
	}
	public String getMessageId() {
		return messageId;
	}
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
	public String getSignTip() {
		return signTip;
	}
	public void setSignTip(String signTip) {
		this.signTip = signTip;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getIsp() {
		return isp;
	}
	public void setIsp(String isp) {
		this.isp = isp;
	}
	public String getMerchantNameAbbreviation() {
		return merchantNameAbbreviation;
	}
	public void setMerchantNameAbbreviation(String merchantNameAbbreviation) {
		this.merchantNameAbbreviation = merchantNameAbbreviation;
	}
	public Double getCostQuantity() {
		return costQuantity;
	}
	public void setCostQuantity(Double costQuantity) {
		this.costQuantity = costQuantity;
	}
	public String getSmsConetntHidden() {
		return smsConetntHidden;
	}
	public void setSmsConetntHidden(String smsConetntHidden) {
		this.smsConetntHidden = smsConetntHidden;
	}
	public String getMercReqTime() {
		return mercReqTime;
	}
	public void setMercReqTime(String mercReqTime) {
		this.mercReqTime = mercReqTime;
	}
}