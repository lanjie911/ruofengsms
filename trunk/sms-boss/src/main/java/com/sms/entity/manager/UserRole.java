package com.sms.entity.manager;

import java.io.Serializable;

public class UserRole implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3479050755622492259L;
	public static final String STATUS_OPEN = "O";
	public static final String STATUS_CLOSED ="C";
	
	
	
	private long user_role_id;
	private long role_id;
	private long user_id;
	public long getUser_role_id() {
		return user_role_id;
	}
	public void setUser_role_id(long user_role_id) {
		this.user_role_id = user_role_id;
	}
	public long getRole_id() {
		return role_id;
	}
	public void setRole_id(long role_id) {
		this.role_id = role_id;
	}
	public long getUser_id() {
		return user_id;
	}
	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}
	
	
}
