package com.OovEver.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.util.AttributeKey;

import javax.naming.Name;

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
        ctx.channel().attr(AttributeKey.valueOf("clientMsg")).set(msg);
//        关闭通道
        ctx.channel().close();
        super.channelRead(ctx, msg);
    }
}
