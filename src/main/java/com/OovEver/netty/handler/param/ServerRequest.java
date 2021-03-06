package com.OovEver.netty.handler.param;

/**
 * 接受客户端请求
 * @author OovEver
 * 2018/7/9 22:53
 */
public class ServerRequest {
//    请求ID
    private long   id;
//    请求内容
    private Object content;
//    某个类的方法
    private String command;
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }
}
