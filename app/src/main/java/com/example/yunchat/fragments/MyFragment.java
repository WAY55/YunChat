package com.example.yunchat.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.yunchat.App;
import com.example.yunchat.R;
import com.example.yunchat.configs.IpConfig;
import com.example.yunchat.models.User;
import com.example.yunchat.utils.BitmapUtils;
import com.example.yunchat.utils.HttpUtils;
import com.example.yunchat.utils.LoginUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 用于显示个人页
 * @author 曾健育
 */
public class MyFragment extends Fragment {
    private User user;
    private static final String TAG = "MyFragment";
    private App app;
    @BindView(R.id.my_toolbar)
    Toolbar toolbar;
    /**ButterKnife*/
    private Unbinder unbinder;
    /**头像*/
    @BindView(R.id.my_avatar)
    ImageView imageView;
    /**用户名*/
    @BindView(R.id.my_username)
    TextView myUsername;
    /**性别*/
    @BindView(R.id.my_sex)
    TextView mySex;
    MyHandler myHandler;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        assert activity != null;
        user = LoginUtils.getLoginInfo(activity);
        activity.setSupportActionBar(toolbar);
        //初始化handler
         myHandler = new MyHandler();
        //获取头像
        initAvatar();
        //初始化性别
        initSex();

        //设置用户名
        myUsername.setText(user.getUsername());
        return view;
    }

    private void initSex() {
        switch (user.getSex()) {
            case 0:
                mySex.setText(R.string.sex_male);
                break;
            case 1:
                mySex.setText(R.string.sex_female);
                break;
            default:
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }

     class MyHandler extends Handler{
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    Bitmap bitmap = (Bitmap) msg.obj;
                    imageView.setImageBitmap(bitmap);
                    break;
                case 2:
                    break;
                default:
            }
        }
    }

    private void initAvatar() {
        new Thread(() -> {
            String url = "http://" + IpConfig.getAddress() + "/avatar/" + user.getAvatar();
            Bitmap bitmap = HttpUtils.getUrlImage(url);
            Message message = myHandler.obtainMessage();
            message.what = 1;
            message.obj = bitmap;
            myHandler.sendMessage(message);
        }).start();
    }
}
