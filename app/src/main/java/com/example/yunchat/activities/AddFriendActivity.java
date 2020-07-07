package com.example.yunchat.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.yunchat.R;
import com.example.yunchat.models.User;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.yunchat.utils.BitmapUtils.initAvatar;

/**
 * 添加好友界面
 *
 * @author 曾健育
 */
public class AddFriendActivity extends AppCompatActivity {

    @BindView(R.id.friend_toolbar)
    Toolbar toolbar;
    /**
     * 头像
     */
    @BindView(R.id.friend_avatar)
    ImageView imageView;
    /**
     * 用户名
     */
    @BindView(R.id.friend_username)
    TextView friendUsername;
    /**
     * 性别
     */
    @BindView(R.id.friend_sex)
    TextView friendSex;
    /**
     * 邮箱
     */
    @BindView(R.id.friend_email)
    TextView friendEmail;
    /**
     * 生日
     */
    @BindView(R.id.friend_birthday)
    TextView friendBirthday;
    /**
     * 地址
     */
    @BindView(R.id.friend_address)
    TextView friendAddress;
    @BindView(R.id.add_friend_button1)
    Button button;
    private User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        ButterKnife.bind(this);
        //获取用户信息
        user = getUser();
        //初始化界面
        init();

    }

    private void initButton() {
        button.setOnClickListener(v -> {
            Intent intent = new Intent(AddFriendActivity.this, AddFriendMessageActivity.class);
            intent.putExtra("user", user);
            startActivityForResult(intent, 1);
            overridePendingTransition(R.anim.open_enter_t, R.anim.open_exit_t);
        });
    }

    private User getUser() {
        Intent intent = getIntent();
        return (User) intent.getSerializableExtra("user");
    }

    private void init() {
        initAvatar(user.getAvatar(), imageView);
        initToolBar();
        initAddress();
        initBirthday();
        initUserName();
        initEmail();
        initSex();
        initButton();
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

    private void initAddress() {
        friendAddress.setText(user.getAddress());
    }

    private void initBirthday() {
        friendBirthday.setText(user.getBirthday());
    }

    private void initUserName() {
        friendUsername.setText(user.getUsername());
    }

    private void initEmail() {
        friendEmail.setText(user.getEmail());
    }

    private void initSex() {
        switch (user.getSex()) {
            case 0:
                friendSex.setText(R.string.sex_male);
                break;
            case 1:
                friendSex.setText(R.string.sex_female);
                break;
            default:
        }
    }
}
