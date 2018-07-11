package com.OovEver.netty.server;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 采用Spring启动Server
 * @author OovEver
 * 2018/7/9 23:12
 */
@Configuration
@ComponentScan("com.OovEver")
public class SpringServer {
    public static void main(String[] args) throws InterruptedException {
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringServer.class);
//        context.wait();
    }
}
