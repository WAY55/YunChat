package com.example.yunchat.server;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * 信息管理类
 * @author 陈树旭
 */
public class MessageManager {
    private static Map<String, Socket> map = new HashMap<>();
    private static ServerThread serverThread = null;

    private static class ServerThread implements Runnable{
        private int port = 11111;
        private boolean exit = false;
        private ServerSocket serverSocket;

        public ServerThread() {
            try {
                serverSocket = new ServerSocket(port);
                System.out.println("服务器启动成功" + "port" + port);
            }catch (IOException e){
                System.out.println("服务器启动失败,失败原因：" + e.getMessage());
            }
        }

        @Override
        public void run() {
            try {
                while (!exit){
                    //等待获取手机连接
                    System.out.println("等待手机连接......");
                    final Socket socket = serverSocket.accept();
                    //获取连接IP以及端口号
                    final String IP = socket.getRemoteSocketAddress().toString();
                    System.out.println("连接成功，IP：" + IP);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                //单线程锁
                                synchronized (this){
                                    map.put(IP, socket);
                                }
                                InputStream is = socket.getInputStream();
                                byte[] b = new byte[1024];
                                int length;
                                while((length = is.read(b)) != -1){
                                    String text = new String(b, 0, length);
                                    System.out.println("接收的数据：" + text);
                                }
                            }catch (IOException e){
                                e.printStackTrace();
                            }finally {
                                synchronized (this){
                                    System.out.println("关闭链接：" + IP);
                                    map.remove(IP);
                                }
                            }
                        }
                    }).start();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        public void Stop(){
            exit = true;
            if (serverSocket != null){
                try {
                    serverSocket.close();
                    System.out.println("服务关闭成功");
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public static ServerThread startServer(){
        System.out.println("服务开启");
        if (serverThread != null){
            showDown();
        }
        serverThread = new ServerThread();
        new Thread(serverThread).start();
        System.out.println("服务开启成功");
        return serverThread;
    }

    /*清空Map并关闭所有Socket、Server*/
    public static void showDown(){
        for (Socket socket:map.values()){
            try {
                socket.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        serverThread.Stop();
        map.clear();
    }
}
