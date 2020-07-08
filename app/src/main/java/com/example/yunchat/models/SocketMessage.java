package com.example.yunchat.models;

import java.io.Serializable;

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
public class SocketMessage implements Serializable {
    private Integer type;
    private Object content;
}
