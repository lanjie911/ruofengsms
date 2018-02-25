package com.sms.service.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sms.dao.manager.MenuDao;
import com.sms.dao.manager.PermissionDao;
import com.sms.dao.manager.RoleDao;
import com.sms.dao.manager.UserDao;
import com.sms.entity.manager.Menu;
import com.sms.entity.manager.Permission;
import com.sms.entity.manager.TreeNode;
import com.sms.entity.manager.User;
import com.sms.util.SecurityUtil;


@Service
public class LoginService {
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	MenuDao menuDao;
	
	@Autowired
	PermissionDao permissionDao;
	
	@Autowired
	RoleDao roleDao;
	
	public User checkUser(String userName, String password) {
		User user=userDao.getUserbyLoginname(userName) ;
		if(user==null){
			 return null;
		}
		String selkey=SecurityUtil.genSalt(user.getLoginName());
		boolean flag=SecurityUtil.matchPassword(user.getLoginPassword(), selkey, password);
		if(flag){
			return user;
		}
		 return null;
	}
	
	public List<TreeNode> getAllMenuTreeNodes(){
		List<Menu> menus = menuDao.getAllMenus();
		
		List<TreeNode> list1 = new ArrayList<TreeNode>(); 
		List<TreeNode> list2 = new ArrayList<TreeNode>(); 
		
		TreeNode mtnAll = new TreeNode();
		mtnAll.setId("");
		mtnAll.setText("菜单");
		mtnAll.setChildren(list2);
		
		list1.add(mtnAll);
		
		for(int i = 0;i < menus.size();i++){
			if (menus.get(i).getMenuLevel() == 1) {
				TreeNode mtn = new TreeNode();
				mtn.setId(Long.toString(menus.get(i).getMenuId()));
				mtn.setText(menus.get(i).getMenuName());
				mtn.setChecked(false);
				List<TreeNode> list3 = new ArrayList<TreeNode>(); 
				mtn.setChildren(list3);
				for(int j = 0;j < menus.size(); j++){
					if ((2 == menus.get(j).getMenuLevel()) && (menus.get(j).getParentMenuId() == menus.get(i).getMenuId()))  {
						TreeNode mtn2 = new TreeNode();
						mtn2.setId(Long.toString(menus.get(j).getMenuId()));
						mtn2.setText(menus.get(j).getMenuName());
						mtn2.setChecked(false);
						list3.add(mtn2);
					}
				}
				list2.add(mtn);
			}
		}
		return list1;
	}
	
	public List<TreeNode> getAllMenuTreeNodesForEdit(Long roleId){
		List<Menu> menus = menuDao.getAllMenus();
		List<Menu> usedMenus = menuDao.getUsedMenusByRoleId(roleId);
		
		Map<String, Object> MenusMap = new HashMap<String, Object>(usedMenus.size());
		for(Menu um:usedMenus) {
			MenusMap.put(Long.toString(um.getMenuId()), um.getMenuName());
		}
		
		List<TreeNode> list1 = new ArrayList<TreeNode>(); 
		List<TreeNode> list2 = new ArrayList<TreeNode>(); 
		
		TreeNode mtnAll = new TreeNode();
		mtnAll.setId("");
		mtnAll.setText("菜单");
		mtnAll.setChildren(list2);
		
		list1.add(mtnAll);
		
		for(int i = 0;i < menus.size();i++){
			if (menus.get(i).getMenuLevel() == 1) {
				TreeNode mtn = new TreeNode();
				mtn.setId(Long.toString(menus.get(i).getMenuId()));
				mtn.setText(menus.get(i).getMenuName());
				List<TreeNode> list3 = new ArrayList<TreeNode>(); 
				mtn.setChildren(list3);
				for(int j = 0;j < menus.size(); j++){
					if ((2 == menus.get(j).getMenuLevel()) && (menus.get(j).getParentMenuId() == menus.get(i).getMenuId()))  {
						TreeNode mtn2 = new TreeNode();
						mtn2.setId(Long.toString(menus.get(j).getMenuId()));
						mtn2.setText(menus.get(j).getMenuName());
						if(null != MenusMap.get(Long.toString(menus.get(j).getMenuId()))){
							mtn2.setChecked(true);
						}
						list3.add(mtn2);
					}
				}
				list2.add(mtn);
			}
		}
		return list1;
	}
	
	
	public Map<String, Object> getUserMenus(long userId,String orgUnitHid) {
		
		List<Menu> menus = new ArrayList<Menu>();
		menus = menuDao.getMenusByUserId(userId);//add
		
		List<Object> parentMenus = new ArrayList<Object>();
		
		for(Menu m1:menus) {
			//get all level1 menus
			if (m1.getMenuLevel() == 1) {
				Map<String, Object> parentMenu = new HashMap<String, Object>();
				
				parentMenu.put("id", m1.getMenuId());
				parentMenu.put("name", m1.getMenuName());
				parentMenu.put("code", m1.getMenuCode());
				
				//get each parent menu's son
				List<Object> sonMenus = new ArrayList<Object>();
				for (Menu m2:menus) {
					if ((m2.getMenuLevel() == 2) && (m2.getParentMenuId() == m1.getMenuId()))  {
						Map<String, Object> sonMenu = new HashMap<String, Object>();
						sonMenu.put("id", m2.getMenuId());
						sonMenu.put("name", m2.getMenuName());
						sonMenu.put("code", m2.getMenuCode());
						sonMenu.put("url", m2.getMenuUrl());
						sonMenus.add(sonMenu);
					}
				}
				parentMenu.put("submenus", sonMenus);
				
				parentMenus.add(parentMenu);
			}
		}
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("menus", parentMenus);
		return result;	
	}
	
	public List<Permission> getUserPermissions(long userId) {
		return permissionDao.getPermissionsByUserId(userId);
	}
	
	/*public List<Permission> getUserPermissionsByOrgUnit(long userId,String orgUnitHid) {
		return permissionDao.getPermissionByOrgUnitHid(orgUnitHid);
	}
	
	public UserOrgUnit getUserOrgUnitByHid(String orgUnitHid,long userId) {
		return userOrgUnitDao.getByHid(orgUnitHid,userId);
	}*/
	
}
