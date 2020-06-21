package com.example.yunchat.dialogs;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.yunchat.R;
import com.example.yunchat.activities.HomeActivity;
import com.example.yunchat.activities.MessageActivity;
import com.example.yunchat.models.UserMessage;
import com.example.yunchat.recyclerview.DialogAdapter;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class DialogMessage extends Dialog {

    private ImageButton backBtn;
    private ImageButton messageBtn;
    private RecyclerView recyclerView;
    private EditText editInput;
    private ImageButton enterBtn;
    private Socket socket;
    private List<UserMessage> list;
    private DialogAdapter adapter;

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
        recyclerView = (RecyclerView) findViewById(R.id.dialog_rv);
        editInput = (EditText) findViewById(R.id.dialog_input);
        enterBtn = (ImageButton) findViewById(R.id.dialog_enter);
        list = new ArrayList<>();
//        adapter = new DialogAdapter(this);
    }

}