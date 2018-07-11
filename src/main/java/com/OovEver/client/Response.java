package com.OovEver.client;

/**
 * 响应类
 * @author OovEver
 * 2018/7/9 22:38
 */
public class Response {
//    响应ID
    private Long   id;
//    响应结果
    private Object result;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }


}
