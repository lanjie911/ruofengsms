package com.sms.entity;

import java.io.Serializable;
/**
 * 普通发送记录
 */
public class PlainSendRecord implements Serializable {

	private static final long serialVersionUID = -1L;
	
	private Long recordId;
	private Long batchNo;				// 批次号
	private Long channelId;
	private String channelName;
	private Long accountNo;
	private String merchantNameAbbreviation;
	private Integer accountType;
	private Integer failedRetransmission;
	private String mobile;
	private String content;
	private Integer sendStatus;
	private String sendMsg;
	private Integer failedNum;
	private String createDatetime;
	private String respDatetime;
	private String messageId;
	private String reservationDatetime;
	private String signTip;
	private String province;
	private String city;
	private String isp;
	private String reqMsgId;
	
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

	public PlainSendRecord(){}
	
	public PlainSendRecord(Long channelId, String channelName, Long accountNo, String merchantNameAbbreviation,
			Integer accountType, Integer failedRetransmission, String mobile, String content, Integer sendStatus,
			String sendMsg, String respDatetime,String createDatetime, String messageId,String reservationDatetime,String signTip) {
		this.channelId = channelId;
		this.createDatetime = createDatetime;
		this.channelName = channelName;
		this.accountNo = accountNo;
		this.merchantNameAbbreviation = merchantNameAbbreviation;
		this.accountType = accountType;
		this.failedRetransmission = failedRetransmission;
		this.mobile = mobile;
		this.content = content;
		this.sendStatus = sendStatus;
		this.sendMsg = sendMsg;
		this.respDatetime = respDatetime;
		this.messageId = messageId;
		this.reservationDatetime = reservationDatetime;
		this.signTip = signTip;
	}

	public Long getRecordId() {
		return recordId;
	}

	public Long getBatchNo() {
		return batchNo;
	}

	public Long getChannelId() {
		return channelId;
	}

	public String getChannelName() {
		return channelName;
	}

	public Long getAccountNo() {
		return accountNo;
	}

	public String getMerchantNameAbbreviation() {
		return merchantNameAbbreviation;
	}

	public Integer getAccountType() {
		return accountType;
	}

	public Integer getFailedRetransmission() {
		return failedRetransmission;
	}

	public String getMobile() {
		return mobile;
	}

	public String getContent() {
		return content;
	}

	public Integer getSendStatus() {
		return sendStatus;
	}

	public String getSendMsg() {
		return sendMsg;
	}

	public Integer getFailedNum() {
		return failedNum;
	}

	public String getCreateDatetime() {
		return createDatetime;
	}

	public String getRespDatetime() {
		return respDatetime;
	}

	public String getMessageId() {
		return messageId;
	}

	public String getReservationDatetime() {
		return reservationDatetime;
	}

	public String getSignTip() {
		return signTip;
	}

	public String getReqMsgId() {
		return reqMsgId;
	}

	public void setRecordId(Long recordId) {
		this.recordId = recordId;
	}

	public void setBatchNo(Long batchNo) {
		this.batchNo = batchNo;
	}

	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public void setAccountNo(Long accountNo) {
		this.accountNo = accountNo;
	}

	public void setMerchantNameAbbreviation(String merchantNameAbbreviation) {
		this.merchantNameAbbreviation = merchantNameAbbreviation;
	}

	public void setAccountType(Integer accountType) {
		this.accountType = accountType;
	}

	public void setFailedRetransmission(Integer failedRetransmission) {
		this.failedRetransmission = failedRetransmission;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setSendStatus(Integer sendStatus) {
		this.sendStatus = sendStatus;
	}

	public void setSendMsg(String sendMsg) {
		this.sendMsg = sendMsg;
	}

	public void setFailedNum(Integer failedNum) {
		this.failedNum = failedNum;
	}

	public void setCreateDatetime(String createDatetime) {
		this.createDatetime = createDatetime;
	}

	public void setRespDatetime(String respDatetime) {
		this.respDatetime = respDatetime;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public void setReservationDatetime(String reservationDatetime) {
		this.reservationDatetime = reservationDatetime;
	}

	public void setSignTip(String signTip) {
		this.signTip = signTip;
	}

	public void setReqMsgId(String reqMsgId) {
		this.reqMsgId = reqMsgId;
	}

	@Override
	public String toString() {
		return "PlainSendRecord [recordId=" + recordId + ", batchNo=" + batchNo + ", channelId=" + channelId
				+ ", channelName=" + channelName + ", accountNo=" + accountNo + ", merchantNameAbbreviation="
				+ merchantNameAbbreviation + ", accountType=" + accountType + ", failedRetransmission="
				+ failedRetransmission + ", mobile=" + mobile + ", content=" + content + ", sendStatus=" + sendStatus
				+ ", sendMsg=" + sendMsg + ", failedNum=" + failedNum + ", createDatetime=" + createDatetime
				+ ", respDatetime=" + respDatetime + ", messageId=" + messageId + ", reservationDatetime="
				+ reservationDatetime + ", signTip=" + signTip + ", province=" + province + ", city=" + city + ", isp="
				+ isp + ", reqMsgId=" + reqMsgId + "]";
	}
}