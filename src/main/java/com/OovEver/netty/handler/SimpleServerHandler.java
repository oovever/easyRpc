package com.OovEver.netty.handler;

import com.OovEver.netty.handler.param.ServerRequest;
import com.OovEver.netty.util.Response;
import com.alibaba.fastjson.JSONObject;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * 服务端处理器
 * @author OovEver
 * 2018/7/9 0:24
 */
public class SimpleServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        服务器写回到client内容
//        ctx.channel().writeAndFlush("is ok\r\n");
//        ctx.channel().close();
       ServerRequest request=JSONObject.parseObject(msg.toString(), ServerRequest.class);
        Response response = new Response();
        response.setId(request.getId());
        response.setResult("is ok");
        ctx.channel().writeAndFlush(JSONObject.toJSONString(response));
        ctx.channel().writeAndFlush("\r\n");
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
//        如果是IdleStateEvent事件
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event=(IdleStateEvent) evt;
//            读空闲 60s无事件读
            if (event.state().equals(IdleState.READER_IDLE)) {
                System.out.println("读空闲=====");
                ctx.channel().close();
            }
//            写空闲 45秒无写事件发生
           else if (event.state().equals(IdleState.WRITER_IDLE)) {
                System.out.println("写空闲=====");
            }
//            20s无读写 读写空闲
            else if (event.state().equals(IdleState.ALL_IDLE)) {
                System.out.println("读写空闲");
                ctx.channel().writeAndFlush("ping\r\n");
            }

        }
        super.userEventTriggered(ctx, evt);
    }
}
