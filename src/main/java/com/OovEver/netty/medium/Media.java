package com.OovEver.netty.medium;

import com.OovEver.netty.handler.param.ServerRequest;
import com.OovEver.netty.util.Response;
import com.alibaba.fastjson.JSONObject;
import org.springframework.cglib.beans.BeanMap;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author OovEver
 * 2018/7/11 13:43
 */
public class Media {
    public static Map<String,BeanMethod> beanMap;
    static {
        beanMap = new HashMap<String, BeanMethod>();
    }

    private static Media m = null;
    private Media(){

    }
    public static Media newInstance(){
        if (m == null) {
            m = new Media();
        }
        return m;
    }

    /**
     * 反射处理业务
     * @param request 要处理的请求
     * @return 返回处理结果
     */
    public Response process(ServerRequest request) {
        Response result = null;
        try {
            String command = request.getCommand();
            BeanMethod beanMethod = beanMap.get(command);
            if (beanMethod == null) {
                return null;
            }
            Object bean = beanMethod.getBean();
            Method m = beanMethod.getMethod();
            Class paramType=m.getParameterTypes()[0];
            Object content = request.getContent();
            Object args=JSONObject.parseObject(JSONObject.toJSONString(content), paramType);
            result=(Response) m.invoke(bean, args);
            result.setId(request.getId());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return result;
    }
}
