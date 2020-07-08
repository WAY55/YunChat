package com.example.yunchat.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.yunchat.App;
import com.example.yunchat.R;
import com.example.yunchat.activities.HomeActivity;
import com.example.yunchat.activities.NewFriendActivity;
import com.example.yunchat.adapters.MyFriendAdapter;
import com.example.yunchat.configs.IpConfig;
import com.example.yunchat.configs.StringConfig;
import com.example.yunchat.models.SocketMessage;
import com.example.yunchat.models.User;
import com.example.yunchat.task.MyFriendsTask;
import com.example.yunchat.utils.HttpUtils;
import com.example.yunchat.utils.JsonUtils;
import com.example.yunchat.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 用于显示通讯录
 *
 * @author 曾健育
 */
public class FriendsFragment extends Fragment {
    private static final String TAG = "FriendsFragment";

    public static int hasNew = 0;
    /**
     * ButterKnife
     */
    private Unbinder unbinder;
    @BindView(R.id.new_friends_click)
    LinearLayout newFriends;
    @BindView(R.id.new_icon)
    public ImageView newIcon;
    public FriendHandler handler;
    @BindView(R.id.my_friend_list)
    ListView listView;
    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.friends_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        init();
        return view;

    }

    private void init() {
        initNewFriendClick();
        initHandler();
        initNews();
        initList();
    }

    private void initList() {
        try {
            MyFriendsTask task = new MyFriendsTask(listView, getActivity());
            task.execute(HomeActivity.user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //初始化点击事件
        listView.setOnItemClickListener((parent, view, position, id) -> {
            MyFriendAdapter adapter = (MyFriendAdapter) parent.getAdapter();
            User user = (User) adapter.getItem(position);
            ToastUtils.showShortToast(user.toString());
        });
    }

    private void initNews() {
        new Thread(() -> {
            Message message = new Message();

            String result = HttpUtils.sendJsonPost(JsonUtils.beanToJson(HomeActivity.user), IpConfig.FRIENDS_NEWS);
            if ("fail".equals(result)) {
                message.what = -1;
                handler.sendMessage(message);
            }else {
                message.what = 2;
                int num = Integer.parseInt(result);
                Log.d(TAG, "initNews: " + num);
                if (num == -1) {
                    Log.d(TAG, "initNews: error");
                }
                hasNew += num;
                if (hasNew > 0) {
                    handler.sendMessage(message);
                }
            }

        }).start();
    }

    private void initHandler() {
        Log.d(TAG, "initHandler: 初始化Handler");
        handler = new FriendHandler();
        ((App) getActivity().getApplication()).setHandler(handler);
    }


    private void initNewFriendClick() {
        newFriends.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), NewFriendActivity.class);
            startActivityForResult(intent, 3);
            //取出新的标识
            newIcon.setVisibility(View.GONE);
            hasNew = 0;
            getActivity().overridePendingTransition(R.anim.open_enter_t, R.anim.open_exit_t);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    class FriendHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case -1:
                    ToastUtils.showShortToast(StringConfig.NETWORK_CONNECTION_FAILED);
                    break;
                case 1:
                    hasNew = 1;
                    ToastUtils.showShortToast("您有一条好友请求，请前往查看！");
                case 2:
                    newIcon.setVisibility(View.VISIBLE);
                    break;
                default:
            }
        }
    }

}
