package com.sms.entity.manager;

import java.io.Serializable;

public class Role implements Serializable {
	
	private static final long serialVersionUID = 7581099124083186340L;
	
	private long id;
	private String name;
	private String desc;
	private String status;
	private String statusName;
	private String moduleCode;
	private String createDatetime;
	private String updateDatetime;
	private String menuSelect;
	private String rolePermission;
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
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public String getModuleCode() {
		return moduleCode;
	}
	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}
	public String getCreateDatetime() {
		return createDatetime;
	}
	public void setCreateDatetime(String createDatetime) {
		this.createDatetime = createDatetime;
	}
	public String getUpdateDatetime() {
		return updateDatetime;
	}
	public void setUpdateDatetime(String updateDatetime) {
		this.updateDatetime = updateDatetime;
	}
	public String getMenuSelect() {
		return menuSelect;
	}
	public void setMenuSelect(String menuSelect) {
		this.menuSelect = menuSelect;
	}
	public String getRolePermission() {
		return rolePermission;
	}
	public void setRolePermission(String rolePermission) {
		this.rolePermission = rolePermission;
	}

}
