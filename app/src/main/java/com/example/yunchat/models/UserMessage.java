package com.example.yunchat.models;

/**
 * 对话框信息类
 * @author 陈树旭
 */
public class UserMessage {
    /**用户信息*/
    private String name;
    /**信息发送时间*/
    private String time;
    /**信息发送日期*/
    private String data;
    /**信息发送类型*/
    private int messageType;

    public UserMessage() {
    }

    public UserMessage(String name, String time, String data, int messageType) {
        this.name = name;
        this.time = time;
        this.data = data;
        this.messageType = messageType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
