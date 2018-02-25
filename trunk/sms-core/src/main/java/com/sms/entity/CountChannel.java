package com.sms.entity;

import java.io.Serializable;

public class CountChannel implements Serializable{

	private static final long serialVersionUID = 2785011279212870807L;
	private String channelId;
	private String channelName;
	private Long sumCount;
	private Long sucCount;
	private Long failCount;
	
	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	public Long getSumCount() {
		return sumCount;
	}
	public void setSumCount(Long sumCount) {
		this.sumCount = sumCount;
	}
	public Long getSucCount() {
		return sucCount;
	}
	public void setSucCount(Long sucCount) {
		this.sucCount = sucCount;
	}
	public Long getFailCount() {
		return failCount;
	}
	public void setFailCount(Long failCount) {
		this.failCount = failCount;
	}
}