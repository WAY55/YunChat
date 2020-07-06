package com.example.yunchat.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.yunchat.App;
import com.example.yunchat.R;
import com.example.yunchat.configs.IpConfig;
import com.example.yunchat.fragments.FindFragment;
import com.example.yunchat.fragments.FriendsFragment;
import com.example.yunchat.fragments.MessagesFragment;
import com.example.yunchat.fragments.MyFragment;
import com.example.yunchat.models.User;
import com.example.yunchat.server.JWebSocketClient;
import com.example.yunchat.utils.LoginUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.net.URI;

import static com.example.yunchat.configs.StringConfig.NEVER_LOGIN;

/**
 * 首页
 *
 * @author 曾健育
 */
public class HomeActivity extends AppCompatActivity {
    private static final String TAG = "HomeActivity";
    /**
     * 两次按下返回键的时间范围
     */
    private final int exitTime = 2000;
    /**
     * 两次返回键推出的判断时间
     */
    private long mExitTime;
    /**
     * Application对象
     */
    App app;


    /**
     * 四个对应的内容页面
     */
    Fragment messageFragment;
    Fragment friendsFragment;
    Fragment findFragment;
    Fragment myFragment;

    boolean messageInit;
    boolean friendsInit;
    boolean findInit;
    boolean myInit;
    /**
     * fragment管理器
     */
    FragmentManager fragmentManager = getSupportFragmentManager();
    /**
     * 底部导航栏
     */
    BottomNavigationView bottomNavigationView;
    public static JWebSocketClient client;
    private User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //启动对话框内的服务
//        DialogMessageManager.startServer();
        //检测是否登录
        if (user == null) {
            //检查是否存有登录信息
            user = LoginUtils.getLoginInfo(this);
            Log.d(TAG, "onCreate: " + user);
            if (user == null) {
                //跳转登录界面
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                Toast.makeText(HomeActivity.this, NEVER_LOGIN, Toast.LENGTH_SHORT).show();
                startActivity(intent);
                finish();
            }
        }
        //启用WebSocket
        URI uri = URI.create("ws://" + IpConfig.getAddress() + "/connection/" + user.getOpenId());
        client = new JWebSocketClient(uri);
        try {
            client.connectBlocking();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        client.send("111");
        setContentView(R.layout.activity_home);


        //进行fragment初始化
        messageFragment = new MessagesFragment();
        friendsFragment = new FriendsFragment();
        findFragment = new FindFragment();
        myFragment = new MyFragment();

        messageFragment.setHasOptionsMenu(true);
        //是否需要初始化
        messageInit = true;
        friendsInit = true;
        findInit = true;
        myInit = true;

        //默认显示消息页
        messageInit = showFragment(messageFragment, messageInit);
        //底部导航栏允许自定义颜色
        bottomNavigationView = findViewById(R.id.home_tag);
        bottomNavigationView.setItemIconTintList(null);


        //底部导航栏点击事件
        bottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                //消息页
                case R.id.home_tag_messages:
                    messageInit = showFragment(messageFragment, messageInit);
                    break;
                //通讯录页
                case R.id.home_tag_friends:
                    friendsInit = showFragment(friendsFragment, friendsInit);
                    break;
                //发现页
                case R.id.home_tag_find:
                    findInit = showFragment(findFragment, findInit);
                    break;
                //个人页
                case R.id.home_tag_my:
                    myInit = showFragment(myFragment, myInit);
                    break;
                default:
                    throw new RuntimeException("不是有效的导航栏页面");
            }
            return true;
        });

    }


    @Override
    public void onBackPressed() {
        //mExitTime的初始值为0，currentTimeMillis()肯定大于2000（毫秒），所以第一次按返回键的时候一定会进入此判断
        if ((System.currentTimeMillis() - mExitTime) > exitTime) {
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();

        } else {
            super.onBackPressed();
        }

    }


    /**
     * 隐藏fragment
     *
     * @param fragments 需要隐藏的fragment
     */
    private void hideFragment(FragmentTransaction transaction, Fragment... fragments) {
        if (transaction == null) {
            transaction = fragmentManager.beginTransaction();
        }
        for (Fragment x :
                fragments) {
            if (x != null) {
                transaction.hide(x);
            }
        }
    }


    /**
     * 隐藏所有fragment
     */
    private void hideAllFragment(FragmentTransaction transaction) {
        hideFragment(transaction, messageFragment, friendsFragment, findFragment, myFragment);
    }

    /**
     * 显示fragment
     *
     * @param fragment 需要显示的fragment
     * @param init     是否需要初始化
     * @return false
     */
    private boolean showFragment(Fragment fragment, boolean init) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (init) {
            transaction.add(R.id.home_content, fragment);
        }
        //隐藏所有fragment
        hideAllFragment(transaction);
        transaction.show(fragment);
        transaction.commit();
        return false;
    }

}
