package com.OovEver.netty.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author OovEver
 * 2018/7/9 0:24
 */
public class SimpleServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ctx.channel().writeAndFlush("is ok/r/n");
        ctx.channel().close();
        super.channelRead(ctx, msg);
    }
}
