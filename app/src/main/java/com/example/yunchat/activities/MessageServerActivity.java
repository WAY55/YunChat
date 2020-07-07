package com.example.yunchat.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.yunchat.R;
import com.example.yunchat.models.User;
import com.example.yunchat.models.UserMessage;
import com.example.yunchat.adapters.MessageAdapter;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MessageServerActivity extends AppCompatActivity {

    private RecyclerView rv;
    private EditText et;
    private Button btn;
    private Socket socket;
    private ArrayList<UserMessage> list;
    private MessageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_server);

        rv = (RecyclerView) findViewById(R.id.message_rv);
        et = (EditText) findViewById(R.id.message_et);
        btn = (Button) findViewById(R.id.message_btn);
        list = new ArrayList<>();
        adapter = new MessageAdapter(this);

        final Handler handler = new MyHandler();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    socket = new Socket("192.168.0.113", 10010);
                    InputStream inputStream = socket.getInputStream();
                    byte[] buffer = new byte[1024];
                    int len;
                    while ((len = inputStream.read(buffer)) != -1) {
                        String data = new String(buffer, 0, len);
                        // 发到主线程中 收到的数据
                        Message message = Message.obtain();
                        message.what = 1;
                        message.obj = data;
                        handler.sendMessage(message);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String data = et.getText().toString();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            OutputStream outputStream = socket.getOutputStream();
                            SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
                            outputStream.write((socket.getLocalPort() + "//" + data + "//" + df.format(new Date())).getBytes("utf-8"));
                            outputStream.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });

    }

    private class MyHandler extends Handler {
        @Override
        public void handleMessage(@NotNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                User user = new User();
                int localPort = socket.getLocalPort();
                String[] split = ((String) msg.obj).split("//");
                if (split[0].equals(localPort + "")) {
                    user.setUsername("我");
                    UserMessage bean = new UserMessage(user,split[2],split[1],1);
                    list.add(bean);
                } else {
                    user.setUsername("来自：" + split[0]);
                    UserMessage bean = new UserMessage(user,split[2],split[1],2);
                    list.add(bean);
                }

                // 向适配器set数据
                adapter.setMessages(list);
                rv.setAdapter(adapter);
                LinearLayoutManager manager = new LinearLayoutManager(MessageServerActivity.this, LinearLayoutManager.VERTICAL, false);
                rv.setLayoutManager(manager);
            }
        }
    }
}