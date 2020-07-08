package com.example.yunchat.task;

import android.os.AsyncTask;
import android.view.View;

import com.example.yunchat.R;
import com.example.yunchat.activities.HomeActivity;
import com.example.yunchat.configs.IpConfig;
import com.example.yunchat.models.AddFriendMessage;
import com.example.yunchat.models.ReturnResult;
import com.example.yunchat.models.SocketMessage;
import com.example.yunchat.utils.HttpUtils;
import com.example.yunchat.utils.JsonUtils;

/**
 * 接受好友请求
 * @author 曾健育
 */
public class AcceptTask extends AsyncTask<Integer, Void, Boolean> {

    private View view;
    public static final String url = "http://" + IpConfig.getAddress() + "/friend/accept";
    public AcceptTask(View view) {
        this.view = view;
    }


    @Override
    protected Boolean doInBackground(Integer... integers) {
        AddFriendMessage message = new AddFriendMessage();
        message.setId(integers[0]);
        try {
            String result = HttpUtils.sendJsonPost(JsonUtils.beanToJson(message), url);
            ReturnResult returnResult = (ReturnResult) JsonUtils.jsonToBean(result, ReturnResult.class);
            if (returnResult.getCode() == 1) {
                SocketMessage msg = new SocketMessage(2, integers[0]);
                HomeActivity.client.send(JsonUtils.beanToJson(msg));
                return true;
            }else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        if (aBoolean) {
            view.findViewById(R.id.accept_text).setVisibility(View.VISIBLE);
            view.findViewById(R.id.accept_button).setVisibility(View.GONE);
        }
        super.onPostExecute(aBoolean);
    }
}
