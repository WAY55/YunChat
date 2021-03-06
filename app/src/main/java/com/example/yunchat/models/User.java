package com.example.yunchat.models;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author 曾健育
 * 用户信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    /**用户唯一标识*/
    private String openId;
    /**用户昵称*/
    private String username;
    /**用户密码*/
    private String password;

    /**用户邮箱*/
    private String email;
    /**头像地址*/
    private String avatar;
    /**用户性别*/
    private byte sex;
    /**生日*/
    private String birthday;
    /**地址*/
    private String address;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
}
