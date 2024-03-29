package com.liu.netty5;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Client {


    public static void main(String[] args) {
     //        服务类
        Bootstrap bootstrap = new Bootstrap();
//        worker
        EventLoopGroup worker = new NioEventLoopGroup();
//        s设置线程池
        try {

//
            bootstrap.group(worker);
//  设置socket 工厂

            bootstrap.channel(NioSocketChannel.class);

//        设置管道

            bootstrap.handler(new ChannelInitializer<Channel>() {
                protected void initChannel(Channel channel) throws Exception {
                    channel.pipeline().addLast("decoder", new StringDecoder());
                    channel.pipeline().addLast("encoder", new StringEncoder());
                    channel.pipeline().addLast("serverHandler", new ClinetHandler());
                }
            });

            ChannelFuture connect = bootstrap.connect("127.0.0.1", 10100);


            BufferedReader  bufferedReader  = new BufferedReader(new InputStreamReader( System.in));
            while (true){
                System.out.println("请输入发送的消息：");
                String msg = bufferedReader.readLine();
                connect.channel().writeAndFlush(msg);
            }




        }catch (Exception e){

        }finally {
            worker.shutdownGracefully();
        }



    }
}
