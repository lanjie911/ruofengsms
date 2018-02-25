package com.sms.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sms.criteria.manager.RoleCriteria;
import com.sms.entity.manager.Permission;
import com.sms.entity.manager.Role;
import com.sms.entity.manager.TreeNode;
import com.sms.service.manager.LoginService;
import com.sms.service.manager.PermissionService;
import com.sms.service.manager.RoleService;


@Controller
@RequestMapping("/role")
@SuppressWarnings("unchecked")
public class RoleController {
	@Autowired
	RoleService roleService;
	
	@Autowired
	PermissionService permissionService;
	
	@Autowired
	LoginService loginService;
	
	@RequestMapping("/query.ajax")
	public @ResponseBody
	Map<String, Object> query(RoleCriteria criteria, HttpSession session) {
		Map<String,Object> map = new HashMap<String,Object>(2);
		
		List<Role> list = roleService.query(criteria);
		
		map.put("total",criteria.getTotalCount());
		map.put("rows",list);
		return map;
	}
	
	
	@ResponseBody
	@RequestMapping("/getPermissionAll.ajax")
	public List<Permission> getPermissionAll(HttpSession session) {
		return permissionService.getAllPermission();
	}
	
	@RequestMapping("/getAllMenuTreeNodes.ajax")
	public @ResponseBody
	List<TreeNode> getAllMenuTreeNodes(HttpServletRequest req,HttpServletResponse res, HttpSession session) {
		return loginService.getAllMenuTreeNodes();
	}
	
	@RequestMapping("/getAllMenuTreeNodesForEdit.ajax")
	public @ResponseBody
	List<TreeNode> getAllMenuTreeNodesForEdit(@RequestParam("roleId") String roleId,HttpServletRequest req,
			HttpServletResponse res, HttpSession session) {
		return loginService.getAllMenuTreeNodesForEdit(Long.parseLong(roleId));
	}
	
	@RequestMapping("/addRole.ajax")
	public @ResponseBody Map<String, Object> addRole(@Valid Role role,BindingResult error,
			HttpSession session) {
		
		Map<String, Object> result = new HashMap<String, Object>(2);
		
		try {
			result =roleService.addRole(role,result);
		} catch(Exception e) {
			result.put("success", false);
			result.put("message", "操作失败:" + e.getMessage());
		}
		
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/editRole.ajax")
	public Map<String, Object> editRole(@Valid Role role,BindingResult error, HttpSession session) {
		
		Map<String, Object> result = new HashMap<String, Object>(2);
		
		try {
			result =roleService.editRole(role,result);
		} catch(Exception e) {
			result.put("success", false);
			result.put("message", "操作失败:" + e.getMessage());
		}
		
		return result;
	}
	
	@RequestMapping("/getRoleByRoleId.ajax")
	public @ResponseBody Role getRoleByRoleId(@RequestParam("roleId") String roleId, HttpSession session) {
		Role role = roleService.getRoleByRoleId(Long.parseLong(roleId));
		return role;
	}
	
	@RequestMapping("/getPermissionByRoleId.ajax")
	public @ResponseBody List<Permission> getPermissionByRoleId(@RequestParam("roleId") String roleId, HttpSession session) {
		return roleService.getPermissionByRoleId(Long.parseLong(roleId));
	}
}
