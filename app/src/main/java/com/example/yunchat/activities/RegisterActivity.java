package com.example.yunchat.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bigkoo.pickerview.TimePickerView;
import com.example.yunchat.R;
import com.example.yunchat.configs.StringConfig;
import com.example.yunchat.dialogs.LoadingDialog;
import com.example.yunchat.models.LoginInfo;
import com.example.yunchat.models.ReturnResult;
import com.example.yunchat.models.User;
import com.example.yunchat.utils.HttpUtils;
import com.example.yunchat.utils.InfoUtils;
import com.example.yunchat.utils.JsonUtils;
import com.example.yunchat.utils.RegisterUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.SneakyThrows;

import static com.example.yunchat.configs.StringConfig.ADDRESS_EMPTY;
import static com.example.yunchat.configs.StringConfig.BIRTHDAY_EMPTY;
import static com.example.yunchat.configs.StringConfig.CONFIRM_PASSWORD_EMPTY;
import static com.example.yunchat.configs.StringConfig.DAY;
import static com.example.yunchat.configs.StringConfig.EMAIL_EMPTY;
import static com.example.yunchat.configs.StringConfig.EMAIL_FORMAT_ERROR;
import static com.example.yunchat.configs.StringConfig.MONTH;
import static com.example.yunchat.configs.StringConfig.PASSWORD_EMPTY;
import static com.example.yunchat.configs.StringConfig.PASSWORD_LENGTH;
import static com.example.yunchat.configs.StringConfig.PASSWORD_NO_MATCHING;
import static com.example.yunchat.configs.StringConfig.SEX_EMPTY;
import static com.example.yunchat.configs.StringConfig.USERNAME_EMPTY;
import static com.example.yunchat.configs.StringConfig.USERNAME_LENGTH;
import static com.example.yunchat.configs.StringConfig.YEAR;
import static com.example.yunchat.utils.RegisterUtils.REGISTER_URL;

/**
 * 注册页面
 *
 * @author 曾健育
 */
public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "RegisterActivity";
    private Handler handler;
    private User user;

    /**
     * 注册用户名
     */
    @BindView(R.id.register_username)
    EditText usernameInput;
    /**
     * 注册密码
     */
    @BindView(R.id.register_password)
    EditText passwordInput;

    /**
     * 注册确认密码
     */
    @BindView(R.id.register_confirm_password)
    EditText confirmPasswordInput;

    /**
     * 注册邮箱
     */
    @BindView(R.id.register_email)
    EditText emailInput;

    /**
     * 注册性别
     */
    @BindView(R.id.register_sex)
    RadioGroup sexInput;

    /**
     * 注册生日
     */
    @BindView(R.id.date_input)
    EditText birthdayInput;

    /**
     * 注册地址
     */
    @BindView(R.id.register_address)
    EditText addressInput;

    /**
     * 注册按钮
     */
    @BindView(R.id.register_submit)
    Button submitButton;
    /**
     * 日期选择器
     */
    TimePickerView timePickerView;

    private LoadingDialog loadingDialog;
    private ExecutorService cachedThreadPool;
    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {


        loadingDialog = LoadingDialog.getInstance(RegisterActivity.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        //初始化加载动画

        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.register_toolbar);
        setSupportActionBar(toolbar);
        //添加默认的返回图标
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //设置返回键可用
        getSupportActionBar().setHomeButtonEnabled(true);
        //去掉左侧标题
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //Toolbar回退键事件
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.close_enter_t, R.anim.close_exit_t);
            }
        });

        //初始化handler
        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                androidx.appcompat.app.AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this, R.style.MyDialogStyle);
                super.handleMessage(msg);
                int dialogNum = 0;
                switch (msg.what) {
                    case 1:
                        ReturnResult resultHandler = (ReturnResult) msg.obj;
                        dialogNum = resultHandler.getCode();
                        Log.d(TAG, "handleMessage: " + resultHandler);
                        //有数据变化就关闭加载动画
                        if (dialogNum != 0) {
                            loadingDialog.dismiss();
                            //注册成功
                            if (dialogNum == 1) {
                                //执行弹窗提示

                                builder
                                        .setMessage("恭喜您注册成功")
                                        .setTitle("提示")
                                        .setPositiveButton("确认", (dialog, which) -> {
                                            //关闭对话框
                                            dialog.dismiss();


                                            //返回数据到登录界面
                                            Intent intent = new Intent();
                                            LoginInfo loginInfo = new LoginInfo(user.getUsername(), user.getPassword());
                                            intent.putExtra("login_info", loginInfo);
                                            setResult(RESULT_OK, intent);
                                            //跳转到登录
                                            RegisterActivity.this.finish();
                                            overridePendingTransition(R.anim.close_enter_t, R.anim.close_exit_t);
                                        });
                                builder.create().show();
                            }

                            //注册失败
                            if (dialogNum == -1) {
                                builder
                                        .setMessage(resultHandler.getInfo().toString())
                                        .setTitle("提示")
                                        .setPositiveButton("确认", (dialog, which) -> {
                                            //关闭对话框
                                            dialog.dismiss();

                                        });
                                builder.create().show();
                            }
                            dialogNum = 0;
                        }
                        break;
                    case -1:
                        Toast.makeText(RegisterActivity.this, "网路连接失败", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                }


            }
        };

        //日期输入框单击显示日期选择器
        birthdayInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //显示日期选择器
                timePickerView.show(birthdayInput);
            }
        });
        //构造时间选择器
        timePickerView = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                birthdayInput.setText(getTimes(date));
            }
        })
                .setType(new boolean[]{true, true, true, false, false, false})
                .setLabel(YEAR, MONTH, DAY, "", "", "")
                .build();

        user = new User();
        //注册点击事件
        submitButton.setOnClickListener(new View.OnClickListener() {
            @SneakyThrows
            @Override
            public void onClick(View v) {

                //获取各项信息值
                user.setUsername(InfoUtils.getEditTextValue(usernameInput));
                user.setPassword(InfoUtils.getEditTextValue(passwordInput));
                String confirmPasswordValue = InfoUtils.getEditTextValue(confirmPasswordInput);
                user.setEmail(InfoUtils.getEditTextValue(emailInput));
                user.setSex((byte) getSex());
                user.setBirthday(InfoUtils.getEditTextValue(birthdayInput));
                user.setAddress(InfoUtils.getEditTextValue(addressInput));

                //进行数据处理
                if (!checkEmpty(user, confirmPasswordValue)) {
                    return;
                }
                if (!checkRules(user)) {
                    return;
                }
                if (!user.getPassword().equals(confirmPasswordValue)) {
                    Toast.makeText(RegisterActivity.this, PASSWORD_NO_MATCHING, Toast.LENGTH_SHORT).show();
                    return;
                }
                //加载动画
                loadingDialog.show();
                //将密码进行MD5加密
                user.setPassword(InfoUtils.md5(user.getPassword()));


                //创建线程池
                cachedThreadPool = Executors.newCachedThreadPool();
                cachedThreadPool.execute(() -> {
                    Message message = handler.obtainMessage();
                    String result = HttpUtils.sendJsonPost(JsonUtils.beanToJson(user), REGISTER_URL);
                    if ("fail".equals(result)) {
                        message.what = -1;
                        handler.sendMessage(message);
                        return;
                    }
                    ReturnResult returnResult = JsonUtils.getResult(result);
                    //向主线程传递参数
                    message.what = 1;
                    message.obj = returnResult;
                    handler.sendMessage(message);

                });

            }
        });


    }

    /**
     * 格式化时间
     *
     * @param date 时间
     * @return 格式化字符串
     */
    private String getTimes(Date date) {
        //可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.close_enter_t, R.anim.close_exit_t);
    }

    /**
     * 获取选择的性别
     *
     * @return 0代表男性 1代表女性
     */
    private int getSex() {
        int radioButtonId = sexInput.getCheckedRadioButtonId();
        switch (radioButtonId) {
            //男
            case R.id.register_sex_male:
                return 0;
            case R.id.register_sex_female:
                return 1;
            default:
                return -1;
        }
    }

    /**
     * 数据查空处理
     *
     * @param user 输入的user序列
     * @return false代表不通过， true代表数据无空数据
     */
    private boolean checkEmpty(User user, String confirmPassword) {
        if (user.getUsername().isEmpty()) {
            Toast.makeText(RegisterActivity.this, USERNAME_EMPTY, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (user.getPassword().isEmpty()) {
            Toast.makeText(RegisterActivity.this, PASSWORD_EMPTY, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (confirmPassword.isEmpty()) {
            Toast.makeText(RegisterActivity.this, CONFIRM_PASSWORD_EMPTY, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (user.getSex() == -1) {
            Toast.makeText(RegisterActivity.this, SEX_EMPTY, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (user.getEmail().isEmpty()) {
            Toast.makeText(RegisterActivity.this, EMAIL_EMPTY, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (user.getBirthday().isEmpty()) {
            Toast.makeText(RegisterActivity.this, BIRTHDAY_EMPTY, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (user.getAddress().isEmpty()) {
            Toast.makeText(RegisterActivity.this, ADDRESS_EMPTY, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * 检查数据格式
     *
     * @param user 用户数据
     * @return false代表不通过， true代表数据无空数据
     */
    private boolean checkRules(User user) {
        int length = user.getUsername().length();
        if (length < 4 || length > 8) {
            Toast.makeText(RegisterActivity.this, USERNAME_LENGTH, Toast.LENGTH_SHORT).show();
            return false;
        }
        length = user.getPassword().length();
        if (length < 8 || length > 16) {
            Toast.makeText(RegisterActivity.this, PASSWORD_LENGTH, Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!InfoUtils.emailFormat(user.getEmail())) {
            Toast.makeText(RegisterActivity.this, EMAIL_FORMAT_ERROR, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
        super.onDestroy();
    }
}
