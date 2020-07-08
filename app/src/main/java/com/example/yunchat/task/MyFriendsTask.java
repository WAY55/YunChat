package com.example.yunchat.task;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.ListView;

import com.example.yunchat.R;
import com.example.yunchat.adapters.MyFriendAdapter;
import com.example.yunchat.models.ReturnResult;
import com.example.yunchat.models.User;
import com.example.yunchat.utils.HttpUtils;
import com.example.yunchat.utils.JsonUtils;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import static com.example.yunchat.configs.IpConfig.getAddress;

public class MyFriendsTask extends AsyncTask<User, Void, List<User>> {
    private ListView listView;
    private Activity activity;
    private static final String URL = "http://"  + getAddress() + "/friend/my";

    public MyFriendsTask(ListView listView, Activity activity) {
        this.listView = listView;
        this.activity = activity;
    }

    @Override
    protected List<User> doInBackground(User... users) {
        List<User> users1 = null;
        try {
            String json = HttpUtils.sendJsonPost(JsonUtils.beanToJson(users[0]), URL);
            ReturnResult result = (ReturnResult) JsonUtils.jsonToBean(json, ReturnResult.class);
            users1 = (List<User>) JsonUtils.jsonToList(JsonUtils.beanToJson(result.getInfo()), new TypeToken<List<User>>() {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();

        }
        return users1;
    }

    @Override
    protected void onPostExecute(List<User> users) {
        MyFriendAdapter adapter = new MyFriendAdapter(activity, R.layout.my_friend_item, users);
        listView.setAdapter(adapter);
    }
}
