package com.liu.oio;



import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class IOServerExecuter {

    public static void main(String[] args)throws Exception {

//        创建线程池
        ExecutorService newCache = Executors.newCachedThreadPool();

        ServerSocket serverSocket = new ServerSocket(10010);
        System.out.println("服务启动");

        while(true){
//            accept 为阻塞点
            final Socket socket = serverSocket.accept();
            System.out.println("新客户端连接");
//            业务处理
//            handler(socket);

            newCache.execute(new Runnable() {
                @Override
                public void run() {
                    handler(socket);
                }
            });


        }

    }

    public static void handler(Socket socket) {


        try {
            byte[] bytes = new byte[1024];
            InputStream   inputStream= socket.getInputStream();
            while (true){
//                read 为阻塞点
                int read = inputStream.read(bytes);
                if(read!=-1){
                    String  ss = new String(bytes,0,read);
                    System.out.println(ss);
                }else {
                    break;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            System.out.println("socket关闭");
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }






    }
}
