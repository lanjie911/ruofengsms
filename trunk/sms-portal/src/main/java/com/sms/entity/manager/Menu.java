package com.sms.entity.manager;

import java.io.Serializable;

public class Menu implements Serializable {

	public static final String STATUS_OPEN = "O";
	public static final String STATUS_CLOSED = "C";
	
	private static final long serialVersionUID = -4473570334533637156L;
	
	private long menuId;
	private String hierarchyNo;
	private String menuName;
	private String menuCode;
	private long menuLevel;
	private String menuUrl;
	private long menuOrder;
	private long parentMenuId;
	private String moduleCode;
	private String status;
	private String createDatetime;
	private String updateDatetime;
	
	public long getMenuId() {
		return menuId;
	}
	public void setMenuId(long menuId) {
		this.menuId = menuId;
	}
	public String getHierarchyNo() {
		return hierarchyNo;
	}
	public void setHierarchyNo(String hierarchyNo) {
		this.hierarchyNo = hierarchyNo;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public String getMenuCode() {
		return menuCode;
	}
	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}
	public String getMenuUrl() {
		return menuUrl;
	}
	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}
	public void setParentMenuId(int parentMenuId) {
		this.parentMenuId = parentMenuId;
	}
	public String getModuleCode() {
		return moduleCode;
	}
	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}
	public long getParentMenuId() {
		return parentMenuId;
	}
	public void setParentMenuId(long parentMenuId) {
		this.parentMenuId = parentMenuId;
	}
	public long getMenuLevel() {
		return menuLevel;
	}
	public void setMenuLevel(long menuLevel) {
		this.menuLevel = menuLevel;
	}
	public long getMenuOrder() {
		return menuOrder;
	}
	public void setMenuOrder(long menuOrder) {
		this.menuOrder = menuOrder;
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
