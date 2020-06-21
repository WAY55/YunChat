package com.example.yunchat.configs;

/**
 * IP地址配置
 * @author 曾健育
 */
public class IpConfig {

    private static final String IP = "119.134.182.112";
    private static final String PORT = "8080";

    public static String getAddress() {
        return IP + ":" + PORT + "/yunchat";
    }
}
