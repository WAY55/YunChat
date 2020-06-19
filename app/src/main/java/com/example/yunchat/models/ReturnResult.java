package com.example.yunchat.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 数据返回类
 * @author 曾健育
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReturnResult {
    private byte code;
    private Object info;


}
