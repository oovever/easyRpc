package com.OovEver.user.controller;

import com.OovEver.user.Bean.User;
import com.OovEver.user.Service.UserService;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * @author OovEver
 * 2018/7/11 13:10
 */
@Controller
public class UserController {
    @Resource
    private UserService userService;
    public void saveUser(User user) {
        userService.save(user);
    }
}
