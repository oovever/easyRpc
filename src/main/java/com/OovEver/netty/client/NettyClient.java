package com.OovEver.netty.client;

import com.OovEver.netty.handler.SimpleClientHandler;
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
import io.netty.util.AttributeKey;

/**
 * Netty客户端
 * @author OovEver
 * 2018/7/9 1:28
 */
public class NettyClient {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 8080;
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
//            客户端启动
            Bootstrap b = new Bootstrap(); // (1)
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

            // Start the client.
            ChannelFuture f = b.connect(host, port).sync(); // (5)
            f.channel().writeAndFlush("hello server");
            f.channel().writeAndFlush("\r\n");
            // Wait until the connection is closed.等待通道关闭
            f.channel().closeFuture().sync();
//            设置属性
            Object result = f.channel().attr(AttributeKey.valueOf("clientMsg")).get();
            System.out.println("获取到服务器返回的数据" + result.toString());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }

}
