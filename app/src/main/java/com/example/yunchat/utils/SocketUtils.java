package com.example.yunchat.utils;

import com.example.yunchat.models.AddFriendMessage;
import com.example.yunchat.models.SocketMessage;

public class SocketUtils {
    private SocketMessage message;

    public SocketUtils(String message) {
        this.message = (SocketMessage) JsonUtils.jsonToBean(message, SocketMessage.class);
    }

    public void check() {
        int type = message.getType();
        switch (type) {
            case 1:
                AddFriendMessage friendMessage = (AddFriendMessage) JsonUtils.jsonToBean(JsonUtils.beanToJson(message.getContent()), AddFriendMessage.class);

                break;
            default:
        }
    }
}
