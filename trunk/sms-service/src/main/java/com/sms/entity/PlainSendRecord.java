package com.sms.entity;

import java.io.Serializable;

/**
 * @author Cao 普通发送记录
 */
public class PlainSendRecord implements Serializable {

	private static final long serialVersionUID = -1L;

	private Long recordId;
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
	private String createDatetime; // xml中未currentdatetime，不需要赋值，提供给商户请求时间使用。
	private String respDatetime;
	private String messageId;
	private String reservationDatetime;
	private String signTip;
	private String province;
	private String city;
	private String isp;
	private String reqMsgId;

	public PlainSendRecord() {
	}

	public PlainSendRecord(Long channelId, String channelName, Long accountNo, String merchantNameAbbreviation,
			Integer accountType, Integer failedRetransmission, String mobile, String content, Integer sendStatus,
			String sendMsg, String respDatetime, String createDatetime, String messageId, String reservationDatetime,
			String signTip) {
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

	public PlainSendRecord(String mobile, Integer sendStatus, String sendMsg, Integer failedNum, String reqMsgId,
			String respDatetime) {
		this.mobile = mobile;
		this.sendStatus = sendStatus;
		this.sendMsg = sendMsg;
		this.failedNum = failedNum;
		this.reqMsgId = reqMsgId;
		this.respDatetime = respDatetime;
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

	public void setRespDatetime(String respDatetime) {
		this.respDatetime = respDatetime;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getReservationDatetime() {
		return reservationDatetime;
	}

	public void setReservationDatetime(String reservationDatetime) {
		this.reservationDatetime = reservationDatetime;
	}

	public String getSignTip() {
		return signTip;
	}

	public void setSignTip(String signTip) {
		this.signTip = signTip;
	}

	public String getReqMsgId() {
		return reqMsgId;
	}

	public void setReqMsgId(String reqMsgId) {
		this.reqMsgId = reqMsgId;
	}

}
