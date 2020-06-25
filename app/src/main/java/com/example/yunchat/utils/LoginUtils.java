package com.example.yunchat.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.yunchat.configs.IpConfig;
import com.example.yunchat.models.LoginInfo;
import com.example.yunchat.models.User;
import com.google.gson.JsonObject;

/**
 * 登录工具
 *
 * @author 曾健育
 */
public class LoginUtils {
    /**
     * 登录验证的URL地址
     */
    public final static String LOGIN_URL = "http://"+ IpConfig.getAddress() +"/user/login.do";
    /**
     * 存储的key
     */
    private final static String SP_INFO = "Login";

    /**
     * 获取已经存储的登录信息
     * @param activity 当前活动
     * @return 用户信息
     */
    public static User getLoginInfo(Activity activity) {
        //SharedPreferences类获取存储的用户名和密码
        SharedPreferences sharedPreferences = activity.getSharedPreferences(SP_INFO, Context.MODE_PRIVATE);
        //获取用户
        String userJson = sharedPreferences.getString("user", null);
        if ("null".equals(userJson)) {
            return null;
        }

        User user = (User) JsonUtils.jsonToBean(userJson, User.class);
        if (user.getOpenId() == null || user.getOpenId().isEmpty()) {
            return null;
        } else {
            return user;
        }
    }


    /**
     * 将登录信息进行永久性存储
     * @param user 登录信息
     * @param activity 当前活动
     */
    public static void saveLoginInfo(User user, Activity activity) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(SP_INFO, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user", JsonUtils.beanToJson(user));
        System.out.println("write: " + user);
        editor.apply();

    }
}
