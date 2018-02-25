package com.sms.service.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sms.criteria.manager.PermissionCriteria;
import com.sms.dao.manager.PermissionDao;
import com.sms.entity.manager.Permission;


@Service
public class PermissionService {
	
	@Autowired
	PermissionDao permissionDao;
	
	public List<Permission> getAllPermission() {
		PermissionCriteria criteria = new PermissionCriteria();
		return permissionDao.query(criteria);
	}
}