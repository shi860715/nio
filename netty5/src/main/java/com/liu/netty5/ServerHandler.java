package com.liu.netty5;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ServerHandler extends SimpleChannelInboundHandler<String> {


    protected void messageReceived(ChannelHandlerContext channelHandlerContext, String s) throws Exception {

        System.out.println("服务端接收的消息为"+s);
        channelHandlerContext.channel().writeAndFlush("nihao");
    }
}
