package com.example.yunchat.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 登录信息
 * @author 曾健育
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginInfo {
    /**用户名*/
    private String username;
    /**密码*/
    private String password;
}
