package com.example.yunchat.handlers;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.yunchat.models.ReturnResult;

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
        switch (msg.what) {
            case 1:
                ReturnResult result = (ReturnResult) msg.obj;
                if (result.getCode() == 1) {

                } else {
                    Toast.makeText(activity, (String)result.getInfo(), Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }
}
