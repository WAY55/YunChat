package com.example.yunchat.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yunchat.App;
import com.example.yunchat.R;
import com.example.yunchat.configs.StringConfig;
import com.example.yunchat.models.LoginInfo;
import com.example.yunchat.models.ReturnResult;
import com.example.yunchat.models.User;
import com.example.yunchat.utils.HttpUtils;
import com.example.yunchat.utils.InfoUtils;
import com.example.yunchat.utils.JsonUtils;
import com.example.yunchat.utils.LoginUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.yunchat.configs.StringConfig.EXIT_APP;
import static com.example.yunchat.configs.StringConfig.PASSWORD_EMPTY;
import static com.example.yunchat.configs.StringConfig.USERNAME_EMPTY;
import static com.example.yunchat.utils.LoginUtils.LOGIN_URL;


/**
 * 登录活动
 *
 * @author 曾健育
 */
public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
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
    /**
     * 线程池
     */
    ExecutorService cachedThreadPool;
    /**
     * 登录信息
     */
    private LoginInfo loginInfo;
    /**
     * 登录Handler
     */
    private Handler loginHandle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        //初始化线程池

        //初始化handler
        loginHandle = new LoginHandler();

        //获取Application
        app = (App) getApplication();

        //添加登录事件
        submitButton.setOnClickListener(v -> {
            cachedThreadPool = Executors.newCachedThreadPool();
            //获取输入值
            String usernameText = usernameInput.getText().toString();
            String passwordText = passwordInput.getText().toString();

            //检验空值
            if (usernameText.isEmpty()) {
                Toast.makeText(LoginActivity.this, USERNAME_EMPTY, Toast.LENGTH_SHORT).show();
                return;
            }

            if (passwordText.isEmpty()) {
                Toast.makeText(LoginActivity.this, PASSWORD_EMPTY, Toast.LENGTH_SHORT).show();
                return;
            }

            //进行登录
            loginInfo = new LoginInfo(usernameText, InfoUtils.md5(passwordText));

            Log.i(TAG, "onClick: " + loginInfo);
            tryLogin(loginInfo, cachedThreadPool);

        });
        //跳转注册
        gotoRegister.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivityForResult(intent, 1);
            overridePendingTransition(R.anim.open_enter_t, R.anim.open_exit_t);
        });
    }

    /**
     * 重写Back键，使双击退出程序
     */
    @Override
    public void onBackPressed() {
        //mExitTime的初始值为0，currentTimeMillis()肯定大于2000（毫秒），所以第一次按返回键的时候一定会进入此判断
        if ((System.currentTimeMillis() - mExitTime) > exitTime) {
            Toast.makeText(this, EXIT_APP, Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();
        } else {
            super.onBackPressed();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    assert data != null;
                    loginInfo = (LoginInfo) data.getSerializableExtra("login_info");
                    autoInput(loginInfo);
                }
                break;
            case 2:
                break;
            default:

        }
    }

    /**
     * 自动填入信息
     *
     * @param loginInfo 登陆信息
     */
    private void autoInput(LoginInfo loginInfo) {
        if (loginInfo != null) {
            usernameInput.setText(loginInfo.getUsername());
        }
    }

    private void tryLogin(LoginInfo loginInfo, ExecutorService cachedThreadPool) {
        cachedThreadPool.execute(() -> {
            Message message = loginHandle.obtainMessage();
            String result = HttpUtils.sendJsonPost(JsonUtils.beanToJson(loginInfo), LOGIN_URL, LoginActivity.this);
            if ("fail".equals(result)) {
                message.what = -1;
                loginHandle.sendMessage(message);
                return;
            }

            //向主线程传递参数

            message.obj = JsonUtils.getResult(result);
            message.what = 1;
            loginHandle.sendMessage(message);

        });
    }

    @SuppressLint("HandlerLeak")
    class  LoginHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    ReturnResult result = (ReturnResult) msg.obj;
                    if (result.getCode() == 1) {
                        User user = (User) JsonUtils.jsonToBean(JsonUtils.beanToJson(result), User.class);
                        app.setUser(user);
                        //登录成功
                        //前往主页面
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        LoginUtils.saveLoginInfo(user, LoginActivity.this);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, (String) result.getInfo(), Toast.LENGTH_SHORT).show();
                    }
                    break;
                case -1:
                    Toast.makeText(LoginActivity.this, "网络连接错误", Toast.LENGTH_SHORT).show();
                    break;
                default:
            }


        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cachedThreadPool.shutdownNow();
    }
}

