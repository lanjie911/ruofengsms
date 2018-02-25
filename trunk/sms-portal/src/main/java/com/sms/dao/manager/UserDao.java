package com.sms.dao.manager;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sms.annotation.DataSource;
import com.sms.criteria.AbstractCriteria;
import com.sms.entity.manager.User;



public interface UserDao{
	
	@DataSource("portal")
	public User getById(@Param("id") Long id);
	@DataSource("portal")
	public int insert(User t);
	@DataSource("portal")
	public int update(User t);
	@DataSource("portal")
	public List<User> query(AbstractCriteria criteria);
	@DataSource("portal")
	public User checkUser(@Param("userName") String userName, @Param("password") String password);
	@DataSource("portal")
	public void insertUserRole(@Param("userId")long userId, @Param("roleId")long roleId);
	@DataSource("portal")
	public void deleteUserRole(@Param("userId")long userId, @Param("roleId")long roleId);
	@DataSource("portal")
	public void deleteAllUserRole(@Param("userId")long userId);
	@DataSource("portal")
	public void updatePassword(@Param("userId")long userId, @Param("newPassword")String newPassword);
	@DataSource("portal")
	public User getUserbyLoginname(@Param("userName") String userName);
}
