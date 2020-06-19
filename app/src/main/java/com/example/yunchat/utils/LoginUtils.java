package com.example.yunchat.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.yunchat.models.LoginInfo;
import com.example.yunchat.models.User;

/**
 * 登录工具
 *
 * @author 曾健育
 */
public class LoginUtils {
    /**
     * 登录验证的URL地址
     */
    private final static String LOGIN_URL = "http://localhost:8080/user/getlogin.do";
    /**
     * 存储的key
     */
    private final static String SP_INFO = "Login";

    /**
     * 获取已经存储的登录信息
     * @param activity 当前活动
     * @return 登录信息
     */
    public static LoginInfo getLoginInfo(Activity activity) {
        //SharedPreferences类获取存储的用户名和密码
        SharedPreferences sharedPreferences = activity.getSharedPreferences(SP_INFO, Context.MODE_PRIVATE);
        //获取用户名
        String username = sharedPreferences.getString("username", null);
        //获取密码
        String password = sharedPreferences.getString("password", null);
        if (username == null || password == null) {
            return null;
        } else {
            return new LoginInfo(username, password);
        }
    }


    /**
     * 将登录信息进行永久性存储
     * @param loginInfo 登录信息
     * @param activity 当前活动
     */
    private static void saveLoginInfo(LoginInfo loginInfo, Activity activity) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(SP_INFO, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", loginInfo.getUsername());
        editor.putString("password", loginInfo.getPassword());
        editor.apply();

    }
}
