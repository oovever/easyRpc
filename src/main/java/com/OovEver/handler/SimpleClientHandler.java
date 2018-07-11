package com.OovEver.handler;

import com.OovEver.client.DefaultFuture;
import com.OovEver.client.Response;
import com.alibaba.fastjson.JSONObject;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 客户端处理器
 * @author OovEver
 * 2018/7/9 1:31
 */
public class SimpleClientHandler extends ChannelInboundHandlerAdapter {
    /**
     * 接受服务器传送过来的数据
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        System.out.println(msg.toString());
//        接受服务器读写空闲传来的数据
        if ("ping".equals(msg.toString())) {
            ctx.channel().writeAndFlush("ping\r\n");
            return;
        }
//        ctx.channel().attr(AttributeKey.valueOf("clientMsg")).set(msg);
        Response response = JSONObject.parseObject(msg.toString(), Response.class);
        DefaultFuture.recive(response);

//        关闭通道
        ctx.channel().close();
        super.channelRead(ctx, msg);
    }
}
