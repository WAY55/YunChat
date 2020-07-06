package com.example.yunchat.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yunchat.R;
import com.example.yunchat.adapters.UserAdapter;
import com.example.yunchat.configs.IpConfig;
import com.example.yunchat.configs.StringConfig;
import com.example.yunchat.models.ReturnResult;
import com.example.yunchat.models.SearchInfo;
import com.example.yunchat.models.User;
import com.example.yunchat.utils.HttpUtils;
import com.example.yunchat.utils.JsonUtils;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.yunchat.configs.StringConfig.NETWORK_CONNECTION_FAILED;

/**
 * 搜索好友
 *
 * @author 曾健育
 */
public class SearchFriendActivity extends AppCompatActivity {
    private static final String TAG = "SearchFriendActivity";

    ListView listView;
    /**
     * 搜索文本
     */
    @BindView(R.id.search_friend_edittext)
    EditText searchText;
    /**
     * 线程池
     */
    ExecutorService cachedThreadPool;
    /**
     * 搜索handler
     */
    SearchFriendHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_friend);
        ButterKnife.bind(this);
        //初始化handler
        handler = new SearchFriendHandler(this);
        listView = findViewById(R.id.search_friend_list);
        //进行搜索

        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                cachedThreadPool = Executors.newCachedThreadPool();
                String info = searchText.getText().toString();
                trySearch(new SearchInfo(info));
            }
        });

        listView.setOnItemClickListener((parent, view, position, id) -> {
            UserAdapter adapter = (UserAdapter) parent.getAdapter();
            User user = (User) adapter.getItem(position);
            Intent intent = new Intent(SearchFriendActivity.this, AddFriendActivity.class);
            intent.putExtra("user", user);
            startActivityForResult(intent, 2);
            overridePendingTransition(R.anim.open_enter_t, R.anim.open_exit_t);
        });
    }

    private void trySearch(SearchInfo info) {
        Message message = handler.obtainMessage();
        cachedThreadPool.execute(() -> {
            String result = HttpUtils.sendJsonPost(JsonUtils.beanToJson(info), IpConfig.SEARCH_FRIEND);
            if ("fail".equals(result)) {
                Toast.makeText(this, NETWORK_CONNECTION_FAILED, Toast.LENGTH_SHORT).show();
                return;
            }
            message.what = 1;
            message.obj = JsonUtils.getResult(result);
            handler.sendMessage(message);
        });

    }

    /**
     * 搜索好友Handler
     *
     * @author 曾健育
     */
    class SearchFriendHandler extends Handler {

        private static final String TAG = "SearchFriendHandler";
        private Activity activity;

        public SearchFriendHandler(Activity activity) {
            this.activity = activity;
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    ReturnResult result = (ReturnResult) msg.obj;
                    if (result.getCode() == 1) {
                        List<User> users = (List<User>) JsonUtils.jsonToList(JsonUtils.beanToJson(result.getInfo()), new TypeToken<List<User>>() {
                        }.getType());
                        UserAdapter adapter = new UserAdapter(SearchFriendActivity.this, R.layout.user_item, users);
                        listView.setAdapter(adapter);

                    } else {
                        Toast.makeText(activity, (String) result.getInfo(), Toast.LENGTH_SHORT).show();
                    }
                    break;
                default:
            }
        }
    }


}
