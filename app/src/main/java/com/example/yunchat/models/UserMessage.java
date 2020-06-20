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
    /**信息发送日期*/
    private String data;
    /**信息发送类型*/
    private int messageType;

    public UserMessage() {
    }

    public UserMessage(User user, String time, String data, int messageType) {
        this.user = user;
        this.time = time;
        this.data = data;
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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }
}