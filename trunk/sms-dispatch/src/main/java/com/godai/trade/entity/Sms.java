package com.godai.trade.entity;

import java.io.Serializable;
/**
 * @author Cao
 * 通道
 */
public class Sms implements Serializable{

	private static final long serialVersionUID = -1L;
	
	private Long recordId; //	记录编号
	private Long channelId; //	通道编号
	private String channelName; //	通道名称
	private Long accountNo; //	商户账户编号
	private String merchantNameAbbreviation; //	商户简称
	private Integer accountType; //	账户类型 100 - 行业账户 200 - 普通账户 300 - 全类型商户
	private Integer failedRetransmission; //	是否失败重发 100 - 是 200 - 否
	private String mobile; //	手机号
	private String content; //	短信内容
	private String reservationDatetime; //	预约发送时间
	private Integer sendStatus; //	发送状态 100 - 待处理101 - 处理中200 - 发送成功300 - 发送失败
	private String sendMsg; //	发送结果说明
	private Integer failedNum; //	失败计数
	private String createDatetime; //	创建时间
	private String respDatetime; //	响应时间
	private Integer sumNum; //	发送总数
	private Integer successNum	; //成功计数
	private Integer blacklistNum; //	黑名单基数
	private String messageId ;   //扣量比
	private Integer recordType; //	黑名单基数
	private String recordIdList; //	黑名单基数
	private String signTip; //	黑名单基数
	
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
	public Integer getRecordType() {
		return recordType;
	}
	public void setRecordType(Integer recordType) {
		this.recordType = recordType;
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
	public String getMessageId() {
		return messageId;
	}
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
	public String getRecordIdList() {
		return recordIdList;
	}
	public void setRecordIdList(String recordIdList) {
		this.recordIdList = recordIdList;
	}
	public String getSignTip() {
		return signTip;
	}
	public void setSignTip(String signTip) {
		this.signTip = signTip;
	}
	
}
