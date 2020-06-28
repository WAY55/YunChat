package com.example.yunchat.handlers;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

/**
 * 搜索好友Handler
 * @author 曾健育
 */
public class SearchFriendHandler extends Handler {

    private Activity activity;
    public SearchFriendHandler(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void handleMessage(@NonNull Message msg) {
        super.handleMessage(msg);
    }
}
