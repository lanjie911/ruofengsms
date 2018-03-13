package com.sms.entity;

import java.io.Serializable;

/**
 * @author Cao 发送记录回调表
 */
public class PlainSendResp implements Serializable {

	private static final long serialVersionUID = -1L;
	private Long respId;
	private String reqMsgId;
	private String mobile;
	private Integer sendStatus;
	private String sendMsg;
	private String respDatetime;
	public PlainSendResp( String reqMsgId, String mobile, Integer sendStatus, String sendMsg,
			String respDatetime) {
		super();
		this.reqMsgId = reqMsgId;
		this.mobile = mobile;
		this.sendStatus = sendStatus;
		this.sendMsg = sendMsg;
		this.respDatetime = respDatetime;
	}
	public Long getRespId() {
		return respId;
	}
	public void setRespId(Long respId) {
		this.respId = respId;
	}
	public String getReqMsgId() {
		return reqMsgId;
	}
	public void setReqMsgId(String reqMsgId) {
		this.reqMsgId = reqMsgId;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
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
	public String getRespDatetime() {
		return respDatetime;
	}
	public void setRespDatetime(String respDatetime) {
		this.respDatetime = respDatetime;
	}


}
