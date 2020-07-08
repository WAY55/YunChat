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
        Log.d("abc", "check: " +type);
        switch (type) {
            case 1:
                Message msg = new Message();
                msg.what = 1;
                Log.d("abc", String.valueOf(((App) ((HomeActivity) getContext()).getApplication()).getHandler() != null));
                ((App) ((HomeActivity) getContext()).getApplication()).getHandler().sendMessage(msg);
                break;
            default:
        }
    }
}
