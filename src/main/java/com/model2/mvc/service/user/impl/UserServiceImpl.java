package com.model2.mvc.service.user.impl;

import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.dao.UserDAO;


public class UserServiceImpl implements UserService{
	
	///Field
	private UserDAO userDao;
	
	///Constructor
	public UserServiceImpl() {
		userDao=new UserDAO();
	}

	///Method
	public void addUser(User user) throws Exception {
		userDao.insertUser(user);
	}

	public User loginUser(User user) throws Exception {
			User dbUser=userDao.findUser(user.getUserId());

			if(! dbUser.getPassword().equals(user.getPassword())){
				throw new Exception("�α��ο� �����߽��ϴ�.");
			}
			
			return dbUser;
	}

	public User getUser(String userId) throws Exception {
		return userDao.findUser(userId);
	}

	public Map<String,Object> getUserList(Search search) throws Exception {
		return userDao.getUserList(search);
	}

	public void updateUser(User user) throws Exception {
		userDao.updateUser(user);
	}

	public boolean checkDuplication(String userId) throws Exception {
		boolean result=true;
		User user=userDao.findUser(userId);
		if(user != null) {
			result=false;
		}
		return result;
	}
}