package com.example.yunchat.dialogs;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.yunchat.R;
import com.example.yunchat.models.User;
import com.example.yunchat.models.UserMessage;
import com.example.yunchat.recyclerview.DialogAdapter;
import com.example.yunchat.utils.LoginUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DialogMessage extends Dialog {

    private Context context;
    private ImageButton backBtn;
    private ImageButton messageBtn;
    private RecyclerView recyclerView;
    private EditText editInput;
    private ImageButton enterBtn;
    private Socket socket;
    private ArrayList<UserMessage> list;
    private DialogAdapter adapter;
    private Activity activity;

    public DialogMessage(@NonNull Context context, Activity activity) {
        super(context);
        this.context = context;
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置点击屏幕对话框不消失
        this.setCanceledOnTouchOutside(false);
        //引入布局文件
        setContentView(R.layout.dialog_message);
        //初始化组件
        initView();
        //设置按钮的点击事件
        initListener();
        final Handler handler = new MyHandler();
        new Thread(()->{
            try {
                socket = new Socket("192.168.186.101", 11111);
                InputStream is = socket.getInputStream();
                byte[] b = new byte[1024];
                int length;
                while ((length = is.read(b)) != -1){
                    String data = new String(b, 0, length);
                    //将接收的数据发送到主线程去
                    Message message = Message.obtain();
                    message.what = 1;
                    message.obj = data;
                    handler.sendMessage(message);
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }).start();
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
                final String data = editInput.getText().toString();
                new Thread(()->{
                    try {
                        OutputStream os = socket.getOutputStream();
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
                        os.write((socket.getLocalPort() + "//" + data + "//"
                                + simpleDateFormat.format(new Date())).getBytes(
                                        StandardCharsets.UTF_8));
                        //清空
                        os.flush();
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }).start();
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
        adapter = new DialogAdapter(context);
    }

    private class MyHandler extends Handler{
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1){/*发送的消息*/
                int localPort = socket.getLocalPort();
                User user = new User();
                String[] data = ((String) msg.obj).split("//");
                if (data[0].equals(localPort + "")){/*TYPE_RIGHT*/
                    user = LoginUtils.getLoginInfo(activity);
                    UserMessage message = new UserMessage(user, data[2], data[1], 2);
                    list.add(message);
                }else {
                    user.setUsername("IP:" + data[0]);
                    UserMessage message = new UserMessage(user, data[2], data[1], 1);
                    list.add(message);
                }
                //向适配器设置数据
                adapter.setMessages(list);
                recyclerView.setAdapter(adapter);
                LinearLayoutManager manager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
                recyclerView.setLayoutManager(manager);
            }
        }
    }
}