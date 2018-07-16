package com.OovEver.user.controller;

import com.OovEver.netty.client.ClientRequest;
import com.OovEver.netty.util.Response;
import com.OovEver.netty.client.TcpClient;
import com.OovEver.user.Bean.User;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author OovEver
 * 2018/7/16 9:43
 */
public class UserControllerTest {

    @Test
    public void saveUser() {
        ClientRequest request = new ClientRequest();
        User user = new User();
        user.setId(1);
        user.setName("张三");
        request.setCommand("com.OovEver.user.controller.UserController.saveUser");
        request.setContent(user);
        Response response = TcpClient.send(request);
        System.out.println(response.getResult());
    }
    @Test
    public void saveUsers() {
        ClientRequest request = new ClientRequest();
        List<User> users = new ArrayList<User>();
        User user = new User();
        user.setId(1);
        user.setName("张三");
        users.add(user);
        request.setCommand("com.OovEver.user.controller.UserController.saveUsers");
        request.setContent(users);
        Response response = TcpClient.send(request);
        System.out.println(response.getResult());
    }
}