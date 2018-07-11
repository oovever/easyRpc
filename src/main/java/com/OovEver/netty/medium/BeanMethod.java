package com.OovEver.netty.medium;

import java.lang.reflect.Method;

/**
 * @author OovEver
 * 2018/7/11 13:44
 */
public class BeanMethod {
    private Object bean;
    private Method method;

    public Object getBean() {
        return bean;
    }

    public void setBean(Object bean) {
        this.bean = bean;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }
}
