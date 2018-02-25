package com.sms.entity;

import java.io.Serializable;
/**
 * @author Cao
 * 通道
 */
public class ConvinceIp implements Serializable{

	private static final long serialVersionUID = -1L;
	
	private Long ipId;
	private String ipAddr;
	
	public ConvinceIp(){}
	
	public ConvinceIp(String ipAddr) {
		this.setIpAddr(ipAddr);
	}

	public Long getIpId() {
		return ipId;
	}

	public void setIpId(Long ipId) {
		this.ipId = ipId;
	}

	public String getIpAddr() {
		return ipAddr;
	}

	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}
	
}
