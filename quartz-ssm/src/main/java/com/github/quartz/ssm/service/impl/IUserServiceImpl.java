package com.github.quartz.ssm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.quartz.ssm.dao.IUserDao;
import com.github.quartz.ssm.entity.User;
import com.github.quartz.ssm.service.IUserService;

@Service("userService")
public class IUserServiceImpl  implements IUserService{

	@Autowired
	public IUserDao userDao;
	
	@Override
	public User getUserById(int id) {
		return userDao.selectByPrimaryKey(id);
	}

	@Override
	public User getUser(User user) {
	    
	    User user2 = new User("admin", "123456");
	    user2.setAge(24);
	    user2.setId(1);
//		return userDao.findUser(user);
	    return user2;
	}

}
