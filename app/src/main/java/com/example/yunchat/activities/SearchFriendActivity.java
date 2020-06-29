package com.example.yunchat.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.yunchat.R;
import com.example.yunchat.configs.IpConfig;
import com.example.yunchat.configs.StringConfig;
import com.example.yunchat.handlers.SearchFriendHandler;
import com.example.yunchat.models.ReturnResult;
import com.example.yunchat.models.SearchInfo;
import com.example.yunchat.models.User;
import com.example.yunchat.utils.HttpUtils;
import com.example.yunchat.utils.JsonUtils;

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
    /**
     * 搜索按钮
     */
    @BindView(R.id.search_friend_button)
    Button searchButton;
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
        //进行搜索
        searchButton.setOnClickListener(v -> {
            cachedThreadPool = Executors.newCachedThreadPool();
            String info = searchText.getText().toString();

            trySearch(new SearchInfo(info));
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

}
