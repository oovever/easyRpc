package com.OovEver.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 服务端处理器
 * @author OovEver
 * 2018/7/9 0:24
 */
public class SimpleServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        服务器写回到client内容
        ctx.channel().writeAndFlush("is ok\r\n");
//        ctx.channel().close();
        super.channelRead(ctx, msg);
    }
}
