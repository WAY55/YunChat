package com.example.yunchat.models;

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
public class AddFriendMessage {
    private String sendUser;
    private String reviewUser;
    private String message;
}
