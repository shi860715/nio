package com.liu.netty;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NetServer {


    public static void main(String[] args) {

//     服务类
    ServerBootstrap bootstrap = new ServerBootstrap();

//   两个线程池
        ExecutorService boss = Executors.newCachedThreadPool();
        ExecutorService worker = Executors.newCachedThreadPool();


//  设置参数
        bootstrap.setOption("backlog",1024);
        bootstrap.setOption("tcpNoDelay",true);
        bootstrap.setOption("keepAlive",true);
//        设置niosocket工厂
    bootstrap.setFactory(new NioServerSocketChannelFactory(boss,worker));

//    设置管道工厂

   bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
       public ChannelPipeline getPipeline() throws Exception {
           ChannelPipeline channelPipeline = Channels.pipeline();
//           用来处理消息类
           channelPipeline.addLast("decoder",new StringDecoder());
           channelPipeline.addLast("encoder",new StringEncoder());

           channelPipeline.addLast("helloHandler",new HelloHandler());


           return channelPipeline;
       }
   });

        bootstrap.bind(new InetSocketAddress(10030));


        System.out.println("start !!!");



    }



}
