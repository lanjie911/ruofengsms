package com.sms.dao.manager;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sms.annotation.DataSource;
import com.sms.entity.manager.Menu;


public interface MenuDao {
	@DataSource("boss")
	public List<Menu> getMenusByUserId(@Param("userId")long userId);
	@DataSource("boss")
	public List<Menu> getUsedMenusByRoleId(@Param("roleId")long roleId);
	@DataSource("boss")
	public List<Menu> getAllMenus();
}
