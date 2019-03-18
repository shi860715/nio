package com.liu.netty5;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ClinetHandler extends SimpleChannelInboundHandler<String> {


    protected void messageReceived(ChannelHandlerContext channelHandlerContext, String s) throws Exception {

        System.out.println("客户端收到的消息为："+s);
//        channelHandlerContext.channel().writeAndFlush("nihao");
    }


    /**
     *  异常处理
     * @param ctx
     * @param cause
     * @throws Exception
     */
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }

    /**
     *  新的客户端
     * @param ctx
     * @throws Exception
     */
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    /**
     * 客户端断开
     * 客户端断开
     * @param ctx
     * @throws Exception
     */
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
    }
}
