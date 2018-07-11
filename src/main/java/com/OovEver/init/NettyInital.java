package com.OovEver.init;

import com.OovEver.handler.SimpleServerHandler;
import com.OovEver.constant.Constants;
import com.OovEver.factory.ZookeeperFactory;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

/**
 * Netty实现的服务器
 * @author OovEver
 * 2018/7/9 0:08
 */
@Component
public class NettyInital implements ApplicationListener<ContextRefreshedEvent> {
    public static void start() {
//        线程组的创建 监听端口accept事件 默认只有一个线程
        EventLoopGroup parentGroup = new NioEventLoopGroup();
//        监听read write 默认启动CPU线程2倍
        EventLoopGroup childGroup = new NioEventLoopGroup();
        try {
//            服务端做配置和启动的类
            ServerBootstrap bootstrap = new ServerBootstrap();

            bootstrap.group(parentGroup, childGroup);
//临时存放已完成三次握手的请求的队列的最大长度
            bootstrap.option(ChannelOption.SO_BACKLOG, 128)
//                设置心跳包
                    .childOption(ChannelOption.SO_KEEPALIVE, false)
//                监听通道
//                    创建NioServerSocketChannel的实例
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() { // (4)
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
//                        解码器
                           ch.pipeline().addLast(new DelimiterBasedFrameDecoder(65535,Delimiters.lineDelimiter()[0]));
//                        字符串解码器
                            ch.pipeline().addLast(new StringDecoder());
//                            心跳长连接设置 读空闲 写空闲 所有空闲 时间单位
                            ch.pipeline().addLast(new IdleStateHandler(60, 45, 20,TimeUnit.SECONDS));
                            ch.pipeline().addLast(new SimpleServerHandler());
//                        字符串编码器
                            ch.pipeline().addLast(new StringEncoder());
                        }
                    });
//            绑定关口
            ChannelFuture future = bootstrap.bind(8080).sync();
//            注册服务器到zookeeper中
            CuratorFramework client = ZookeeperFactory.create();
//            获取IP地址
            InetAddress inetAddress = InetAddress.getLocalHost();
//            在服务器创建临时节点
            client.create().withMode(CreateMode.EPHEMERAL).forPath(Constants.SERVER_PATH + inetAddress.getHostAddress());
//            等待，直到异步操作执行完毕，核心思想同await。我们得到Future实例后，可以使用sync()方法来阻塞当前线程，直到异步操作执行完毕。
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            parentGroup.shutdownGracefully();
            childGroup.shutdownGracefully();
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        start();

    }
}
