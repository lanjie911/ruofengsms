package com.sms.service.manager;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sms.criteria.manager.RoleCriteria;
import com.sms.dao.manager.PermissionDao;
import com.sms.dao.manager.RoleDao;
import com.sms.entity.manager.Permission;
import com.sms.entity.manager.Role;


@Service
public class RoleService {
	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	private PermissionDao permissionDao;
	
	public List<Role> getRolesByModuleCode(String moduleCode) {
		return roleDao.getRolesByModuleCode(moduleCode);
	}

	public List<Role> getRolesByUserId(long userId) {
		return roleDao.getRolesByUserId(userId);
	}
	
	public List<Role> query(RoleCriteria criteria) {
		return roleDao.query(criteria);
	}
	
	
	@Transactional
	public Map<String, Object> addRole(Role role, Map<String, Object> result) {
		Role temp =roleDao.getRolesByRoleName(role.getName()) ;
		if(null != temp){
			result.put("success", false);
			result.put("message", "the record has been exists");
		}else{
			roleDao.insert(role);
			if(role.getMenuSelect()!= null ){
				String[] menuSelects = role.getMenuSelect().split(";|,");
				for (String menuId : menuSelects) {
					roleDao.insertRoleMenu(role.getId(), Long.parseLong(menuId));
				}
			}
			if(role.getRolePermission() != null){
				String[] rolePermissions = role.getRolePermission().split(";|,");
				
				for (String permissionId : rolePermissions) {
					roleDao.insertRolePermission(role.getId(), Long.parseLong(permissionId));
				}
			}
			result.put("success", true);
			result.put("message", "Add param successfully.");
		}

		
		return result;
	}
	
	@Transactional
	public Map<String, Object> editRole(Role role, Map<String, Object> result){
		
		Role temp =roleDao.getRolesByRoleName(role.getName()) ;
		if(null != temp){
			roleDao.update(role);
			roleDao.deleteAllRoleMenu(role.getId());
			roleDao.deleteAllRolePermission(role.getId());
			
			if(role.getMenuSelect()!= null ){
				String[] menuSelects = role.getMenuSelect().split(";|,");
				for (String menuId : menuSelects) {
					roleDao.insertRoleMenu(role.getId(), Long.parseLong(menuId));
				}
			}
			if(role.getRolePermission() != null){
				String[] rolePermissions = role.getRolePermission().split(";|,");
				
				for (String permissionId : rolePermissions) {
					roleDao.insertRolePermission(role.getId(), Long.parseLong(permissionId));
				}
			}
		}else{
			roleDao.insert(role);
			if(role.getMenuSelect()!= null ){
				String[] menuSelects = role.getMenuSelect().split(";|,");
				for (String menuId : menuSelects) {
					roleDao.insertRoleMenu(role.getId(), Long.parseLong(menuId));
				}
			}
			if(role.getRolePermission() != null){
				String[] rolePermissions = role.getRolePermission().split(";|,");
				
				for (String permissionId : rolePermissions) {
					roleDao.insertRolePermission(role.getId(), Long.parseLong(permissionId));
				}
			}
		}
		result.put("success", true);
		
		return result;
	}
	
	public Role getRoleByRoleId(Long roleId) {
		return roleDao.getById(roleId);
	}
	
	public List<Permission> getPermissionByRoleId(long roleId) {
		return permissionDao.getPermissionByRoleId(roleId);
	}
}
