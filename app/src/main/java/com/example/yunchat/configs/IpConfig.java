package com.example.yunchat.configs;

/**
 * IP地址配置
 * @author 曾健育
 */
public class IpConfig {

    public static final String IP = "192.168.1.103";
    public static final String PORT = "8080";

    public static String getAddress() {
        return IP + ":" + PORT;
    }
}
