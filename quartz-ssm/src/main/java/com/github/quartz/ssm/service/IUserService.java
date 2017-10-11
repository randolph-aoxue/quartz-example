package com.github.quartz.ssm.service;

import com.github.quartz.ssm.entity.User;

public interface IUserService {

    User getUserById(int id);

    User getUser(User user);
}
