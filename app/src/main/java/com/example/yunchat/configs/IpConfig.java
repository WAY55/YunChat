package com.example.yunchat.configs;

/**
 * IP地址配置
 * @author 曾健育
 */
public class IpConfig {

    private static final String IP = "192.168.1.101";
    private static final String PORT = "8080";

    public static String getAddress() {
        return IP + ":" + PORT;
    }
}
