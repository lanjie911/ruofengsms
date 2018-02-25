package com.sms.entity.smsmanager;

import java.io.Serializable;
/**
 * @author Cao
 * 通道
 */
public class Sms implements Serializable{

	private static final long serialVersionUID = -1L;
	
	private Long recordId; //	记录编号
	private String batchNo;	// 批次号
	private Long channelId; //	通道编号
	private String channelName; //	通道名称
	private Long accountNo; //	商户账户编号
	private String merchantNameAbbreviation; //	商户简称
	private Integer accountType; //	账户类型 100 - 行业账户 200 - 普通账户 300 - 全类型商户
	private String accountTypeDes; //	账户类型描述
	private Integer failedRetransmission; //	是否失败重发 100 - 是 200 - 否
	private String mobile; //	手机号
	private String content; //	短信内容
	private String reservationDatetime; //	预约发送时间
	private Integer sendStatus; //	发送状态 100 - 待处理101 - 处理中200 - 发送成功300 - 发送失败
	private String sendStatusDes; //	发送状态描述
	private String sendMsg; //	发送结果说明
	private Integer failedNum; //	失败计数
	private String createDatetime; //	创建时间
	private String respDatetime; //	响应时间
	private Integer sumNum; //	发送总数
	private Integer successNum	; //成功计数
	private Integer blacklistNum; //	黑名单基数
	private String sumSuccess; //	计数
	private String appointmentFlag; //	是否预约发送
	private String sendSmsType; //	是否批量发送
	private Integer sendAuditFlag; //	是否发送审核
	private Integer billingWordsize; //	计费个数
	private String signTip; //	签名
	private Double costQuantity ;   //扣量比
	
	private String province; //	手机号归属省
	private String city; //	手机号归属市
	private String isp; //	手机号所属运营商
	private String reqMsgId; //	手机号所属运营商
	private String mercReqTime; //	手机号所属运营商
	
	public String getAccountTypeDes() {
		return accountTypeDes;
	}
	public void setAccountTypeDes(String accountTypeDes) {
		this.accountTypeDes = accountTypeDes;
	}
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
	public String getSendSmsType() {
		return sendSmsType;
	}
	public void setSendSmsType(String sendSmsType) {
		this.sendSmsType = sendSmsType;
	}
	public Long getRecordId() {
		return recordId;
	}
	public void setRecordId(Long recordId) {
		this.recordId = recordId;
	}
	public Long getChannelId() {
		return channelId;
	}
	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	public Long getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(Long accountNo) {
		this.accountNo = accountNo;
	}
	public String getMerchantNameAbbreviation() {
		return merchantNameAbbreviation;
	}
	public void setMerchantNameAbbreviation(String merchantNameAbbreviation) {
		this.merchantNameAbbreviation = merchantNameAbbreviation;
	}
	public Integer getAccountType() {
		return accountType;
	}
	public void setAccountType(Integer accountType) {
		this.accountType = accountType;
	}
	public Integer getFailedRetransmission() {
		return failedRetransmission;
	}
	public void setFailedRetransmission(Integer failedRetransmission) {
		this.failedRetransmission = failedRetransmission;
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
	public String getAppointmentFlag() {
		return appointmentFlag;
	}
	public void setAppointmentFlag(String appointmentFlag) {
		this.appointmentFlag = appointmentFlag;
	}
	public String getSignTip() {
		return signTip;
	}
	public void setSignTip(String signTip) {
		this.signTip = signTip;
	}
	public Double getCostQuantity() {
		return costQuantity;
	}
	public void setCostQuantity(Double costQuantity) {
		this.costQuantity = costQuantity;
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
	public String getReqMsgId() {
		return reqMsgId;
	}
	public void setReqMsgId(String reqMsgId) {
		this.reqMsgId = reqMsgId;
	}
	public String getMercReqTime() {
		return mercReqTime;
	}
	public void setMercReqTime(String mercReqTime) {
		this.mercReqTime = mercReqTime;
	}
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
}