package com.sms.dao.manager;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sms.annotation.DataSource;
import com.sms.criteria.AbstractCriteria;
import com.sms.entity.manager.User;



public interface UserDao{
	
	@DataSource("boss")
	public User getById(@Param("id") Long id);
	@DataSource("boss")
	public int insert(User t);
	@DataSource("boss")
	public int update(User t);
	@DataSource("boss")
	public List<User> query(AbstractCriteria criteria);
	@DataSource("boss")
	public User checkUser(@Param("userName") String userName, @Param("password") String password);
	@DataSource("boss")
	public void insertUserRole(@Param("userId")long userId, @Param("roleId")long roleId);
	@DataSource("boss")
	public void deleteUserRole(@Param("userId")long userId, @Param("roleId")long roleId);
	@DataSource("boss")
	public void deleteAllUserRole(@Param("userId")long userId);
	@DataSource("boss")
	public void updatePassword(@Param("userId")long userId, @Param("newPassword")String newPassword);
	@DataSource("boss")
	public User getUserbyLoginname(@Param("userName") String userName);
}
