package com.sms.entity.smsmanager;

import java.io.Serializable;
/**
 * @author Cao
 * 通道
 */
public class Ip implements Serializable{

	private static final long serialVersionUID = -1L;
	
	private Long ipId;
	private String ipAddress;
	
	public Long getIpId() {
		return ipId;
	}
	public void setIpId(Long ipId) {
		this.ipId = ipId;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	
	
	
}
