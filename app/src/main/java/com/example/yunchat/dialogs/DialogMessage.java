package com.example.yunchat.dialogs;

import androidx.annotation.NonNull;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.yunchat.R;

public class DialogMessage extends Dialog {

    private ImageButton backBtn;
    private ImageButton messageBtn;
    private ImageButton enterBtn;
    private EditText editInput;
    private TextView textView;

    public DialogMessage(@NonNull Context context) {
        super(context);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //引入布局文件
        setContentView(R.layout.dialog_message);
        //初始化组件
        initView();
        //设置按钮的点击事件
        initListener();
    }

    /**
     * 按钮监听事件
     */
    private void initListener() {
        //返回按钮
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        //个人信息按钮
        messageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        //发送按钮
        enterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String editText = editInput.getText().toString();
                if (editText.length() != 0){
                    String text = textView.getText().toString() + '\n' + editText;
                    textView.setText(text);
                    editInput.setText("");
                }
            }
        });
    }

    /**
     * 初始化组件
     */
    private void initView() {
        backBtn = (ImageButton) findViewById(R.id.dialog_back);
        messageBtn = (ImageButton) findViewById(R.id.dialog_message_button);
        enterBtn = (ImageButton) findViewById(R.id.dialog_enter);
        editInput = (EditText) findViewById(R.id.dialog_input);
        textView = (TextView) findViewById(R.id.dialog_view);
    }

}