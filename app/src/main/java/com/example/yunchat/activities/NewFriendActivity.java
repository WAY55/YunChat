package com.example.yunchat.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.example.yunchat.R;
import com.example.yunchat.adapters.NewFriendAdapter;
import com.example.yunchat.adapters.UserAdapter;
import com.example.yunchat.configs.IpConfig;
import com.example.yunchat.configs.StringConfig;
import com.example.yunchat.models.AddFriendMessage;
import com.example.yunchat.models.ReturnResult;
import com.example.yunchat.models.User;
import com.example.yunchat.utils.HttpUtils;
import com.example.yunchat.utils.JsonUtils;
import com.example.yunchat.utils.ToastUtils;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.yunchat.configs.StringConfig.NETWORK_CONNECTION_FAILED;

/**
 * @author 曾健育
 */
public class NewFriendActivity extends AppCompatActivity {
    private static final String TAG = "NewFriendActivity";
    @BindView(R.id.new_friends_toolbar)
    Toolbar toolbar;
    @BindView(R.id.new_friends_list)
    ListView listView;
    private FriendNewsHandler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_friend);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        initToolBar();
        initHandler();
        initLoad();
        initList();
    }

    private void initList() {
        new Thread(() -> {
            Message message = new Message();
            String result = HttpUtils.sendJsonPost(JsonUtils.beanToJson(HomeActivity.user), IpConfig.FRIENDS_GET_NEWS);
            Log.d(TAG, "initLoad: " +result);
            ReturnResult result2 = (ReturnResult)JsonUtils.jsonToBean(result, ReturnResult.class);
            if ("fail".equals(result)) {
                handler.sendEmptyMessage(-1);
            }else {
                message.what = 2;
                message.obj = result2;
                handler.sendMessage(message);
            }

        }).start();
    }

    private void initHandler() {
        handler = new FriendNewsHandler();
    }

    private void initLoad() {
        new Thread(()->{
            String result = HttpUtils.sendJsonPost(JsonUtils.beanToJson(HomeActivity.user), IpConfig.FRIENDS_LOAD_ALL);

            if ("fail".equals(result)) {
                handler.sendEmptyMessage(-1);
            } else {
                handler.sendEmptyMessage(1);
                Log.d(TAG, "initLoad: 已查看");
            }
        }).start();
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

    class FriendNewsHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case -1:
                    ToastUtils.showShortToast(StringConfig.NETWORK_CONNECTION_FAILED);
                    break;
                case 2:
                    ReturnResult result = (ReturnResult) msg.obj;
                    Log.d(TAG, "handleMessage: " + result);
                    if (result.getCode() == 1) {
                        String json = JsonUtils.beanToJson(result.getInfo());
                        Log.d(TAG, "handleMessage: " + json);
                        List<AddFriendMessage> users = (List<AddFriendMessage>) JsonUtils.jsonToList(json, new TypeToken<List<AddFriendMessage>>(){}.getType());
                        NewFriendAdapter adapter = new NewFriendAdapter(NewFriendActivity.this, R.layout.new_friend_item, users);
                        listView.setAdapter(adapter);
                    } else {
                        ToastUtils.showShortToast((String) result.getInfo());
                    }
                    break;
                default:
            }
        }
    }

}