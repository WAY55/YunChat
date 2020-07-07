package com.example.yunchat.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import com.example.yunchat.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author 曾健育
 */
public class NewFriendActivity extends AppCompatActivity {
    @BindView(R.id.new_friends_toolbar)
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_friend);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        initToolBar();
    }

    private void initToolBar() {

        setSupportActionBar(toolbar);
        //添加默认的返回图标
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.new_friends);
        //设置返回键可用
        getSupportActionBar().setHomeButtonEnabled(true);
        //Toolbar回退键事件
        toolbar.setNavigationOnClickListener(v -> {
            finish();
            overridePendingTransition(R.anim.close_enter_t, R.anim.close_exit_t);
        });
    }
}