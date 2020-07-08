package com.example.yunchat.server;

import android.app.Activity;
import android.content.Context;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.example.yunchat.App;
import com.example.yunchat.activities.HomeActivity;
import com.example.yunchat.utils.SocketUtils;
import com.example.yunchat.utils.ToastUtils;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

/**
 * WebSocket连接
 * @author 曾健育
 */
public class JWebSocketClient extends WebSocketClient {
    SocketUtils utils = null;

    public JWebSocketClient(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        Log.d("JWebSocketClient", "onOpen():" + handshakedata);
    }

    @Override
    public void onMessage(String message) {
        Log.d("JWebSocketClient", "onMessage():" + message);
        if (utils == null) {
            utils = new SocketUtils(message);
        }
        utils.check();
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        Log.d("JWebSocketClient", "onClose():" + reason);
    }

    @Override
    public void onError(Exception ex) {

    }
}
