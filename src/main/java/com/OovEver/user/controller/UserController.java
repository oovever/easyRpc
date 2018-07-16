package com.OovEver.user.controller;

import com.OovEver.netty.util.Response;
import com.OovEver.netty.util.ResponseUtil;
import com.OovEver.user.Bean.User;
import com.OovEver.user.Service.UserService;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author OovEver
 * 2018/7/11 13:10
 */
@Controller
public class UserController {
    @Resource
    private UserService userService;
    public Response saveUser(User user) {
        userService.save(user);
        return ResponseUtil.createSuccessResult(user);
    }
    public Response saveUsers(List<User> users) {
        userService.saveList(users);
        return ResponseUtil.createSuccessResult(users);
    }
}
