package com.example.yunchat.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * WebSocket消息
 * @author 曾健育
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SocketMessage {
    private Integer type;
    private Object content;
}
