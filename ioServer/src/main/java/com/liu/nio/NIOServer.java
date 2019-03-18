package com.liu.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.*;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.Executors;

/**
 * NIO 服务端
 */
public class NIOServer {

    //通道管理器
    private Selector selector;


    /**
     * 获取一个serversocket通道，并对该通管做一些初始化工作
     *
     * @param port
     * @throws IOException
     */
    public void initServer(int port) throws IOException{
//      获取一个serversocket 通管道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
//        设置通管道为非阻塞
        serverSocketChannel.configureBlocking(false);
//        将通道对应的serversocket 绑定到port 端口
         serverSocketChannel.socket().bind(new InetSocketAddress(port));
//         获得一个通管道管理器
        this.selector=Selector.open();
//        将通管道管理器和该通道绑定，并为该通道注册SelectionKey.op_accept事件，注册事件后，
//        当该事件被触发后，Selector.select() 会返回，如果该事件没到达，selector.select()会一直被阻塞
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);


    }

    /**
     * 采用轮询的方式监听selector上是否有需要处理的事件，如果有则处理
     * @throws IOException
     */
    public void listen() throws IOException{

        System.out.println("服务已经启动成功！！");
        while (true){
//            当注册事件到达时，方法返回。否则，该方法会一直阻塞
            selector.select();

//            获取seletor中选中的项的迭代器，选中的项为注册的事件
            Iterator<?> iterator = this.selector.selectedKeys().iterator();
            while (iterator.hasNext()){
                SelectionKey key = (SelectionKey) iterator.next();
                iterator.remove();
                handler(key);
            }

        }
    }

    /**
     * 处理请求
     * @param key
     * @throws IOException
     */
    public  void handler(SelectionKey key) throws IOException{

//        客户端请求链接事件
        if(key.isAcceptable()){
            handlerAccept(key);
        }else if(key.isReadable()){
            handlerRead(key);
        }

    }


    /**
     * 处理链接请求
     * @param key
     * @throws IOException
     */
     public void handlerAccept(SelectionKey key) throws IOException{

        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
//        获取和客户端的链接通道
         SocketChannel channel = serverSocketChannel.accept();
//        设置成非阻塞
         channel.configureBlocking(false);
//         在这里可以给客户端发送消息

         System.out.println("新的客户端链接");
         ByteBuffer outBuffer = ByteBuffer.wrap("你好 welcome jion us".getBytes());
         channel.write(outBuffer);
//        在和客户端链接成功后，为了可以接受到客户端的信息，需要给通道设置读的权限。

         channel.register(this.selector,SelectionKey.OP_READ);

     }

     public void  handlerRead(SelectionKey key) throws IOException{


//         服务器可读取消息，得到发生的socket通道
         SocketChannel channel = (SocketChannel) key.channel();
         ByteBuffer buffer = ByteBuffer.allocate(1024);
         int read = channel.read(buffer);
         if(read>0){
             byte[] data = buffer.array();
             String msg = new String(data).trim();
             System.out.println("服务端收到的消息："+ msg);

//             回写数据
             ByteBuffer outBuffer = ByteBuffer.wrap("你好ok i am get it".getBytes());
             channel.write(outBuffer);

         }else{
             System.out.println("客户端关闭");
//            客户端关闭后，记得释放
             key.cancel();
         }

     }


    public static void main(String[] args) throws IOException{
        NIOServer server = new NIOServer();
        server.initServer(10020);
        server.listen();
    }











}
