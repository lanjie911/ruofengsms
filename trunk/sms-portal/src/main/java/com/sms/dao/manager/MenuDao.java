package com.sms.dao.manager;

import java.util.List;

import com.sms.annotation.DataSource;
import com.sms.entity.manager.Menu;


public interface MenuDao {
	@DataSource("portal")
	public List<Menu> getAllMenus();
}
