package org.ssm.dufy.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.quartz.ssm.entity.User;
import com.github.quartz.ssm.service.IUserService;

/**
 * 配置spring和junit整合，junit启动时加载springIOC容器 spring-test,junit
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext.xml" })
public class IUserServiceTest {

    @Autowired
    public IUserService userService;

    @Test
    public void getUserByIdTest() {

        User user = userService.getUserById(1);
        System.out.println(user.getUserName());
    }

}
