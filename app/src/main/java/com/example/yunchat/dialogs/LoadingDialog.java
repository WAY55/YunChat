package com.example.yunchat.dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import com.example.yunchat.R;
import com.wang.avi.AVLoadingIndicatorView;

/**
 * 加载动画页
 * @author 曾健育
 */
public class LoadingDialog extends AlertDialog {
    private static LoadingDialog loadingDialog;
    private AVLoadingIndicatorView avi;

    public static LoadingDialog getInstance(Context context) {
        //设置AlertDialog背景透明
        loadingDialog = new LoadingDialog(context, R.style.TransparentDialog);
        loadingDialog.setCancelable(false);
        loadingDialog.setCanceledOnTouchOutside(false);
        return loadingDialog;
    }

    public LoadingDialog(Context context, int themeResId) {
        super(context,themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog_loading);
        avi = (AVLoadingIndicatorView)this.findViewById(R.id.avi);
    }

    @Override
    public void show() {
        super.show();
        avi.show();
    }

    @Override
    public void dismiss() {
        super.dismiss();
        avi.hide();
    }


}
