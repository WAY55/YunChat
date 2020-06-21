package com.example.yunchat.dialogs;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 对话框聊天室
 * @author 陈树旭
 */
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

    public DialogMessage(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Handler handler = new MyHandler();
        //引入布局文件
        setContentView(R.layout.dialog_message);
        //初始化组件
        initView();
        //设置按钮的点击事件
        initListener();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    socket = new Socket("192.168.0.113", 11111);
                    InputStream is = socket.getInputStream();
                    byte[] b = new byte[1024];
                    int length;
                    while ((length = is.read(b)) != -1){
                        String input = new String(b, 0, length);
                        //将接收的数据发送至主线程
                        Message message = Message.obtain();
                        /*TYPE_RIGHT*/
                        message.what = 2;
                        message.obj = input;
                        handler.sendMessage(message);
                    }
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        });
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
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            OutputStream os = socket.getOutputStream();
                            /*@SuppressLint忽略带注释元素的指定警告。*/
                            @SuppressLint("SimpleDateFormat")
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
                            os.write((socket.getLocalPort()+"//"+ data + "//"
                                    + simpleDateFormat.format(new Date())).getBytes(StandardCharsets.UTF_8));
                            os.flush();
                        }catch (IOException e){
                            e.printStackTrace();
                        }
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

    @SuppressLint("HandlerLeak")
    private class MyHandler extends Handler{
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 2){ /*TYPE_Right*/
                /*获取端口号*/
                int localPort = socket.getLocalPort();
                String[] data = ((String) msg.obj).split("//");
                //临时存储用户姓名
                User user = new User();
                if (data[0].equals(localPort + "")){
                    user.setUsername("我：");
                    UserMessage message = new UserMessage(user,
                            data[2], data[1], 1);
                    list.add(message);
                }else {/*TYPE_LEFT*/
                    user.setUsername("端口:" + data[0]);
                    UserMessage message = new UserMessage(user,
                            data[2], data[1], 2);
                    list.add(message);
                }
                //UI交互
                adapter.setMessages(list);
                recyclerView.setAdapter(adapter);
                LinearLayoutManager manager = new LinearLayoutManager(
                        context, LinearLayoutManager.VERTICAL, false);
                recyclerView.setLayoutManager(manager);
            }
        }
    }
}