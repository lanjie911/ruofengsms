package com.sms.entity.manager;

import java.io.Serializable;

public class RolePermission implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1251188709925535269L;
	public static final String STATUS_OPEN = "O";
	public static final String STATUS_CLOSED ="C";
	
	
	
	private long role_permission_id;
	private long role_id;
	private long permission_id;
	public long getRole_permission_id() {
		return role_permission_id;
	}
	public void setRole_permission_id(long role_permission_id) {
		this.role_permission_id = role_permission_id;
	}
	public long getRole_id() {
		return role_id;
	}
	public void setRole_id(long role_id) {
		this.role_id = role_id;
	}
	public long getPermission_id() {
		return permission_id;
	}
	public void setPermission_id(long permission_id) {
		this.permission_id = permission_id;
	}
	
}
