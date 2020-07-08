package com.example.yunchat.configs;

/**
 * IP地址配置
 * @author 曾健育
 */
public class IpConfig {

    private static final String IP = "119.134.182.197";
    private static final String PORT = "8080";

    public static String getAddress() {
        return IP + ":" + PORT + "/yunchat";
    }

    public static final String SEARCH_FRIEND = "http://"  + getAddress() + "/friend/search";
    public static final String FRIENDS_NEWS = "http://"  + getAddress() + "/friend/news";
    public static final String FRIENDS_LOAD_ALL = "http://"  + getAddress() + "/friend/load";
    public static final String FRIENDS_GET_NEWS = "http://"  + getAddress() + "/friend/getNews";
}
