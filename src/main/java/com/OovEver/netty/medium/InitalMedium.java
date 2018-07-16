package com.OovEver.netty.medium;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.lang.reflect.Method;
import java.security.Key;
import java.util.Map;

/**
 * 中介者模式
 * bean初始化之后 执行这个方法
 * @author OovEver
 * 2018/7/9 23:30
 */
@Component
public class InitalMedium implements BeanPostProcessor {
//初始化之前
    public Object postProcessBeforeInitialization(Object o, String s) throws BeansException {
        return o;
    }
//初始化之后
    public Object postProcessAfterInitialization(Object o, String s) throws BeansException {
        if (o.getClass().isAnnotationPresent(Controller.class)) {
            Method[] methods = o.getClass().getDeclaredMethods();
            for (Method m : methods) {
                String key = o.getClass().getName() + "." + m.getName();
                Map<String, BeanMethod> beanMethodMap = Media.beanMap;
                BeanMethod beanMethod = new BeanMethod();
                beanMethod.setBean(o);
                beanMethod.setMethod(m);
                beanMethodMap.put(key, beanMethod);
            }
        }
        return o;
    }
}
