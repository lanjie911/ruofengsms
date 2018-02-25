package com.sms.entity.manager;

import java.io.Serializable;

public class UserStatus implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1587606270338617662L;

	private long id;
	private String name;
	private String code;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	
}
