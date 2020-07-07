package com.example.yunchat.utils;

import android.content.Context;
import android.widget.Toast;

import com.example.yunchat.activities.HomeActivity;

public class ToastUtils {
    private static Context context;
    private static Context getContext() {
        if(context == null) {
            context = HomeActivity.context;
        }
        return context;
    }
    public static void showShortToast(String msg) {

        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public static void showLongToast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
    }

    public static void showToast(String msg) {
        showToast(msg);
        //默认showShortToast
    }


}
