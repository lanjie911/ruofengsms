package com.sms.entity.bizdict;

import java.io.Serializable;

public class OrderFlag implements Serializable {

	private static final long serialVersionUID = -2566684010870114519L;
	private Integer orderFlagId;
	private Integer orderFlag;
	private String orderFlagDes;
	
	public Integer getOrderFlagId() {
		return orderFlagId;
	}
	public Integer getOrderFlag() {
		return orderFlag;
	}
	public String getOrderFlagDes() {
		return orderFlagDes;
	}
	public void setOrderFlagId(Integer orderFlagId) {
		this.orderFlagId = orderFlagId;
	}
	public void setOrderFlag(Integer orderFlag) {
		this.orderFlag = orderFlag;
	}
	public void setOrderFlagDes(String orderFlagDes) {
		this.orderFlagDes = orderFlagDes;
	}
}