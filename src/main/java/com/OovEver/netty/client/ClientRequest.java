package com.OovEver.netty.client;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 客户端请求
 * @author OovEver
 * 2018/7/9 22:29
 */
public class ClientRequest {
//    请求ID
    private final long       id;
//    请求内容
    private       Object     content;
//    自增ID
    private final AtomicLong aid = new AtomicLong(1);
    private String command;
    public ClientRequest(){
        id = aid.incrementAndGet();

    }
    public long getId() {
        return id;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public AtomicLong getAid() {
        return aid;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }
}
