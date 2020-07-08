package com.example.yunchat.models;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 添加好友信息
 * @author 曾健育
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddFriendMessage implements Serializable {
    private int id;
    private String sendUser;
    private String reviewUser;
    private String message;
    private int accept;
}
