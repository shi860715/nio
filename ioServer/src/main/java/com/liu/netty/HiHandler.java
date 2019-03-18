package com.liu.netty;

import org.jboss.netty.channel.*;

public class HiHandler extends SimpleChannelHandler {


    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        System.out.println("messageReceived");
        System.out.println(e.getMessage());

//        ChannelBuffer channelBuffer = ChannelBuffers.copiedBuffer("hi".getBytes());
//        这里千万不能给服务器会写数据，要不然就是没完没了的循环输出了
//           ctx.getChannel().write("hi");

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
        super.exceptionCaught(ctx, e);
    }

    @Override
    public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
              System.out.println("channelConnected");
        super.channelConnected(ctx, e);
    }

    @Override
    public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
           System.out.println("channelDisconnected");
             
                                                  
        super.channelDisconnected(ctx, e);
    }

    @Override
    public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
           System.out.println("channelClosed");
        super.channelClosed(ctx, e);
    }
}
