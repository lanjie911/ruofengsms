package com.sms.entity.manager;

import java.io.Serializable;

public class RoleMenu implements Serializable {
	private static final long serialVersionUID = -531620740390022595L;
	public static final String STATUS_OPEN = "O";
	public static final String STATUS_CLOSED ="C";

	private long role_menu_id;
	private long role_id;
	private long menu_id;
	public long getRole_menu_id() {
		return role_menu_id;
	}
	public void setRole_menu_id(long role_menu_id) {
		this.role_menu_id = role_menu_id;
	}
	public long getRole_id() {
		return role_id;
	}
	public void setRole_id(long role_id) {
		this.role_id = role_id;
	}
	public long getMenu_id() {
		return menu_id;
	}
	public void setMenu_id(long menu_id) {
		this.menu_id = menu_id;
	}
	
}
