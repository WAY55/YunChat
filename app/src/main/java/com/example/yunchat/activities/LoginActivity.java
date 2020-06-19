package com.example.yunchat.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yunchat.App;
import com.example.yunchat.R;
import com.example.yunchat.models.LoginInfo;
import com.example.yunchat.models.User;
import com.example.yunchat.utils.LoginUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 登录活动
 *
 * @author 曾健育
 */
public class LoginActivity extends AppCompatActivity{
    private App app;
    /**
     * 两次按下返回键的时间范围
     */
    private final int exitTime = 2000;
    /**
     * 两次返回键推出的判断时间
     */
    private long mExitTime;
    /**
     * 用户名输入框
     */
    @BindView(R.id.username_text)
    EditText usernameInput;
    /**
     * 密码输入框
     */
    @BindView(R.id.password_text)
    EditText passwordInput;
    /**
     * 登录按钮
     */
    @BindView(R.id.login_submit_button)
    Button submitButton;
    /**
     * 前往注册的文字按钮
     */
    @BindView(R.id.login_goto_register_click)
    TextView gotoRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        //获取Application
        app = (App) getApplication();

        //添加登录事件
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取输入值
                String usernameText = usernameInput.getText().toString();
                String passwordText = passwordInput.getText().toString();

                //检验空值
                if (usernameText == null || usernameText.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "用户名不可为空", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (passwordText == null || passwordText.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "密码不可为空", Toast.LENGTH_SHORT).show();
                    return;
                }

                //进行登录
                LoginInfo loginInfo = new LoginInfo(usernameText, passwordText);

                //前往主页面
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
        //跳转注册
        gotoRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivityForResult(intent, 1);
                overridePendingTransition(R.anim.open_enter_t,R.anim.open_exit_t);
            }
        });
    }

    /**
     * 重写Back键，使双击退出程序
     */
    @Override
    public void onBackPressed() {
        //mExitTime的初始值为0，currentTimeMillis()肯定大于2000（毫秒），所以第一次按返回键的时候一定会进入此判断
        if ((System.currentTimeMillis() - mExitTime) > exitTime) {
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();
        } else {
            super.onBackPressed();
        }

    }


}
