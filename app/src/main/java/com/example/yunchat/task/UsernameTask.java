package com.example.yunchat.task;

import android.os.AsyncTask;
import android.widget.TextView;

import com.example.yunchat.configs.IpConfig;
import com.example.yunchat.models.ReturnResult;
import com.example.yunchat.models.User;
import com.example.yunchat.utils.HttpUtils;
import com.example.yunchat.utils.JsonUtils;

public class UsernameTask extends AsyncTask<String, Void, String> {
    private TextView textView;
    public static final String url = "http://" + IpConfig.getAddress() + "/user/getUser";
    public UsernameTask(TextView textView) {
        this.textView = textView;
    }

    @Override
    protected String doInBackground(String... strings) {
        User user = new User();
        user.setOpenId(strings[0]);
        String username = null;
        try {
            String result = HttpUtils.sendJsonPost(JsonUtils.beanToJson(user), url);
            ReturnResult returnResult = (ReturnResult) JsonUtils.jsonToBean(result, ReturnResult.class);
            if (returnResult.getCode() == 1) {
                user = (User) JsonUtils.jsonToBean(JsonUtils.beanToJson(returnResult.getInfo()), User.class);
                username = user.getUsername();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return username;
    }

    @Override
    protected void onPostExecute(String s) {
        textView.setText(s);
        super.onPostExecute(s);
    }
}
