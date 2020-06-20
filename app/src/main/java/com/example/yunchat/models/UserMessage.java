package com.example.yunchat.models;

/**
 * 对话框信息类
 * @author 陈树旭
 */
public class UserMessage {
    /**用户信息*/
    private User user;
    /**信息发送时间*/
    private String time;
    /**信息发送内容*/
    private String message;
    /**信息发送类型*/
    private int messageType;

    public UserMessage() {
    }

    public UserMessage(User user, String time, String message, int messageType) {
        this.user = user;
        this.time = time;
        this.message = message;
        this.messageType = messageType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setData(String message) {
        this.message = message;
    }

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }
}