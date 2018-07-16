package com.OovEver.netty.util;

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
    private String status="00000";//返回状态 00000表示成功 其他表示失败
    private String message;//失败信息
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
