package com.liu.netty5;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
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
//            netty3、x
////  设置参数
//            bootstrap.setOption("backlog",1024);
//            bootstrap.setOption("tcpNoDelay",true);
//            bootstrap.setOption("keepAlive",true);
//            设置参数
            bootstrap.option(ChannelOption.SO_BACKLOG,2048);//serverSocketchannel设置 链接缓冲池大小
            bootstrap.option(ChannelOption.SO_KEEPALIVE,true);// socketchannel维持链接活跃，清除死链接
            bootstrap.option(ChannelOption.TCP_NODELAY,true);//  socketchannel 关闭延迟发送的功能



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
