package com.example.yunchat.inerface;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * 注解获取EditText 的String值
 * @author 曾健育
 */
@Target(ElementType.FIELD)
public @interface EditTextValue {
    String value();
}
