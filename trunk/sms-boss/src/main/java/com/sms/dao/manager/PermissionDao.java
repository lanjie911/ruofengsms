package com.sms.dao.manager;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sms.annotation.DataSource;
import com.sms.criteria.AbstractCriteria;
import com.sms.entity.manager.Permission;

public interface PermissionDao{
	@DataSource("boss")
	public List<Permission> getPermissionsByUserId(@Param("userId") long userId);
	@DataSource("boss")
	public List<Permission> getPermissionByRoleId(@Param("roleId") long roleId);
	@DataSource("boss")
	public List<Permission> query(AbstractCriteria criteria);
}
