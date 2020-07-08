package com.example.lib;

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
                System.out.println("Server started successfully:" + "port" + port);
            }catch (IOException e){
                System.out.println("Server failed to start, reason for failure：" + e.getMessage());
            }
        }

        @Override
        public void run() {
            try {
                while (!exit){
                    //等待获取手机连接
                    System.out.println("Waiting for phone to connect......");
                    final Socket socket = serverSocket.accept();
                    //获取连接IP以及端口号
                    final String IP = socket.getRemoteSocketAddress().toString();
                    System.out.println("connection succeeded,IP:" + IP);
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
                                    System.out.println("Received data:" + text);
                                }
                            }catch (IOException e){
                                e.printStackTrace();
                            }finally {
                                synchronized (this){
                                    System.out.println("Close link：" + IP);
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
                    System.out.println("Service closed successfully");
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public static ServerThread startServer(){
        System.out.println("Service start");
        if (serverThread != null){
            showDown();
        }
        serverThread = new ServerThread();
        new Thread(serverThread).start();
        System.out.println("Service started successfully");
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
