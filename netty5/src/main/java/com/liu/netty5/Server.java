package com.liu.netty5;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class Server {


    public static void main(String[] args) {
        //    服务类
        ServerBootstrap bootstrap = new ServerBootstrap();
//  事件循环组，理解成线程池
        EventLoopGroup boos = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();


        try {
//            设置线程池
            bootstrap.group(boos,worker);

// 设置socket工厂
            bootstrap.channel(NioServerSocketChannel.class);


//       设置管道工厂
            bootstrap.childHandler(new ChannelInitializer<Channel>() {


                protected void initChannel(Channel channel) throws Exception {
                    channel.pipeline().addLast("decoder", new StringDecoder());
                    channel.pipeline().addLast("encoder", new StringEncoder());
                    channel.pipeline().addLast("serverHandler", new ServerHandler());

                }
            });

//        绑定端口
            ChannelFuture future = bootstrap.bind(10100);
//    等待服务端关闭
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
             boos.shutdownGracefully();
             worker.shutdownGracefully();

        }
    }


}
