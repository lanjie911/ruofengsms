package com.sms.dao.manager;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sms.annotation.DataSource;
import com.sms.criteria.AbstractCriteria;
import com.sms.entity.manager.Role;





public interface RoleDao {
	@DataSource("boss")
	public Role getById(@Param("id") Long id);
	@DataSource("boss")
	public List<Role> query(AbstractCriteria criteria);
	@DataSource("boss")
	public int insert(Role t);
	@DataSource("boss")
	public int update(Role t);
	@DataSource("boss")
	public List<Role> getRolesByModuleCode(String moduleCode);
	@DataSource("boss")
	public List<Role> getRolesByUserId(Long userId);
	@DataSource("boss")
	public void insertRoleMenu(@Param("roleId")long roleId, @Param("menuId")long menuId);
	@DataSource("boss")
	public void insertRolePermission(@Param("roleId")long roleId, @Param("permissionId")long permissionId);
	@DataSource("boss")
	public void deleteAllRoleMenu(@Param("roleId")long roleId);
	@DataSource("boss")
	public void deleteAllRolePermission(@Param("roleId")long roleId);
	@DataSource("boss")
	public Role getRolesByRoleName(@Param("name")String name);
}
