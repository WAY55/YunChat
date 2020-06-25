package com.example.yunchat.utils;

import com.example.yunchat.configs.IpConfig;
import com.example.yunchat.models.ReturnResult;
import com.example.yunchat.models.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 注册工具类
 *
 * @author 曾健育
 */
public class RegisterUtils {
    public static final String REGISTER_URL = "http://" + IpConfig.getAddress() + "/user/register.do";


}
