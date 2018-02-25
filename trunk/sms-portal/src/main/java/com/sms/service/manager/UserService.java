package com.sms.service.manager;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sms.criteria.manager.UserCriteria;
import com.sms.dao.manager.UserDao;
import com.sms.entity.manager.User;
import com.sms.util.SecurityUtil;

@Service
public class UserService {

	@Autowired
	private UserDao userDao;

	public User getUserByUserId(Long userId) {
		return userDao.getById(userId);
	}

	@Transactional
	public Map<String, Object> addUser(User user,Map<String, Object> result) {
		setMaskedPassword(user);
		User temp = userDao.getUserbyLoginname(user.getLoginName()) ;
		if(null != temp){
			result.put("success", false);
			result.put("message", "the record has been exists");
		}else{
			userDao.insert(user);
			result.put("success", true);
			result.put("message", "Add user successfully.");
			
		}
		return result;
	}

	@Transactional
	public Map<String, Object> editUser(User user,Map<String, Object> result) {
			setMaskedPassword(user);
			int i = userDao.update(user);
			if(i==1){
				result.put("success", true);
				result.put("message", "edit user successfully.");
			}else{
				result.put("success", false);
				result.put("message", "edit user failed.");
			}
		return result;
	}

	public List<User> query(UserCriteria criteria) {
		return userDao.query(criteria);
	}


	public void changePassword(long userId, String newPassword) {
		User user = userDao.getById(userId);
		user.setLoginPassword(newPassword);
		setMaskedPassword(user);
		userDao.updatePassword(userId, user.getLoginPassword());
	}

	public String changePasswordForLoginUser(long userId, String password,
			String newPassword) {
		User user = userDao.getById(userId);

		String selkey = user.getLoginName();
		String sel = SecurityUtil.genSalt(selkey);

		boolean success = SecurityUtil.matchPassword(user.getLoginPassword(),
				sel, password);
		if (false == success) {
			// throw new RuntimeException("原始密码错误");
			return "1";
		}
		user.setLoginPassword(newPassword);
		setMaskedPassword(user);
		userDao.updatePassword(userId, user.getLoginPassword());
		return "0";
	}

	public String setMaskedPassword(User user) {
		String password = user.getLoginPassword();
		String selkey = user.getLoginName();
		String sel = SecurityUtil.genSalt(selkey);
		String maskedPassword = SecurityUtil.calcMaskedPassword(sel, password);
		user.setLoginPassword(maskedPassword);
		return maskedPassword;
	}
}
