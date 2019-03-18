package com.liu.oio;



import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class IOServer {

    public static void main(String[] args)throws Exception {


        ServerSocket serverSocket = new ServerSocket(10010);
        System.out.println("服务启动");

        while(true){
//            accept 为阻塞点
            final Socket socket = serverSocket.accept();
            System.out.println("新客户端连接");
//            业务处理
            handler(socket);
        }

    }

    public static void handler(Socket socket) {
        InputStream inputStream =null;

        try {
            byte[] bytes = new byte[1024];
            inputStream= socket.getInputStream();
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
                inputStream.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }






    }
}
