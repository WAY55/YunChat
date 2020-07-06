package com.example.yunchat.utils;

import com.example.yunchat.models.ReturnResult;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * json 操作
 *
 * @author 曾健育
 */
public class JsonUtils {
    /**
     * 将java对象转换成json字符串
     *
     * @param bean
     * @return
     */

    public static String beanToJson(Object bean) {
        Gson gson = new Gson();
        String jsonStr = gson.toJson(bean);
        System.out.println(jsonStr);
        return jsonStr;
    }

    public static ReturnResult getResult(String json) {
        Gson gson = new Gson();
        ReturnResult result = gson.fromJson(json, ReturnResult.class);
        return result;
    }

    public static Object jsonToBean(String json, Class<?> aClass) {
        Gson gson = new Gson();
        return gson.fromJson(json, aClass);
    }

    public static List<?> jsonToList(String json, Type type) {
        Gson gson = new Gson();
        return gson.fromJson(json, type);
    }
}
