package com.OovEver.netty.client;

import com.OovEver.netty.handler.SimpleClientHandler;
import com.OovEver.netty.util.Response;
import com.alibaba.fastjson.JSONObject;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * TCP客户端
 * @author OovEver
 * 2018/7/9 10:27
 */
public class TcpClient {
    static final Bootstrap b = new Bootstrap();
    static ChannelFuture f = null;
    static {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        b.group(workerGroup); // (2)
        b.channel(NioSocketChannel.class); // (3)
        b.option(ChannelOption.SO_KEEPALIVE, true); // (4)
        b.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            public void initChannel(SocketChannel ch) throws Exception {
//                    解码服务端传过来的数据 二进制
                ch.pipeline().addLast(new DelimiterBasedFrameDecoder(Integer.MAX_VALUE, Delimiters.lineDelimiter()[0]));
//                    字符串解码器 字符串
                ch.pipeline().addLast(new StringDecoder());
                ch.pipeline().addLast(new SimpleClientHandler());
//                    字符串编码器
                ch.pipeline().addLast(new StringEncoder());
            }
        });
        String         host        = "localhost";
        int            port        = 8080;
        // Start the client.
        try {
            f = b.connect(host, port).sync(); // (5)
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 以长连接形式发送数据
     * 1. 每一个请求都是同一个连接，并发问题 唯一的id进行识别
     * Request
     *  1.唯一请求ID
     *  2.请求内容
     *Response
     *  1.唯一相应ID
     *  2.响应内容
     * @param request 要发送的数据
     * @return 响应结果
     */
    public static Response send(ClientRequest request) {
        f.channel().writeAndFlush(JSONObject.toJSONString(request));
        f.channel().writeAndFlush("\r\n");
        DefaultFuture df = new DefaultFuture(request);
        return df.get();
    }

}
