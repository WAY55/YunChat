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
        MyHandler myHandler = new MyHandler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                String url = "http://" + IpConfig.getAddress() + "/avatar/" + user.getAvatar();
                Bitmap bitmap = HttpUtils.getUrlImage(url);
                Message message = myHandler.obtainMessage();
                message.what = 1;
                message.obj = bitmap;
                myHandler.sendMessage(message);
            }
        }).start();

        //获取头像

        //设置圆形头像
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);

        //设置用户名
        myUsername.setText(user.getUsername());
        return view;
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
                    imageView.setImageBitmap(BitmapUtils.ClipSquareBitmap(bitmap, 200, bitmap.getHeight()));
                    break;
                case 2:
                    break;
                default:
            }
        }
    }
}
