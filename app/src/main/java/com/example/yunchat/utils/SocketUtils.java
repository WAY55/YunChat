package com.example.yunchat.utils;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.yunchat.App;
import com.example.yunchat.R;
import com.example.yunchat.activities.HomeActivity;
import com.example.yunchat.fragments.FriendsFragment;
import com.example.yunchat.models.AddFriendMessage;
import com.example.yunchat.models.SocketMessage;

public class SocketUtils {
    private SocketMessage message;
    private static Context context;

    private static Context getContext() {
        if (context == null) {
            context = HomeActivity.context;
        }
        return context;
    }

    public SocketUtils(String message) {
        this.message = (SocketMessage) JsonUtils.jsonToBean(message, SocketMessage.class);
    }

    public void check() {
        int type = message.getType();
        Message msg = new Message();
        switch (type) {
            case 1:
                ((App) ((HomeActivity) getContext()).getApplication()).getHandler().sendEmptyMessage(1);
                break;
            case 2:
                ((App) ((HomeActivity) getContext()).getApplication()).getHandler().sendEmptyMessage(2);
                break;
            default:
        }
    }
}
