package com.sms.entity.manager;

import java.io.Serializable;

public class Permission implements Serializable {

	public static final String STATUS_OPEN="O";
	public static final String STATUS_CLOSED="C";	
	
	private static final long serialVersionUID = 1346915885824342308L;

	private long id;
	private String name;
	private String moduleCode;
	private String permissionCode;
	private String urlFilter; //supports multiple filters by using ';' or ','
	private String status;
	private String statusName;
	private String createDatetime;
	private String updateDatetime;
	
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
	public String getModuleCode() {
		return moduleCode;
	}
	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}
	public String getPermissionCode() {
		return permissionCode;
	}
	public void setPermissionCode(String permissionCode) {
		this.permissionCode = permissionCode;
	}
	public String getUrlFilter() {
		return urlFilter;
	}
	public void setUrlFilter(String urlFilter) {
		this.urlFilter = urlFilter;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
}
