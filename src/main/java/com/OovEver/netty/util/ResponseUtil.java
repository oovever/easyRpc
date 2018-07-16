package com.OovEver.netty.util;

/**
 * 响应工具类
 * @author OovEver
 * 2018/7/16 9:49
 */
public class ResponseUtil {
    public static Response createSuccessResult(){
        return new Response();
    }

    public static Response createFailResult(String status, String message) {
        Response response = new Response();
        response.setStatus(status);
        response.setMessage(message);
        return response;
    }

    public static Response createSuccessResult(Object content) {
        Response response = new Response();
        response.setResult(content);
        return response;
    }
}
