package com.example.yunchat.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yunchat.R;
import com.example.yunchat.models.AddFriendMessage;
import com.example.yunchat.models.SocketMessage;
import com.example.yunchat.models.User;
import com.example.yunchat.utils.JsonUtils;
import com.example.yunchat.utils.LoginUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.yunchat.configs.StringConfig.SEND_SUCCESS;
import static com.example.yunchat.utils.BitmapUtils.initAvatar;

/**
 * 发送好友请求
 * @author 曾健育
 */
public class AddFriendMessageActivity extends AppCompatActivity {

    private static final String TAG = "AddFriendMessageActivit";
    @BindView(R.id.add_friend_username)
    TextView usernameText;
    @BindView(R.id.add_friend_avatar)
    ImageView imageView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.add_friend_message)
    EditText text;
    @BindView(R.id.add_friend_button2)
    Button button;
    private User friend;
    private User me;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend_message);
        ButterKnife.bind(this);
        //初始化
        init();

    }

    private void initButton() {
        button.setOnClickListener(v -> {
            String myId = me.getOpenId();
            String yourId = friend.getOpenId();
            String msg = text.getText().toString();
            AddFriendMessage message = new AddFriendMessage();
            message.setSendUser(myId);
            message.setReviewUser(yourId);
            message.setMessage(msg);
            SocketMessage socketMessage = new SocketMessage(1, message);
            String json = JsonUtils.beanToJson(socketMessage);
            HomeActivity.client.send(json);
            Toast.makeText(this, SEND_SUCCESS, Toast.LENGTH_SHORT).show();
            this.finish();
            overridePendingTransition(R.anim.close_enter_t, R.anim.close_exit_t);
        });
    }
    private void initToolBar() {
        setSupportActionBar(toolbar);
        //添加默认的返回图标
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //设置返回键可用
        getSupportActionBar().setHomeButtonEnabled(true);

        //Toolbar回退键事件
        toolbar.setNavigationOnClickListener(v -> {
            finish();
            overridePendingTransition(R.anim.close_enter_t, R.anim.close_exit_t);
        });
    }
    private void init() {
        //获取用户数据
        getUser();
        //初始化用户名
        initUsername();
        //初始化头像
        initAvatar(friend.getAvatar(), imageView);
        //初始化Toolbar
        initToolBar();
        //初始化信息
        initMessage();
        //初始化按钮
        initButton();

    }

    private void initMessage() {
        text.setText("你好，我是" + me.getUsername());
    }

    private void getUser() {
        Intent intent = getIntent();
        friend = (User) intent.getSerializableExtra("user");
        Log.d(TAG, "getUser: " +friend);
        me = LoginUtils.getLoginInfo(AddFriendMessageActivity.this);
    }
    private void initUsername() {
        usernameText.setText(friend.getUsername());
    }

}