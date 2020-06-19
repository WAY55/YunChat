package com.example.yunchat.utils;

import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 信息工具封装类
 *
 * @author 曾健育
 */
public class InfoUtils {

    /**
     * 获取EditText的值
     *
     * @param text EditText组件
     * @return String类型字符串
     */
    public static String getEditTextValue(EditText text) {
        return text.getText().toString();
    }

    /**
     * 校验EMAIL格式，真为正确
     *
     * @param email 邮箱值
     * @return true 为格式正确 false 为格式错误
     */
    public static boolean emailFormat(String email) {
        boolean tag = true;
        if (!email.matches("[\\w\\.\\-]+@([\\w\\-]+\\.)+[\\w\\-]+")) {
            tag = false;
        }
        return tag;
    }

    /**
     * 进行md5加密
     * @param plainText 需要加密的字符串
     * @return 加密后的字符串
     */
    public static String md5(String plainText) {
        //定义一个字节数组
        byte[] secretBytes = null;
        try {
            // 生成一个MD5加密计算摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            //对字符串进行加密
            md.update(plainText.getBytes());
            //获得加密后的数据
            secretBytes = md.digest();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("没有md5这个算法！");
        }
        //将加密后的数据转换为16进制数字
        String md5code = new BigInteger(1, secretBytes).toString(16);
        // 如果生成数字未满32位，需要前面补0
        for (int i = 0; i < 32 - md5code.length(); i++) {
            md5code = "0" + md5code;
        }
        return md5code;
    }

}
